package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.AuthenticationService;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    private BCryptPasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private UserService userService;

    private AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.authenticationService = authenticationService;
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
    public ResponseEntity<?> authenticateUser(@RequestBody User userPost) {
        return authenticationService.authenticateUser(userPost);
    }
}
