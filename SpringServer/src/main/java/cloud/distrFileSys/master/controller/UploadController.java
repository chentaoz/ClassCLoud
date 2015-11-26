package cloud.distrFileSys.master.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
import com.dropbox.core.v2.Users.GetSpaceUsageException;
import com.dropbox.core.v2.Users.SpaceUsage;

import cloud.distrFileSys.master.model.File;
import cloud.distrFileSys.master.model.FileReps;
import cloud.distrFileSys.master.model.Sessions;
import cloud.distrFileSys.support.service.Configuration;



@Controller
public class UploadController {
	
	@Autowired
	FileReps fileReps;
	
	
	@RequestMapping(value = Configuration.UPLOAD_PATH_START,method = RequestMethod.POST)
	public Sessions applyToUpload (@RequestBody File f,@PathVariable("id") long id) {
		String [] accessTokens;
		
		Sessions response=new Sessions();
		if(true){//todo: we need function to authenticate the user
			accessTokens=Configuration.ACCESS_TOKEN;
		}
		
		
		List<DbxClientV2> clients=new ArrayList<DbxClientV2>();
		for(String at: accessTokens){
		  DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
		  DbxClientV2 client = new DbxClientV2(config, at);
		  clients.add(client);
		}
		
		
	
		//loaderbalanec
		DbxClientV2 maxSpaceClient=null;
		Long maxSpace=new Long(0);
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
			if(maxSpaceClient==null){
				maxSpaceClient=client;
				maxSpace=us.allocation.getIndividual().allocated-us.used;	
			}
			else if(maxSpace < (us.allocation.getIndividual().allocated-us.used)){
				maxSpaceClient=client;
				maxSpace=us.allocation.getIndividual().allocated-us.used;	
			}
			
		}
		
		if(maxSpace<f.getSize()){
			response.setError("-3");
			return response;//no available space;
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
		response.setSession(sessionId);
		response.setAccessToken(maxSpaceClient.getAccessToken());
		
		return response;
		
    }
	
	@RequestMapping(value = Configuration.UPLOAD_PATH_END,method = RequestMethod.POST)
	public int finishUpload (@RequestBody File f,@PathVariable("id") long id,
			@PathVariable("session") Sessions session){
		String accessTokens;
		DbxClientV2 client=null;
		if(true){//todo: we need function to authenticate the user
			accessTokens=session.getAccessToken();
			DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
			client = new DbxClientV2(config, accessTokens);
		}
		
		String uploadSession=session.getSession();
		long offset=session.getOffset();
	
		
		//allocate path in cloud
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("E-yyyy-MM-dd-hh-mm-ss-a-zzz");
		
		String cloudPath=Configuration.PATH_IN_CLOUD+ft.format(dNow)+"/"+f.getName();
		
		
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
		
		//load file meta data for request
		f.setCloudPath(cloudPath);
		fileReps.save(f);
		
		
		
		
		
		
        return 1; 
	}

}
