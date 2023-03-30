package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping("user/login")
  public ResponseEntity<?> authenticateUser(@RequestBody User requestBody) {
    return userService.authenticateUser(
            requestBody.getEmail(),
            requestBody.getPassword()
    );
  }

  @PostMapping("user/register")
  public ResponseEntity<?> registerUser(@RequestBody User requestBody) {
    return userService.save(requestBody);
  }

}
