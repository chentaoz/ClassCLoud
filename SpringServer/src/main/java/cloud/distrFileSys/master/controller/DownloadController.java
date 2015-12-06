package cloud.distrFileSys.master.controller;

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
	 public @ResponseBody String[] sendRequest(@PathVariable("path") String path, @PathVariable("user_id") Long uid){
			
			String cloudpath = new String();

			cloudpath = fr.findByUserandPath(uid, path).get(0).getCloudPath();
			System.out.println(fr.findByUserandPath(uid, cloudpath).size());

			
			CloudAccount cloudacc = new CloudAccount();
			cloudacc = fr.findByUserandPath(uid, cloudpath).get(0).getCloudAccount();
			System.out.println(fr.findByUserandPath(uid, cloudpath).size());
			String accessToken = new String();
			accessToken = cloudacc.getAccessToken();
			
			String info[];
			info = new String[1];
			
			info[0] = cloudpath;
			info[1] = accessToken;
			
			return info;
			
			
			
		}

}
