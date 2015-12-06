package cloud.distrFileSys.master.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CloudAccountReps extends JpaRepository<CloudAccount, Long> {
	
	@Query("select c  from CloudAccount c where c.account = :acc and  c.Provider=:Pd")
	List<CloudAccount> findOneByAccount(@Param("acc")String account,@Param("Pd")String provider);
	
	@Query("select c from CloudAccount c where c.account = ?1")
	List<CloudAccount> findOneByA(String account);
	
	//@Query("select c from CloudAccount c where c.userId = ?1")
	List<CloudAccount> findByUser(User user);
}
