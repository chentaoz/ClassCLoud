package cloud.distrFileSys.master.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileReps extends JpaRepository<File,Long> {
	List<File> findByPath(String s);
	
	@Query("select f from File f where  f.userId=:uid and f.path =:path")
	List<File> findByUserandPath (@Param("uid") Long uid, @Param("path") String path);

}
