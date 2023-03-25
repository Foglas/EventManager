package cz.uhk.fim.projekt.EventManager.Controllers;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;

import java.util.HashMap;
import java.util.Map;

import cz.uhk.fim.projekt.EventManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userRepo){
        this.userService = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User userPost) {
     return    userService.save(userPost);
    }
    
}