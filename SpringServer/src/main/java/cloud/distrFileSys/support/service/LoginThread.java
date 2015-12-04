package cloud.distrFileSys.support.service;

import cloud.distrFileSys.master.model.CloudAccount;
import cloud.distrFileSys.master.model.CloudAccountReps;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

public class LoginThread extends Thread {
	
	String accessToekn;
	String email;
	String provider;
	Long accountId;//the account id in master server database
	String option;
	Object client;//return client
	CloudAccountReps car;
	private Object variable;
	



	public Object getClient() {
		return client;
	}


	public void setClient(Object client) {
		this.client = client;
	}


	public Object getVariable() {
		return variable;
	}


	public void setVariable(Object variable) {
		this.variable = variable;
	}


	public LoginThread(String provider,String ID,String option,Object o){
	
		this.provider=provider;
		if(provider=="provider"){
			car=(CloudAccountReps)o;
		}
		switch (option){
			case "at":this.accessToekn=ID;this.option=option;break;
			case "em":this.email=ID;this.option=option;break;
			case "ai":this.accountId=Long.parseLong(ID);
			default: break;
		}
	}
	
	
	public void run(){
		switch (option){
			case "at":loginByAccessToken(accessToekn);break;
			case "em":loginByEmail(email);break;
			case "ai":
			default: break;
		}
		
		
	}
	
	public void loginByAccessToken(String at){
		if(provider=="dropbox"){
			DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
			DbxClientV2 client = new DbxClientV2(config, at);
			this.client=client;  
		}
		
	}
	public void loginByEmail(String em){
		if(provider=="dropbox"){
			String at=car.findOneByAccount(em, "dropbox").getAccessToken();
			DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
			DbxClientV2 client = new DbxClientV2(config, at);
			this.client=client; 
		}
		 
	}
	
	public void loginByAccountId(Long id){
		if(provider=="dropbox"){
			String at=car.findOne(id).getAccessToken();
			DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
			DbxClientV2 client = new DbxClientV2(config, at);
			this.client=client; 
		}
	}
	
	
	
	
}
