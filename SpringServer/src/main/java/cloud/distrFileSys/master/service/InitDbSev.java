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
		
		
		 //init cloud accout;
		CloudAccount firstAccount=new CloudAccount();
		
		firstAccount.setAccount("chentaoz@udel.edu");
		firstAccount.setProvider("dropbox");
		
		car.save(firstAccount);
		
		// init user
		User u=new User();
		
		List<CloudAccount> accounts= new ArrayList<CloudAccount>();
		accounts.add(firstAccount);
		
		u.setCloudAccounts(accounts);
		
		ur.save(u);
		
		
		
		
		
		
	}
	
}
