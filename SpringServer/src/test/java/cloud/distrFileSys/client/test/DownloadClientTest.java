package cloud.distrFileSys.client.test;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import retrofit.RestAdapter;
import cloud.distrFileSys.master.controller.DownloadController;
import cloud.distrFileSys.support.service.FileApi;

import org.junit.*;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxStreamReader.OutputStreamCopier;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxRawClientV2;
import com.dropbox.core.v2.Files.DownloadBuilder;
import com.dropbox.core.v2.Users;
import com.dropbox.core.DbxDownloader;

public class DownloadClientTest {
	
	private static final String SERVER = "http://localhost:8080/SpringServer/";
	
	private File downloadingFData;
	
	private cloud.distrFileSys.master.model.File file;
	
	private FileApi fileSvc = new RestAdapter.Builder()
	.setEndpoint(SERVER).build()
	.create(FileApi.class);

	private DbxRequestConfig request;

	private static String accToken;
	


	public File getDownloadingFData() {
		return downloadingFData;
	}

	public void setDownloadingFData(File downloadingFData) {
		this.downloadingFData = downloadingFData;
	}

	public cloud.distrFileSys.master.model.File getFile() {
		return file;
	}

	public void setFile(cloud.distrFileSys.master.model.File file) {
		this.file = file;
	}
	
	
	@Test
	public void initialDownload() throws Exception{
		String path;
		Long uid;
		path = "src/test/resources/test.mp4";
		uid = new Long(1);
		
		path=path.replace("/", "%");
		
		List<String> info = fileSvc.downfileinfo(path, uid);
		System.out.println(path);
//		accToken = info.get(0);		
//		System.out.println(accToken);
		
	}
	
//	public static void main(String args[]) throws Users.GetCurrentAccountException, DbxException {
//        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
//        DbxClientV2 client = new DbxClientV2(config, accToken);
//        
//        Users.FullAccount account = client.users.getCurrentAccount();
//        System.out.println(account.name.displayName);
//
//    
//	
//	Object inputstream;
//	downloadingFData = DbxDownloader(result, inputstream);
//}
}

