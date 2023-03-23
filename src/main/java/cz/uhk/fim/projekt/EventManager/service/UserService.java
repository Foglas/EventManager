package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.UserDetailsRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.service.serviceinf.UserServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInf {

    UserRepo userRepo;
    UserDetailsRepo userDetailsRepo;

    @Autowired
    public UserService(UserRepo userRepo, UserDetailsRepo userDetailsRepo){
        this.userRepo = userRepo;
        this.userDetailsRepo = userDetailsRepo;
    }

    public Optional<User> findUserByID(Long id){
       return userRepo.findById(id);
    }

    public void save(User user){
        userDetailsRepo.save(user.getUserDetails());
        userRepo.save(user);
    }

    public User findUserByUserName(String username) {
        return userRepo.findUserByUsername(username);
    }
}
