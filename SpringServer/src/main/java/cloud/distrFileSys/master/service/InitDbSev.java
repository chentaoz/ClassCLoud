package cloud.distrFileSys.master.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloud.distrFileSys.master.model.TestModel;
import cloud.distrFileSys.master.model.testReps;

@Service
public class InitDbSev {
	@Autowired
	private testReps tp;
	
	
	@PostConstruct
	public void init(){
		TestModel tm=new TestModel();
		tp.save(tm);
	}
	
}
