package cz.uhk.fim.projekt.EventManager.Controllers;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.Domain.UserDetails;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    @Autowired
    public RegistrationController(UserService userRepo){
        this.userService = userRepo;
    }

    @PostMapping("register")
public ResponseEntity<?> registerUser(
@RequestParam String email,
@RequestParam String username,
@RequestParam String password,
@RequestParam String passwordAgain,
@RequestParam String dateOfBirth,
@RequestParam String name,
@RequestParam String surname,
@RequestParam String phone
) {

    

    if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")) {
        return ResponseEntity.badRequest().body("Password is not strong enough.");
    }

    

// check if passwords match
if (!password.equals(passwordAgain)) {
return ResponseEntity.badRequest().body("Passwords do not match");
}

// check if name and surname are the same
if (name.equalsIgnoreCase(surname)) {
return ResponseEntity.badRequest().body("Name and surname cannot be the same");
}

// check if phone number is at least 8 characters
if (phone.length() < 8) {
    return ResponseEntity.badRequest().body("Phone number must be at least 8 characters");
}

 LocalDate dob = null;
 if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
     try {
         dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
     } catch (DateTimeParseException ex) {
         return ResponseEntity.badRequest().body("Invalid date format");
     }
 }
    

 UserDetails userDetails = new UserDetails();

userDetails.setDateOfBirth(dob);
userDetails.setName(name);
userDetails.setSurname(surname);
userDetails.setPhone(phone);

User user = new User(email, username, password);

user.setUserDetails(userDetails);

boolean saveResult = userService.save(user);

if (saveResult) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("password", user.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } else {
        return ResponseEntity.badRequest().body("Save was unsuccesful");
    }
}
    
}