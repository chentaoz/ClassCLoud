package cloud.distrFileSys.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DownloadController {
	
	
	 @RequestMapping(value = "/download/{id}/{path}")
	 public void test(@PathVariable("id") Long id, @PathVariable("path") String path){
		 System.out.println(1);
		
	 }
}
