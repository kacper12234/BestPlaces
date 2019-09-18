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
	@Query(value="UPDATE places SET name=:newname, loc=:newloc, descrp=:newdescrp WHERE link=:name", nativeQuery=true)
	void placeUpdate(@Param("name") String name, @Param("newname") String newname, @Param("newloc") String newloc, @Param("newdescrp") String newdescrp);
	
	@Modifying
	@Query(value="UPDATE places SET up=:up WHERE name=:name", nativeQuery=true)
	void changeUp(@Param("up") int up,@Param("name")String name);
	
	@Modifying
	@Query(value="UPDATE places  SET down=:down WHERE name=:name", nativeQuery=true)
	void changeDown(@Param("down") int up,@Param("name")String name);
	
	@Modifying
	@Query(value="DELETE FROM places WHERE name=:name",nativeQuery=true)
	void deletePlace(@Param("name") String name);
	
	@Modifying
	@Query(value="UPDATE places SET id=:nid WHERE id=:id",nativeQuery=true)
	void updateId(@Param("nid") long nid,@Param("id") long id);
	
	@Query(value="SELECT * FROM places WHERE loc LIKE %:param%", nativeQuery=true)
	Page<Places> findInLoc(@Param("param") String param, Pageable pg);
	
	@Query(value="SELECT * FROM places WHERE loc LIKE %:param% AND type=:type", nativeQuery=true)
	Page<Places> findTypeInLoc(@Param("param") String param,@Param("type") String type, Pageable pg);
}
