package cloud.distrFileSys.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	  @RequestMapping(value = "/greeting")
	    public String sayHello (Model model) {
	        model.addAttribute("greeting", "MVC framework construction successed!");
	        return "index"; 
	    }
	
}
