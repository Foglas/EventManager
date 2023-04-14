package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.User;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  User findByUsername(String username);

  User findUserByEmailIgnoreCase(String email);

  User findById(long id);

  @Query(value = "CALL changeuserinfo(?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true)
  @Transactional
  @Modifying
  void updateUser(long id,String email,  String username,LocalDate dateOfBirth, String name,  String phone, String surname);


  @Query(value = "CALL resetpassword(?1,?2,?3)",nativeQuery = true)
  @Transactional
  @Modifying
  void updatePassword(long id, String oldPassword, String password);
}
