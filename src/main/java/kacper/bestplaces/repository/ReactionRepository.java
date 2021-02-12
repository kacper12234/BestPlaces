package kacper.bestplaces.repository;

import kacper.bestplaces.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer>{

	
	@Modifying
	@Query(value="UPDATE reactions SET rate=:rate WHERE id=:id",nativeQuery=true)
	void changeRate(@Param("rate")String type,@Param("id")Integer id);
	
	@Modifying
	@Query(value="UPDATE reactions SET comment=:comment WHERE id=:id",nativeQuery=true)
	void changeComment(@Param("comment") String comment,@Param("id") Integer id);
	
	@Modifying
	@Query(value="UPDATE reactions SET rate=NULL WHERE id=:id",nativeQuery=true)
	void undoRate(@Param("id")Integer id);

	@Modifying
	@Query(value="UPDATE reactions SET comment=NULL WHERE id=:id",nativeQuery=true)
	void clearComment(@Param("id")Integer id);
}
