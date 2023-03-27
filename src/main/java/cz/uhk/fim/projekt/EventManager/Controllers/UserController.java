package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("login")
  public ResponseEntity<?> authenticateUser(@RequestBody User requestBody) {
    return userService.authenticateUser(
      requestBody.getEmail(),
      requestBody.getPassword()
    );
  }

  @PostMapping("register")
  public ResponseEntity<?> registerUser(@RequestBody User requestBody) {
    return userService.save(requestBody);
  }
}
