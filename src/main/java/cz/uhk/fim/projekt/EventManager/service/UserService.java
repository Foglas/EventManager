package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.UserDetailsRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.service.serviceinf.UserServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public boolean save(User user) {
        try {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDetailsRepo.save(user.getUserDetails());

        userRepo.save(user);

        return true;

        } catch (Exception exception) {
            return false;
        }
    }

    public User findUserByUserName(String username) {
          return userRepo.findByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
}
