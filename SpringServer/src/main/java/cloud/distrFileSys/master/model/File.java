package cloud.distrFileSys.master.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fluentinterface.ReflectionBuilder;
import com.fluentinterface.builder.Builder;



@Entity
public class File {
	
	
	public static FileBuilder create() {
		return ReflectionBuilder.implementationFor(FileBuilder.class).create();
	}
	
	public interface FileBuilder extends Builder<File> {
		public FileBuilder withFileType(String type);
		public FileBuilder withSize(Long size);
		public FileBuilder withPath(String path);
		public FileBuilder withName(String name);
		public FileBuilder withUserId(Long uId);
	}
	
	
	
	
	@JsonIgnore
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "dir")
    private File dir;//Directory
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="dir")
	private List<File> child;//file or directory 
	
	private String fileType;//type of file
	
	private long size;//value of size of file
	
	private String sizeUnit="b";// the unit for the size above, such as , b, kb ,mb ,gb, etc.
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name="cId")
	@JsonIgnore
	private CloudAccount cloudAccount;
	
	@JsonIgnore
    private String cloudPath;
	
	@JsonIgnore
	private String status="saved";//default is saved, other options : deleted ,lost
	
	@JsonIgnore
	private Integer isDir=0;// 0 represent this is file ,otherwise it's directory
	
	@JsonIgnore
	private Long parentDirId=(long) 0;//0 : it's in root. 
	
	private String path="/";//storage path in master server
	
	private String name;
	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getParentDirId() {
		return parentDirId;
	}

	public void setParentDirId(Long parentDirId) {
		this.parentDirId = parentDirId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getSizeUnit() {
		return sizeUnit;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


	public CloudAccount getCloudAccount() {
		return cloudAccount;
	}

	public void setCloudAccount(CloudAccount cloudAccount) {
		this.cloudAccount = cloudAccount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsDir() {
		return isDir;
	}

	public void setIsDir(Integer isDir) {
		this.isDir = isDir;
	}
	public File getDir() {
		return dir;
	}

	public void setDir(File dir) {
		this.dir = dir;
	}

	public List<File> getChild() {
		return child;
	}

	public void setChild(List<File> child) {
		this.child = child;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCloudPath() {
		return cloudPath;
	}

	public void setCloudPath(String cloudPath) {
		this.cloudPath = cloudPath;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	
	
	
	
	

}
