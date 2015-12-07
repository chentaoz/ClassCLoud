package cloud.distrFileSys.master.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.Files.CommitInfo;
import com.dropbox.core.v2.Files.UploadSessionCursor;
import com.dropbox.core.v2.Files.UploadSessionFinishBuilder;
import com.dropbox.core.v2.Files.UploadSessionFinishException;
import com.dropbox.core.v2.Files.UploadSessionFinishUploader;
import com.dropbox.core.v2.Files.UploadSessionStartException;
import com.dropbox.core.v2.Files.UploadSessionStartUploader;
import com.dropbox.core.v2.Files.WriteMode;
import com.dropbox.core.v2.Users;
import com.dropbox.core.v2.Users.GetCurrentAccountException;
import com.dropbox.core.v2.Users.GetSpaceUsageException;
import com.dropbox.core.v2.Users.SpaceUsage;

import cloud.distrFileSys.master.model.CloudAccount;
import cloud.distrFileSys.master.model.CloudAccountRent;
import cloud.distrFileSys.master.model.CloudAccountReps;
import cloud.distrFileSys.master.model.File;
import cloud.distrFileSys.master.model.FileReps;
import cloud.distrFileSys.master.model.RentReps;
import cloud.distrFileSys.master.model.Sessions;
import cloud.distrFileSys.master.model.User;
import cloud.distrFileSys.master.model.UserReps;
import cloud.distrFileSys.support.service.Configuration;
import cloud.distrFileSys.support.service.LoginThread;



@Controller
public class UploadController {
	
	@Autowired
	FileReps fileReps;
	
	@Autowired
	RentReps accRent;
	
	@Autowired
	UserReps ur;
	
	@Autowired
	CloudAccountReps car;
	
	//temperate store , session is better
	HashMap<String,DbxClientV2> hm=new HashMap<String,DbxClientV2>();
	HashMap<String,String> hm1=new HashMap<String,String>();
	
