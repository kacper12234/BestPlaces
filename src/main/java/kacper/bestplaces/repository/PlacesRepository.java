package kacper.bestplaces.repository;

import kacper.bestplaces.model.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacesRepository extends JpaRepository<Place, Integer>{

	Place findByName(String name);
	
	Page<Place> findByType(String type, Pageable pg);

	@Modifying
	@Query(value="UPDATE places SET name=:newname, loc=:newloc, descrp=:newdescrp WHERE id=:id",nativeQuery=true)
	void placeUpdate(@Param("id") Integer id, @Param("newname") String newname, @Param("newloc") String newloc, @Param("newdescrp") String newdescrp);
	
	@Modifying
	@Query(value="UPDATE places SET id=:newId WHERE id=:id",nativeQuery=true)
	void updateId(@Param("newId") Integer newId,@Param("id") Integer id);
	
	@Query(value="SELECT * FROM places WHERE loc LIKE %:param%",nativeQuery=true)
	Page<Place> findInLoc(@Param("param") String param, Pageable pg);
	
	@Query(value="SELECT * FROM places WHERE loc LIKE %:param% AND type=:type", nativeQuery=true)
	Page<Place> findTypeInLoc(@Param("param") String param, @Param("type") String type, Pageable pg);
}
