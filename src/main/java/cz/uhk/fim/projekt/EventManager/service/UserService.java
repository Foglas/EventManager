package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.Role;
import cz.uhk.fim.projekt.EventManager.Domain.Token.TokenBlackList;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.RoleRepo;
import cz.uhk.fim.projekt.EventManager.dao.TokenBlackListRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserDetailsRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.enums.Error;
import cz.uhk.fim.projekt.EventManager.enums.Roles;
import cz.uhk.fim.projekt.EventManager.service.serviceinf.UserServiceInf;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;

import java.time.LocalDate;
import java.util.*;

import cz.uhk.fim.projekt.EventManager.views.UserView;
import cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo.UserViewRepo;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService implements UserServiceInf {

    private UserRepo userRepo;
    private UserDetailsRepo userDetailsRepo;


    private RoleRepo roleRepo;


    private BCryptPasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil;

    private UserViewRepo userViewRepo;

    private TokenBlackListRepo tokenBlackListRepo;

    String telReg = "\\d\\d\\d[ ]\\d\\d\\d[ ]\\d\\d\\d$";
    String telReg2 = "\\d\\d\\d\\d\\d\\d\\d\\d\\d$";
    @Autowired
    public UserService(
            JwtUtil jwtUtil,
            UserRepo userRepo,
            UserDetailsRepo userDetailsRepo,
            UserViewRepo userViewRepo,
            RoleRepo roleRepo,
            TokenBlackListRepo blackListRepo
    ) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.userDetailsRepo = userDetailsRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userViewRepo = userViewRepo;
        this.roleRepo = roleRepo;
        this.tokenBlackListRepo = blackListRepo;
    }

    public Optional<User> findUserByID(Long id) {
        return userRepo.findById(id);
    }

    public User findUserByUserName(String username) {
        return userRepo.findByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepo.findUserByEmailIgnoreCase(email);
    }

    public void findUserRoleByEmail(String email){
        User user = userRepo.findById(49);
        Set<Role> roles = user.getRoles();
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    /**
     * Registers a new user and saving their details to the database
     *
     * @param user the User object containing the user's details to be saved
     * @return a ResponseEntity containing a success message if the user was saved successfully,
     * or an error message if any validation or database errors occurred
     */
    public ResponseEntity<?> save(User user) {
        // Check if passwords match
        if (!user.getPassword().equals(user.getPasswordAgain())) {
            return ResponseHelper.errorMessage(
                    "password_not_match",
                    "Passwords are not matching"
            );
        }

        // Check if name and surname are the same
        if (
                user
                        .getUserDetails()
                        .getName()
                        .equalsIgnoreCase(user.getUserDetails().getSurname())
        ) {
            return ResponseHelper.errorMessage(
                    "name_surname_same",
                    "Name and surname cannot be the same"
            );
        }
            String phone = user.getUserDetails().getPhone();
           if (!(phone.matches(telReg) || phone.matches(telReg2))) {
               return ResponseHelper.errorMessage(
                       "Wrong number format",
                       "Phone number has wrong format"
               );
           }
     /*
           // Check if phone number is at least 8 characters
        if (user.getUserDetails().getPhone().length() < 8) {
            return ResponseHelper.errorMessage(
                    "phone_number_lenght",
                    "Phone number must be at least 8 characters"
            );
        }

      */

        // Check if user with same email exists
        User dbUser = userRepo.findUserByEmailIgnoreCase(user.getEmail());

        if (dbUser != null) {
            return ResponseHelper.errorMessage(
                    "email_exists",
                    "User with this email address already exists"
            );
        }

        try {
            // Encode the user's password before saving it to the database
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Save the user's details to the UserDetails table
            userDetailsRepo.save(user.getUserDetails());

            Set<Role> roles = new HashSet<>();
            Optional<Role> role = roleRepo.findByType(Roles.ADMIN.name());

            if (role.isPresent()) {
                roles.add(role.get());
                user.setRoles(roles);
            } else {
                return ResponseHelper.errorMessage("role failed", "Cannot add role to user");
            }

            // Save the user to the Users table
            userRepo.save(user);

            // Return a success message
            return ResponseHelper.successMessage("User registered succesfully");
        } catch (Exception exception) {
            // Return an error message if an exception occurred while saving the user's details to the database
            return ResponseHelper.errorMessage(
                    "database_save_failed",
                    "Saving to database failed"
            );
        }
    }

    /**
     * Authenticates a user by checking their email and password against the database.
     *
     * @param email    the user's email address
     * @param password the user's password
     * @return a ResponseEntity containing a JWT token if authentication was successful,
     * or an error message if authentication failed
     */
    public ResponseEntity<?> authenticateUser(String email, String password) {
        try {
            User responseUser = findUserByEmail(email);

            if (responseUser == null) {
                return ResponseHelper.errorMessage(
                        "user_not_found",
                        "User was not found"
                );
            }

            if (!passwordEncoder.matches(password, responseUser.getPassword())) {
                return ResponseHelper.errorMessage(
                        "wrong_password",
                        "Password is incorrect"
                );
            }

            // Generate a JWT token for the user
            String jwt = jwtUtil.generateToken(responseUser.getEmail());

            // Return the JWT token in a success message
            return ResponseHelper.successMessage(jwt);
        } catch (Exception e) {
            // Return an error message if an exception occurred during authentication
            return ResponseHelper.errorMessage(
                    "authentication_failed",
                    "User authentication failed"
            );
        }
    }

    /**
     * Retrieves user details by ID.
     *
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing the user details if retrieval was successful,
     * or an error message if retrieval failed
     */
    public ResponseEntity<?> getUser(long id) {
        try {
            User responseUser = userRepo.findById(id);

            if (responseUser == null) {
                return ResponseHelper.errorMessage(
                        "user_not_found",
                        "User was not found"
                );
            }

            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("id", responseUser.getId());
            userDetails.put("username", responseUser.getUsername());
            userDetails.put("email", responseUser.getEmail());
            userDetails.put("name", responseUser.getUserDetails().getName());
            userDetails.put("surname", responseUser.getUserDetails().getSurname());
            userDetails.put("phone", responseUser.getUserDetails().getPhone());

            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            return ResponseHelper.errorMessage(
                    "user_details_failed",
                    "Retrieving user details failed"
            );
        }
    }

    /**
     * Retrieves user details from the current session using the provided authentication token.
     *
     * @param header the authorization header containing the authentication token
     * @return a ResponseEntity containing the user details if retrieval was successful,
     * or an error message if retrieval failed
     */
    public ResponseEntity<?> getUserFromCurrentSession(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseHelper.errorMessage(
                    "invalid_request",
                    "Authorization header is missing or invalid"
            );
        }

        String token = header.replace("Bearer ", "");
        String email = jwtUtil.getEmailFromToken(token);
        User responseUser = findUserByEmail(email);

        if (responseUser == null) {
            return ResponseHelper.errorMessage(
                    "user_not_found",
                    "User was not found"
            );
        }

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", responseUser.getId());
        userDetails.put("username", responseUser.getUsername());
        userDetails.put("email", responseUser.getEmail());
        userDetails.put("name", responseUser.getUserDetails().getName());
        userDetails.put("surname", responseUser.getUserDetails().getSurname());
        userDetails.put("phone", responseUser.getUserDetails().getPhone());
        userDetails.put(
                "birthDate",
                responseUser.getUserDetails().getDateOfBirth().toString()
        );

        return ResponseEntity.ok(userDetails);
    }

    public List<UserView> getUsersInfo() {
        return userViewRepo.findAll();
    }


    @Transactional
    public List<Role> getUsersRole(User user) {
        Set<Role> roles = user.getRoles();
        List<Role> usersRoles = new ArrayList<>();
        usersRoles.addAll(roles);
        return usersRoles;
    }


    public ResponseEntity<?> deleteUser(long id, HttpServletRequest request) {
       User user = userRepo.findById(id);
        User userFromToken = jwtUtil.getUserFromRequest(request, userRepo);
       if (user == null){
        return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "user not found");
        } else if (user.getId() != userFromToken.getId()){
           return ResponseHelper.errorMessage(Error.NO_ACCESS.name(), "you dont have access to delete user");
       } else {
           userRepo.delete(user);
           return ResponseHelper.successMessage("user deleted");
       }
    }


    public ResponseEntity<?> updateUser(Map<String, String> user, HttpServletRequest httpServletRequest){
        User userFromToken = jwtUtil.getUserFromRequest(httpServletRequest,userRepo);

        String username = user.get("username");
        String email = user.get("email");
        String phone = user.get("phone");
        String surname = user.get("surname");
        String dateOfBirth = user.get("dateOfBirth");
        String name = user.get("name");
        LocalDate localDateOfBirth = LocalDate.parse(dateOfBirth);

        if (user==null){
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "user not found");
        }


        userRepo.updateUser(userFromToken.getUserDetails().getId(),email,username,localDateOfBirth,name ,phone,surname);

        return ResponseHelper.successMessage(jwtUtil.generateToken(email));
    }

    public ResponseEntity<?> updatePassword(Map<String, String> body, HttpServletRequest httpServletRequest){
       User userFromToken = jwtUtil.getUserFromRequest(httpServletRequest,userRepo);

        String oldPassword = body.get("oldPassword");
        if (oldPassword == null){
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "null old password");
        }

        if (!passwordEncoder.matches(oldPassword, userFromToken.getPassword())){
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "old password is incorrect");
        }

       String password = body.get("password");
       if (password == null){
           return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "null password");
       }
        String password2 = body.get("password2");
       if (password2 == null){
           return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "null second password");
       }

       if (password.equals(userFromToken.getPassword())){
           return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "new and old password are same");
       }

       if (!password.equals(password2)){
           return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "passwords is not same");
       }

       userRepo.updatePassword(userFromToken.getId(), userFromToken.getPassword(), passwordEncoder.encode(password));
        return ResponseHelper.successMessage("password changed");
    }

    public ResponseEntity<?> logout(HttpServletRequest request){
         String header = request.getHeader("Authorization");
         String token = header.replace("Bearer ", "");

         TokenBlackList tokenBlackList = new TokenBlackList(token);
         tokenBlackListRepo.save(tokenBlackList);
         return ResponseHelper.successMessage("logout");
    }

    public boolean existsTokenInBlackList(String token){
        if (tokenBlackListRepo.existsInBlackListByToken(token).isPresent()){
            return true;
        } else {
            return false;
        }
    }

}
