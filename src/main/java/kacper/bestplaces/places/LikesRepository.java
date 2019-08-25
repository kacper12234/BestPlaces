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

	@Query(value="SELECT * FROM Likes l WHERE l.place=:place AND l.user=:user",nativeQuery = true)
	public Likes findByPlaceAndUser(@Param("place")String place,@Param("user")String user);
	
	@Query(value="SELECT * FROM likes WHERE comment IS NOT NULL AND place=:place",nativeQuery=true)
	public Page<Likes> findByName(@Param("place")String place,Pageable pg);
	
	@Modifying
	@Query(value="UPDATE Likes l SET l.likes=:status WHERE l.id=:id",nativeQuery=true)
	void changeRate(@Param("status")int status,@Param("id")int id);
	
	@Modifying
	@Query(value="UPDATE Likes l SET l.comment=:comment WHERE l.id=:id",nativeQuery=true)
	void changeComment(@Param("comment") String comment,@Param("id") int id);
	
	@Modifying
	@Query(value="DELETE FROM Likes WHERE id=:id",nativeQuery=true)
	void undoLike(@Param("id")int id);

}
