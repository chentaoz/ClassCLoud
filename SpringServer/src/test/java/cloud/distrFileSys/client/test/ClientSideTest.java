package cloud.distrFileSys.client.test;

import static org.junit.Assert.assertEquals;

import java.io.File;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

import cloud.distrFileSys.master.model.Sessions;
import cloud.distrFileSys.support.service.FileApi;
import retrofit.RestAdapter;

import org.junit.Test;
public class ClientSideTest {
	private static final String SERVER = "http://localhost:8080/SpringServer/";
	
	
	private File uploadingFData;

	private cloud.distrFileSys.master.model.File file;
	
	private FileApi fileSvc = new RestAdapter.Builder()
	.setEndpoint(SERVER).build()
	.create(FileApi.class);
	
	
	
	public File getUploadingFData() {
		return uploadingFData;
	}


	public void setUploadingFData(String path) {
		this.uploadingFData = new File(path);
	}


	public cloud.distrFileSys.master.model.File getFile() {
		return file;
	}


	public void setFile(cloud.distrFileSys.master.model.File file) {
		this.file = file;
	}
	
	public void setFile(String type,Long size,String path,String name,Long uId){
		this.file=cloud.distrFileSys.master.model.File.create().
				withFileType(type).withName(name).withPath(path).withSize(size).withUserId(uId).build();
	
	}
	
	
	@Test
	public void initialUpload() throws Exception{
		String path;Long uId;
		path="src/test/resources/test.mp4";
		uId=new Long(2);
		
		File newF=new File(path);
		Path p=Paths.get(newF.getAbsolutePath());
		try {
			BasicFileAttributes attr
			= Files.getFileAttributeView(p, BasicFileAttributeView.class)
			       .readAttributes();
			
			Long size= new Long(attr.size());
			String name=newF.getName();
			String sfix=name.substring(name.lastIndexOf(".")+1);
			
			setFile(sfix,size,path,name,uId);
			
			Sessions s=fileSvc.addFile(uId, file);
			
			System.out.println(s.getError());
			
			assertEquals(s.getError(), "0");
			
		
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return s;
	}
	
	@Test
	public void finishUpload() throws Exception{
		initialUpload();
	}
	
}
