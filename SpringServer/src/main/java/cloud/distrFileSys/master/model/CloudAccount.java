package cloud.distrFileSys.master.model;

import java.util.List;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class CloudAccount {

	@Id
	@GeneratedValue
	private Long cId;
	
	private String account;
	
	private String accessToken;
	
	private String password; // I haven't decided we shall record that or not;
	
	private String Provider;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="cloudAccount")
	private List<File> files;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getProvider() {
		return Provider;
	}

	public void setProvider(String provider) {
		Provider = provider;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
