package cloud.distrFileSys.support.service;

import java.util.Collection;
import java.util.List;

import cloud.distrFileSys.master.model.BackUpSessions;
import cloud.distrFileSys.master.model.File;
import cloud.distrFileSys.master.model.Sessions;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;

public interface FileApi {

	public final String upload_process_path=Configuration.UPLOAD_PROCESS_PATH;
	
	public final String upload_path_start=Configuration.UPLOAD_PATH_START;
	
	public final String upload_path_end=Configuration.UPLOAD_PATH_END;
	
	public final String get_file_path=Configuration.GET_FILE_PATH;
	
	public final String download_process_path=Configuration. DOWNLOAD_PROCESS_PATH;
	
	public final String path_path="path";
	
	public final String pah_id="id";
	
	public final String para_data="data";
	
	public final String para_session="session";
	
	public final String log_in=Configuration.LOG_IN;
	
	/*client
	 * 
	 */
	@GET(log_in)//client login
	public Collection<File> login(@Path(pah_id) long id);
	
	@GET(get_file_path)
	public Collection<File> getFiles(@Path(path_path) String path);
	
	@POST(upload_path_start)
	public Sessions addFile(@Path(pah_id) long id, @Body File f);
	
	@POST(upload_path_end)
	public Integer addFileE(@Path(pah_id) long id,@Path(para_session) String session,@Path("offset") Long offset,@Body File f);
	
	@POST(Configuration.UPLOAD_PATH_BACK)
	public Integer addFileB(@Body BackUpSessions s);
	
	
	
	@GET(Configuration.DOWNLOAD_PROCESS_PATH)
	public List<String> downfileinfo(@Path("path") String path, @Path("user_id") Long uid);

	
	
}
