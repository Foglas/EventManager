package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
   private UserService userService;

   @Autowired
   public UserController(UserService userService){
       this.userService = userService;
   }

   @GetMapping(path = "/api/user/{username}")
   public List<User> getUserByUsername(@PathVariable() Long username){
       return userService.findUserByID(username);
   }

}
