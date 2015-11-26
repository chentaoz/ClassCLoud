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


@Entity
public class File {

	@Id
	@GeneratedValue
	private Long id;
	

	@ManyToOne
	@JoinColumn(name = "dir")
   private File dir;//Directory
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="dir")
	private List<File> child;//file or directory 
	
	private String fileType;//type of file
	
	private long size;//value of size of file
	
	private String sizeUnit;// the unit for the size above, such as , b, kb ,mb ,gb, etc.
	
	private Date createdDate;
	
	private Date updatedDate;
	
	@ManyToOne
	@JoinColumn(name="cId")
	private CloudAccount cloudAccount;
	
    private String cloudPath;

	private String status="saved";//default is saved, other options : deleted ,lost
	
	
	private Integer isDir=0;// 0 represent this is file ,otherwise it's directory
	
	
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


	
	
	
	
	

}
