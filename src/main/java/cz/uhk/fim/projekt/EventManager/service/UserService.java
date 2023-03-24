package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.UserDetailsRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.service.serviceinf.UserServiceInf;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserServiceInf {

    UserRepo userRepo;
    UserDetailsRepo userDetailsRepo;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, UserDetailsRepo userDetailsRepo){
        this.userRepo = userRepo;
        this.userDetailsRepo = userDetailsRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Optional<User> findUserByID(Long id){
       return userRepo.findById(id);
    }

    public ResponseEntity<?> save(User user){
        Map<String, Object> response = new HashMap<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDetailsRepo.save(user.getUserDetails());
        userRepo.save(user);


        response.put("message", "User registered successfully");
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("password", user.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public User findUserByUserName(String username) {
        return userRepo.findUserByUsername(username);
    }
}
