package cloud.distrFileSys.master.model;

public class Sessions {

	private String accessToken;
	
	private String session;
	
	private String error;
	
	private long offset=0;
	
	private String accessToken_backup="N/A";
	
	public String getAccessToken_backup() {
		return accessToken_backup;
	}
	public void setAccessToken_backup(String accessToken_backup) {
		this.accessToken_backup = accessToken_backup;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
}
