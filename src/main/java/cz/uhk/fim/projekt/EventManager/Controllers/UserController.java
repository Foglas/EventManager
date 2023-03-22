package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.AuthenticationException;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
   
    @Autowired
private JwtUtil jwtUtil;

@Autowired
private AuthenticationManager authenticationManager;

   
    private UserService userService;

   @Autowired
   public UserController(UserService userService){
       this.userService = userService;
   }

   @GetMapping(path = "/api/user/{username}")
   public List<User> getUserByUsername(@PathVariable() Long username) {
       return userService.findUserByID(username);
   }
   
   @PostMapping("/api/auth/login")
public ResponseEntity<?> authenticateUser(@RequestParam String username, @RequestParam String password) {
    try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).build();
    } catch (AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}

}
