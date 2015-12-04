package cloud.distrFileSys.master.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Entity
public class CloudAccountRent {
	
	@Id
	@GeneratedValue
	private Long rId;
	
	private Long clientId;//user who rent the account
	
	private float rate;		// 1.0 indicate full rent of account
							// 0.1 rent the 10% of the account
	
	private Date startDate;
	
	private Date endDate;
	
	private String status="active";
						// active & inactive


	private Long supplierAccId;
	
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSharingAccess() {
		return sharingAccess;
	}

	public void setSharingAccess(String sharingAccess) {
		this.sharingAccess = sharingAccess;
	}

	private String sharingAccess;//dropbox could be share links
	


	public Long getSupplierAccId() {
		return supplierAccId;
	}

	public void setSupplierAccId(Long supplierAccId) {
		this.supplierAccId = supplierAccId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getrId() {
		return rId;
	}

	public void setrId(Long rId) {
		this.rId = rId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
