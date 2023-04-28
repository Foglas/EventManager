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

/**
 * Interface poskytuje metody pro práci s tabulkou users
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  /**
   * Metoda vrátí uživatele podle uživatelského jména
   * @param username uživatelské jméno
   */
  User findByUsername(String username);
  /**
   * Metoda vrátí uživatele podle emailu
   * @param email email uživatele
   */
  User findUserByEmailIgnoreCase(String email);
  /**
   * Metoda vrátí uživatele podle ID
   * @param id ID uživatele
   */
  User findById(long id);
  /**
   * Metoda volá metodu changeuserinfo, která upraví uživatelské údaje
   * @param username uživatelské jméno
   * @param id ID uživatele
   * @param email email uživatele
   * @param name jméno uživatele
   * @param phone telefonní číslo uživatele
   * @param surname přijmení uživatele
   * @param dateOfBirth datum narození uživatele
   */
  @Query(value = "CALL changeuserinfo(?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true)
  @Transactional
  @Modifying
  void updateUser(long id,String email,  String username,LocalDate dateOfBirth, String name,  String phone, String surname);

  /**
   * Metoda volá metodu resetpassword, která slouží k resetu hesla
   * @param id ID uživatele, jehož heslo měníme
   * @param password nové heslo
   * @param oldPassword staré heslo
   */
  @Query(value = "CALL resetpassword(?1,?2,?3)",nativeQuery = true)
  @Transactional
  @Modifying
  void updatePassword(long id, String oldPassword, String password);
}
