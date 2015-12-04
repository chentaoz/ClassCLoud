package cloud.distrFileSys.master.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloud.distrFileSys.master.model.CloudAccount;
import cloud.distrFileSys.master.model.CloudAccountReps;
import cloud.distrFileSys.master.model.TestModel;
import cloud.distrFileSys.master.model.User;
import cloud.distrFileSys.master.model.UserReps;
import cloud.distrFileSys.master.model.testReps;

@Service
public class InitDbSev {
	@Autowired
	private CloudAccountReps car;
	@Autowired
	private UserReps ur;
	@Autowired
	private testReps t;
	
	@PostConstruct
	public void init(){
		//test init
		TestModel tm=new TestModel();
		t.save(tm);
		
		
		 //init cloud accout; hard code!!!
		CloudAccount firstAccount=new CloudAccount();
		
		firstAccount.setAccount("chentaoz@udel.edu");
		firstAccount.setAccessToken("UEL51iPANmAAAAAAAAAABupojQNQQbbABMzZ1_Ai-U3lNsad4UZLJU1t96gY4Ar_");
		firstAccount.setProvider("dropbox");
		
		car.save(firstAccount);
		
		CloudAccount secondAccount=new CloudAccount();
		
		secondAccount.setAccount("freedom.22117@gmail.com");
		secondAccount.setAccessToken("3Tlm1-6FWdAAAAAAAAAABQO90jv5Mmgl_w8Obvx_JviZ1ODv__8qB6BYuUxpoXge");
		secondAccount.setProvider("dropbox");
		
		car.save(secondAccount);
		
		CloudAccount thirdAccount=new CloudAccount();
		
		thirdAccount.setAccount("huiding@udel.edu");
		thirdAccount.setAccessToken("qkh1oWRdV5AAAAAAAAAAG927BK8Onynf1CFfBbwcGJQEItTKsoHX4eW7zdD4sqbv");
		thirdAccount.setProvider("dropbox");
		
		car.save(thirdAccount);
		
		// init user
		User u=new User();
		
		List<CloudAccount> accounts= new ArrayList<CloudAccount>();
		accounts.add(firstAccount);
		accounts.add(secondAccount);
		accounts.add(thirdAccount);
		
		u.setCloudAccounts(accounts);
		
		ur.save(u);
		
		
		
		
		
		
	}
	
}
