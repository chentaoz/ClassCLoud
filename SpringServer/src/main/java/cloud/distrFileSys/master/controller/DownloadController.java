package cloud.distrFileSys.master.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cloud.distrFileSys.master.model.CloudAccount;
import cloud.distrFileSys.master.model.FileReps;
import cloud.distrFileSys.support.service.Configuration;


@Controller
public class DownloadController {
	@Autowired
	FileReps fr;
	
	 @RequestMapping(value =  Configuration.DOWNLOAD_PROCESS_PATH, method = RequestMethod.GET)
	 public @ResponseBody List<String> sendRequest(@PathVariable("path") String path, @PathVariable("user_id") Long uid){
			path=path.replace("%", "/");
		 	String cloudpath = new String();
			cloudpath = fr.findByUserandPath(uid, path).get(0).getCloudPath();
			

			
			CloudAccount cloudacc = new CloudAccount();
			cloudacc = fr.findByUserandPath(uid, path).get(0).getCloudAccount();
			
			
			String accessToken = new String();
			accessToken = cloudacc.getAccessToken();
			
			
			List<String> s = new ArrayList<String>();
			s.add(cloudpath);
			s.add(accessToken);
			
			return s;

			
			
			
		}

}
