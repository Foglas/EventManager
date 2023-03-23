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
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //    @GetMapping(path = "/api/user/{username}")
    //    public List<User> getUserByUsername(@PathVariable() Long username) {
    //        return userService.findUserByID(username);
    //    }

    @GetMapping("api/dummy")
    public ResponseEntity<?> hovno() {

            Map<String, Object> response = new HashMap<>();
            response.put("message", "login vole");

            return ResponseEntity.status(HttpStatus.OK).body(response);
    }
 
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> authenticateUser(@RequestParam String username, @RequestParam String password) {
        try {

            String hashPassword = passwordEncoder.encode(password);

            // Authentication authentication = authenticationManager
            //         .authenticate(new UsernamePasswordAuthenticationToken(username, hashPassword));

            Optional<cz.uhk.fim.projekt.EventManager.Domain.User> optionalUser = userService.findUserByID((long) 2);

            cz.uhk.fim.projekt.EventManager.Domain.User user = optionalUser
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            //User user = (User) authentication.getPrincipal();

            String jwt = jwtUtil.generateToken(user.getUsername());

            Map<String, Object> response = new HashMap<>();

            response.put("message", "login vole");
            response.put("jwt", jwt);
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("password", user.getPassword());

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
