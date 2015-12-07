package cloud.distrFileSys.support.service;

public class Configuration {
	
	public final static String UPLOAD_PROCESS_PATH="";//path to upload file on cloud provide
	
	public final static String UPLOAD_PATH_START="/upload/{id}";
	
	public final static String UPLOAD_PATH_END="/upload_done/{id}/{session}/{offset}";
	
	public final static String GET_FILE_PATH="get/{path}";
	
	public final static String DOWNLOAD_PROCESS_PATH="/download/{path}/{user_id}";
	
	public final static String PATH_IN_CLOUD="/distrStore/";
	
	public final static String PATH_IN_CLOUD_BACKUP="/distrStore_backup/";
	
	public final static String[] ACCESS_TOKEN={
		"UEL51iPANmAAAAAAAAAABupojQNQQbbABMzZ1_Ai-U3lNsad4UZLJU1t96gY4Ar_",
		"3Tlm1-6FWdAAAAAAAAAABQO90jv5Mmgl_w8Obvx_JviZ1ODv__8qB6BYuUxpoXge",
		"qkh1oWRdV5AAAAAAAAAAG927BK8Onynf1CFfBbwcGJQEItTKsoHX4eW7zdD4sqbv"};
	
	public final static String LOG_IN="/login/{id}"; 
	
	

}
