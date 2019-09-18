package kacper.bestplaces.places;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("likesRepository")
public interface LikesRepository extends JpaRepository<Likes, Long>{

	@Query(value="SELECT * FROM likes WHERE place=:place AND user=:user",nativeQuery = true)
	public Likes findByPlaceAndUser(@Param("place")String place,@Param("user")String user);
	
	@Query(value="SELECT * FROM likes WHERE comment IS NOT NULL AND place=:place",nativeQuery=true)
	public Page<Likes> findByName(@Param("place")String place,Pageable pg);
	
	@Modifying
	@Query(value="UPDATE likes SET likes=:status WHERE id=:id",nativeQuery=true)
	void changeRate(@Param("status")int status,@Param("id")int id);
	
	@Modifying
	@Query(value="UPDATE likes SET comment=:comment WHERE id=:id",nativeQuery=true)
	void changeComment(@Param("comment") String comment,@Param("id") int id);
	
	@Modifying
	@Query(value="DELETE FROM likes WHERE id=:id",nativeQuery=true)
	void undoLike(@Param("id")int id);

	@Query(value="UPDATE likes SET comment=:NULL WHERE id=:id",nativeQuery=true)
	void clearComment(@Param("id")int id);
	
}
