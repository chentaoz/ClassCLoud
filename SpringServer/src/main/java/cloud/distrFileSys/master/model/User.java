package cloud.distrFileSys.master.model;



import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User {
	
	
	@Id
	@GeneratedValue
	private Long userId;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="user")
	private List<CloudAccount> cloudAccounts;
	
	



	public List<CloudAccount> getCloudAccounts() {
		return cloudAccounts;
	}



	public void setCloudAccounts(List<CloudAccount> cloudAccounts) {
		this.cloudAccounts = cloudAccounts;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}




	


	





}
