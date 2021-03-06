package kacper.bestplaces.repository;

import kacper.bestplaces.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.email= :email")
    void updateUserPassword(@Param("newPassword") String password, @Param("email") String email);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.activationCode= :code")
    void resetUserPassword(@Param("newPassword") String password, @Param("code") String code);

    @Modifying
    @Query("UPDATE User u SET u.name = :newName, u.lastName = :newLastName, u.email = :newEmail WHERE u.id= :id")
    void updateUserProfile(@Param("newName") String newName, @Param("newLastName") String newLastName,
                           @Param("newEmail") String newEmail, @Param("id") Integer id);

    @Modifying
    @Query("UPDATE User u SET u.active = :activeParam WHERE u.activationCode= :activationCode")
    void updateActivation(@Param("activeParam") int active, @Param("activationCode") String activationCode);
}