package cz.uhk.fim.projekt.EventManager.Controllers;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> registerUser(@RequestParam String email, @RequestParam String username,
            @RequestParam String password) {

        // if (userRepository.existsByUsername(username)) {
        //     return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken");
        // }
        // if (userRepository.existsByEmail(email)) {
        //     return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already in use");
        // }


        Map<String, Object> response = new HashMap<>();


        User user = new User(email, username, passwordEncoder.encode(password));

        userRepository.save(user);

         response.put("message", "User registered successfully");
         response.put("id", user.getId());
         response.put("username", user.getUsername());
         response.put("email", user.getEmail());
        response.put("password", user.getPassword());

         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}