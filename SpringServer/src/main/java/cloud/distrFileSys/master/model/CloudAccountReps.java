package cloud.distrFileSys.master.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CloudAccountReps extends JpaRepository<CloudAccount, Long> {
	
	@Query("select c  from CloudAccount c where c.account = :account and  c.provider=:provider")
	CloudAccount findOneByAccount(@Param("account")String account,@Param("provider")String provider);
}