	@RequestMapping(value = Configuration.UPLOAD_PATH_START,method = RequestMethod.POST)
	public @ResponseBody Sessions applyToUpload (@RequestBody File f,@PathVariable("id") long id) {
		ArrayList<String> accessTokens=new ArrayList<String>();
		
		Sessions response=new Sessions();
		User u=ur.findOne(id);
		List<CloudAccount> cs=null;
		if(true){//todo: we need function to authenticate the user
			cs=car.findByUser(u);
			for(CloudAccount ca : cs){
				accessTokens.add(ca.getAccessToken());
			}
		}
		else{
			// authentication failed
			response.setError("-1");
			return response;
		}
		List<DbxClientV2> clients=new ArrayList<DbxClientV2>();
		for(String at: accessTokens){
		  DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
		  DbxClientV2 client = new DbxClientV2(config, at);
		  clients.add(client);
		}
		
		//test-------------------------------------------------------------
		
//		clients=new ArrayList<DbxClientV2>();
//		String testtoekn="qkh1oWRdV5AAAAAAAAAAG927BK8Onynf1CFfBbwcGJQEItTKsoHX4eW7zdD4sqbv";
//		 DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
//		  DbxClientV2 cl = new DbxClientV2(config, testtoekn);
//		  
//		  clients.add(cl);
		//-------------------------------------------------------------------
	
		//loaderbalance
		DbxClientV2 maxSpaceClient=null;
		Long maxSpace=new Long(0);
		if(clients.size()!=0)
			for(DbxClientV2 client:clients){
				Users users=client.users;
				SpaceUsage us=null;
				try {
					us=users.getSpaceUsage();
				} catch (GetSpaceUsageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.setError("-2");
					return response;//exception
				} catch (DbxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.setError("-2");
					return response;//exception
				}
				Long cid=new Long(0);
				try {
					System.out.println(users.getCurrentAccount().email);
					cid = car.findOneByAccount(users.getCurrentAccount().email, "dropbox").get(0).getcId();
				} catch (GetCurrentAccountException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.setError("hibernate bug1 !");
					return response;//exception
				} catch (DbxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.setError("hibernate bug2 !");
					return response;//exception
				}
				Long newSpace;
				if(accRent.getAllRentAccountBySupplierAccId(cid)!=null){
					float totalRent=0;
					for(CloudAccountRent car1:accRent.getAllRentAccountBySupplierAccId(cid)){
						totalRent+=car1.getRate();
					}
					newSpace=(long) (us.allocation.getIndividual().allocated*(1-totalRent)-us.used);	
				}
				else{
					newSpace=us.allocation.getIndividual().allocated-us.used;
				}
				newSpace=us.allocation.getIndividual().allocated-us.used;
				
				//debug
				//response.setError(""+us.used);
				//return response;
				
				if(maxSpaceClient==null){
					maxSpaceClient=client;
					maxSpace=newSpace;	
					
				}
				else if(maxSpace < newSpace){
					maxSpaceClient=client;
					maxSpace=newSpace;	
				}
				
			}
		
		if(maxSpace<f.getSize()){
			
			//user's account space (exclude rent space) is full
			//todo : actual space of user's own account space is not full, just single accont space is not enough 
			// to upload the file but not combination of his/her accounts ,future effort should overcome this short;
			
			List<CloudAccountRent> rentAccs=accRent.getAllRentAccount(id);
			if(rentAccs==null || rentAccs.size()==0){
				response.setError("-3");
				return response;//no available space;	
			}
			else{//handling rent space
				for(CloudAccountRent rent :rentAccs){
					Long supplierAccId=rent.getSupplierAccId();
					CloudAccount cr=car.findOne(supplierAccId);
					if(cr.getProvider()=="dropbox"){
						//todo!!!!!!!!!!!!!!!!!!!!!
						if(accRent.getAllRentAccount(id)!=null){
							ArrayList<LoginThread> alThreads=new ArrayList<LoginThread>();
							Long maxRentSpace=new Long(0);
							String MaxAccEmail=null;
							for(CloudAccountRent ca2:accRent.getAllRentAccount(id)){
	
								LoginThread lt=new LoginThread("dropbox",""+ca2.getSupplierAccId
										(),"ai",car);
								lt.start();
								lt.setVariable(ca2);
								alThreads.add(lt);
								
							}
							ArrayList<DbxClientV2> suppliers=new ArrayList<DbxClientV2> ();
							
							for(LoginThread sngT: alThreads){
								try {
									sngT.join();//wait until thread finish.
									if(sngT.getClient()==null)
										continue;
									else{
										DbxClientV2 supplier=(DbxClientV2)(sngT.getClient());
										
										maxSpaceClient=supplier;
										break;
										//simplified ,future effort !!!!!	
									}
									
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}	
						}
					}
					
				}
			}
			
			
		}
		if(maxSpaceClient==null)
		{
			response.setError("-4");
			return response;
		}
	
		
		
		UploadSessionStartUploader st=null;
		try {
			st=maxSpaceClient.files.uploadSessionStart();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setError("-2");
			return response;//exception
		}
		String sessionId="";
		try {
			sessionId=st.finish().sessionId;
		} catch (UploadSessionStartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setError("-2");
			return response;//exception
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setError("-2");
			return response;//exception
		}
		//add to memory to memorize the client account;
		hm.put(sessionId,maxSpaceClient);
		
		response.setSession(sessionId);
		System.out.println(sessionId);
		response.setAccessToken(maxSpaceClient.getAccessToken());
		System.out.println(maxSpaceClient.getAccessToken());
		response.setError("0");
		return response;
		
		
		
    }
	
	@RequestMapping(value = Configuration.UPLOAD_PATH_END,method = RequestMethod.POST)
	@Transactional
	public @ResponseBody Integer finishUpload (@RequestBody File f,@PathVariable("id") long id,
			@PathVariable("session") String session,@PathVariable("offset") Long offSet){
		DbxClientV2 client=hm.get(session);
		ArrayList<String> accessTokens=new ArrayList<String>();

		if(true){//todo: we need function to authenticate the user
			User u=ur.findOne(id);
			for(CloudAccount ca : u.getCloudAccounts()){
				accessTokens.add(ca.getAccessToken());
			}
		}
		else{
			// authentication failed
			return -1;
		}
		System.out.println("test point 1!!!!!!!!!!!!");
		String uploadSession=session;
		long offset=offSet;
		System.out.println("test point 2!!!!!!!!!!!!");
		
		//allocate path in cloud
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("E-yyyy-MM-dd-hh-mm-ss-a-zzz");
		
		//for file stored in the user's cloud acount, not in rent space.
		String cloudPath=Configuration.PATH_IN_CLOUD+ft.format(dNow)+"/"+f.getName();
		// todo for in rent space
		
		UploadSessionCursor cursor =new UploadSessionCursor(uploadSession,offset);
		
		//now I decide no allowance to rename automatically
		CommitInfo commit=new CommitInfo(cloudPath,WriteMode.add,false,new Date(),false);
		
		UploadSessionFinishBuilder finisher=client.files.uploadSessionFinishBuilder(cursor, commit);
		UploadSessionFinishUploader finishUpload;
		try {
			finishUpload=finisher.start();
		} catch (UploadSessionFinishException e) {
			e.printStackTrace();
			return -2;//exception
		} catch (DbxException e) {
			e.printStackTrace();
			return -2;//exception
		}
		try {
			cloudPath=finishUpload.finish().pathLower;
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;//exception
		}
		
		
		//load file meta data for request and store it into master database
		f.setCloudPath(cloudPath);
		CloudAccount storedAccount=null;
		try {
			storedAccount =car.findOneByAccount(client.users.getCurrentAccount().email, "dropbox").get(0);
		} catch (GetCurrentAccountException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		}
		
		if(storedAccount==null)
			return -4;//database exception
		
		f.setCloudAccount(storedAccount);
		f.setUserId(id);
		fileReps.save(f);
		
		hm.remove(session);
		
		//test 
		System.out.println(f.getFileType()+"....."+f.getSize()+"....."+f.getId()+"....."+f.getPath()+"....."+f.getCloudAccount().getAccount()+"....."+f.getCloudPath());
		
		
        return 1; 
	}

}

