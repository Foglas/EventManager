package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {


    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.authenticationManager = authenticationManager;
    }


    public ResponseEntity<?> authenticateUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            User responseUser = new User(username, username, password);


            String jwt = jwtUtil.generateToken(user.getUsername());

            Map<String, Object> response = new HashMap<>();

            response.put("message", "login vole");
            response.put("jwt", jwt);
            response.put("username", responseUser.getUsername());
            response.put("email", responseUser.getEmail());
            response.put("password", responseUser.getPassword());

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
