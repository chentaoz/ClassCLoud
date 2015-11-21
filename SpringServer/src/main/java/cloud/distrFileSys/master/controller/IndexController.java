package cloud.distrFileSys.master.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.Users;

import cloud.distrFileSys.support.service.Configuration;



@Controller
public class IndexController {
	  @RequestMapping(value = "/greeting")
	    public String sayHello (Model model) {
	        model.addAttribute("greeting", "MVC framework construction successed!");
	        return "index"; 
	    }
	  
	  
	 
	  //login test
	  @RequestMapping(value = Configuration.LOG_IN)
	  public String login(@PathVariable("id") long id,Model model) 
			  throws Users.GetCurrentAccountException, DbxException 
	  {
		  
		  String[] accessTokens=Configuration.ACCESS_TOKEN;
		  
		  List<DbxClientV2> clients=new ArrayList<DbxClientV2>();
		  for(String at: accessTokens){
			  DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
			  DbxClientV2 client = new DbxClientV2(config, at);
			  clients.add(client);
		  }
		 
		  //print out login account
		  for(DbxClientV2 c :clients){
			  Users.FullAccount account = c.users.getCurrentAccount();
		      System.out.println(account.name.displayName);
		  }
		  
		  //print out on jsp
		  List<String> jsp_clients=new ArrayList<String>();
		  for(DbxClientV2 c :clients){
			  Users.FullAccount account = c.users.getCurrentAccount();
			  jsp_clients.add(account.name.displayName);
		  }
		  
		  model.addAttribute("clients", jsp_clients);
	        
	      return "login";
		  
	  }
	
}
