package cloud.distrFileSys.master.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RentReps extends JpaRepository<CloudAccountRent, Long>{

	@Query("select R  from CloudAccountRent R where R.clientId = :clientId")
	List<CloudAccountRent> getAllRentAccount(@Param("clientId") Long id);
	
	@Query("select R from CloudAccountRent R where R.supplierAccId =:Id")
	List<CloudAccountRent>  getAllRentAccountBySupplierAccId(@Param("Id") Long id);
	
	

	
	
}
