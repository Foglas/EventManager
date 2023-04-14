package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import cz.uhk.fim.projekt.EventManager.views.UserView;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("auth/user/{id}")
  public ResponseEntity<?> getUser(@PathVariable long id) {
    return userService.getUser(id);
  }

  @GetMapping("auth/currentUser")
  public ResponseEntity<?> getUserFromSession(HttpServletRequest request) {
    String header = request.getHeader("Authorization");

    return userService.getUserFromCurrentSession(header);
  }


  @PostMapping(path = "user/login" , consumes = {"application/json"})
  public ResponseEntity<?> authenticateUser(@RequestBody User requestBody) {
    return userService.authenticateUser(
            requestBody.getEmail(),
            requestBody.getPassword()
    );
  }

  @GetMapping("/auth/admin/usersInfo")
  public List<UserView> getUsersInfo(){
    return userService.getUsersInfo();
  }

  @PostMapping(path = "user/register"  , consumes = {"application/json"})
  public ResponseEntity<?> registerUser(@RequestBody User requestBody) {
    return userService.save(requestBody);
  }

  @DeleteMapping("/auth/user/{id}/delete")
  public ResponseEntity<?> deleteUser(@PathVariable("id") long id, HttpServletRequest request){
  return   userService.deleteUser(id, request);
  }

  @PostMapping("/auth/user/update")
  public ResponseEntity<?> updateUser(@RequestBody Map<String, String> body, HttpServletRequest httpServletRequest){
   return userService.updateUser(body, httpServletRequest);
  }

  @PostMapping("/auth/user/resetPassword")
  public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> body, HttpServletRequest httpServletRequest){
    return userService.updatePassword(body, httpServletRequest);
  }

  @PostMapping("/auth/user/logout")
  public ResponseEntity<?> logout(HttpServletRequest httpServletRequest){
    return userService.logout(httpServletRequest);
  }
}
