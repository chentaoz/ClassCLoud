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
		 
		  
		  Users.FullAccount account = clients.get(0).users.getCurrentAccount();
		  model.addAttribute("login", account.name.displayName);
	      System.out.println(account.name.displayName);
	        
	      return "login";
		  
	  }
	
}
