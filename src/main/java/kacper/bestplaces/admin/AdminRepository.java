package kacper.bestplaces.admin;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kacper.bestplaces.user.User;


@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<User, Integer> {

	User findUserById(int id);
	
	@Modifying
	@Query(value="UPDATE user SET active=:intActive WHERE id=:id", nativeQuery=true)
	void updateActivationUser(@Param("intActive")int active ,@Param("id")int id);
	
	@Modifying
	@Query(value = "UPDATE user_role SET role_id = :roleId WHERE user_id= :id", nativeQuery = true)
	void updateRoleUser(@Param("roleId") int nrRoli, @Param("id") int id);
	
	@Query(value="SELECT * FROM user WHERE name LIKE %:param% OR last_name LIKE %:param% OR email LIKE %:param%", nativeQuery=true)
	Page<User> findAllSearch(@Param("param") String param, Pageable pg);
	
	@Modifying
	@Query(value="DELETE FROM user_role WHERE user_id=:id",nativeQuery=true)
	void deleteUserFromUserRole(@Param("id") int id);
	
	@Modifying
	@Query(value="DELETE FROM user WHERE user_id=:id",nativeQuery=true)
	void deleteUserFromUser(@Param("id") int id);
}