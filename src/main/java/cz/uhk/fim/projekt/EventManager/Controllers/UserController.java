package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.PhotoService;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import cz.uhk.fim.projekt.EventManager.views.UserView;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  Třída obsahující metody na příjímaní požadavků na url týkajících se akcí ohledně usera.
 */
@RestController
@RequestMapping("/api")
public class UserController {

  private UserService userService;
  private PhotoService photoService;

  @Autowired
  public UserController(UserService userService, PhotoService photoService) {
    this.userService = userService;
    this.photoService = photoService;
  }

  /**
   * Přijme dotaz na url /api/auth/user/{id}. Slouží pro získání usera z databáze.
   * Vyžaduje autorizaci. Id je id usera.
   * @return http status a message, pokud je request neúspěšný nebo http status a user,
   * pokud je úspěšný
   */
  @GetMapping("auth/user/{id}")
  public ResponseEntity<?> getUser(@PathVariable long id) {
    return userService.getUser(id);
  }

  /**
   * Přijme dotaz na url /api/auth/currentUser. Slouží pro získání přihlášeného usera z databáze.
   * Vyžaduje autorizaci
   * @return http status a message, pokud je request neúspěšný nebo http status a user,
   * pokud je úspěšný
   */
  @GetMapping("auth/currentUser")
  public ResponseEntity<?> getUserFromSession(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    return userService.getUserFromCurrentSession(header);
  }

  /**
   * Přijme dotaz na url /api/user/login. Slouží pro přihlášení uživatele. Informace potřebné
   * k přihlášení jsou obsažené v body.
   * @return http status a message, pokud je request úspěšný, message obsahuje token
   */
  @PostMapping(path = "user/login" , consumes = {"application/json"})
  public ResponseEntity<?> authenticateUser(@RequestBody User requestBody) {
    return userService.authenticateUser(
            requestBody.getEmail(),
            requestBody.getPassword()
    );
  }

  /**
   * Přijme dotaz na url /api/auth/admin/usersInfo. Slouží pro získání všech userview.
   * Vyžaduje autorizaci a roli admina
   * @return http status a list všech userview
   */
  @GetMapping("/auth/admin/usersInfo")
  public List<UserView> getUsersInfo(){
    return userService.getUsersInfo();
  }

  /**
   * Přijme dotaz na url /user/register. Slouží pro registraci usera. Informace potřebné k registraci
   * jsou obsažené v body requestu.
   * @return http status a message
   */
  @PostMapping(path = "user/register"  , consumes = {"application/json"})
  public ResponseEntity<?> registerUser(@RequestBody User requestBody) {
    return userService.save(requestBody);
  }

  /**
   * Přijme dotaz na url /auth/user/{id}/delete. Id je id usera. Vyžaduje autorizaci.
   * Slouží pro zrušení účtu uživatele. User ke smazání se získá z tokenu, který se nachází v header
   * @return http status a message
   */
  @DeleteMapping("/auth/user/{id}/delete")
  public ResponseEntity<?> deleteUser(@PathVariable("id") long id, HttpServletRequest request){
  return   userService.deleteUser(id, request);
  }

  /**
   * Přijme dotaz na url /auth/user/update. Vyžaduje autorizaci. Slouží pro update účtu uživatele.
   * Informace k updatu se nachází v body requestu
   * @return http status a message
   */
  @PutMapping("/auth/user/update")
  public ResponseEntity<?> updateUser(@RequestBody Map<String, String> body, HttpServletRequest httpServletRequest){
   return userService.updateUser(body, httpServletRequest);
  }

  /**
   * Přijme dotaz na url /auth/user/resetPassword. Vyžaduje autorizaci. Slouží pro reset hesla účtu uživatele.
   * Informace k resetu se nachází v body requestu. User se vybere podle tokenu v headeru.
   * @return http status a message
   */
  @PostMapping("/auth/user/resetPassword")
  public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> body, HttpServletRequest httpServletRequest){
    return userService.updatePassword(body, httpServletRequest);
  }

  /**
   * Přijme dotaz na url /auth/user/logout. Vyžaduje autorizaci. Slouží pro odhlášení uživatele.
   * User se vybere podle tokenu v headeru.
   * @return http status a message
   */
  @PostMapping("/auth/user/logout")
  public ResponseEntity<?> logout(HttpServletRequest httpServletRequest){
    return userService.logout(httpServletRequest);
  }

  /**
   * Přijme dotaz na url /auth/user/photo/save. Vyžaduje autorizaci. Slouží pro uložení profilové
   * fotky uživatele. Informace potřebné k uložení jsou v body requestu. User se vybere podle tokenu
   * v headeru.
   * @return http status a message
   */
  @PostMapping("/auth/user/photo/save")
  public ResponseEntity<?> uploadPhoto(@RequestBody Map<String, String> body, HttpServletRequest request){
      return photoService.uploadPhoto(body,request);
  }

  /**
   * Přijme dotaz na url /user/photo. Slouží k získání uložení profilové
   * fotky uživatele. Informace potřebné k uložení jsou v body requestu. User se vybere podle tokenu
   * v headeru.
   * @return http status a message
   */
  @GetMapping("/user/{id}/photo")
  public ResponseEntity<?> getPhoto(@PathVariable("id") long id){
    return photoService.getPhoto(id);
  }
}
