package kacper.bestplaces.places;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("placesRepository")
public interface PlacesRepository extends JpaRepository<Places, Long>{

	public Places findByName(String name);
	
	public Page<Places> findByType(String type,Pageable pg);
	
	@Modifying
	@Query(value="UPDATE Places SET up=:up WHERE name=:name", nativeQuery=true)
	void changeUp(@Param("up") int up,@Param("name")String name);
	
	@Modifying
	@Query(value="UPDATE Places  SET down=:down WHERE name=:name", nativeQuery=true)
	void changeDown(@Param("down") int up,@Param("name")String name);
	
	@Query(value="SELECT * FROM Places WHERE loc LIKE %:param%", nativeQuery=true)
	Page<Places> findInLoc(@Param("param") String param, Pageable pg);
	
	@Query(value="SELECT * FROM Places WHERE loc LIKE %:param% AND type=:type", nativeQuery=true)
	Page<Places> findTypeInLoc(@Param("param") String param,@Param("type") String type, Pageable pg);
}
