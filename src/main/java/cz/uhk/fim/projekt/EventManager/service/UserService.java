package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Role;
import cz.uhk.fim.projekt.EventManager.Domain.Token.TokenBlackList;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.RoleRepo;
import cz.uhk.fim.projekt.EventManager.dao.TokenBlackListRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserDetailsRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.enums.Error;
import cz.uhk.fim.projekt.EventManager.enums.Roles;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;

import java.time.LocalDate;
import java.util.*;

import cz.uhk.fim.projekt.EventManager.views.UserView;
import cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo.UserViewRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Třída poskytující metody pro obsluhu požadavků týkajících se uživatele
 */

@Service
public class UserService {

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
    public UserService(JwtUtil jwtUtil, UserRepo userRepo, UserDetailsRepo userDetailsRepo, UserViewRepo userViewRepo, RoleRepo roleRepo, TokenBlackListRepo blackListRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.userDetailsRepo = userDetailsRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userViewRepo = userViewRepo;
        this.roleRepo = roleRepo;
        this.tokenBlackListRepo = blackListRepo;
    }

    /**
     * Metoda vrátí uživatele podle uživatelského jména
     *
     * @param username uživatelské jméno
     */
    public User findUserByUserName(String username) {
        return userRepo.findByUsername(username);
    }

    /**
     * Metoda vrátí uživatele podle emailu
     *
     * @param email email uživatele
     */
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmailIgnoreCase(email);
    }

    public void findUserRoleByEmail(String email) {
        User user = userRepo.findById(49);
        Set<Role> roles = user.getRoles();
    }


    /**
     * Metoda zaregistruje nového uživatele a uloží jeho informace do databáze
     * Kontroluje zda vstupní údaje jsou validní
     * @param user objekt uživatele obsahující všechny informace uživatele
     * @return ResponseEntity obsahující zprávu o úspěchu nebo chybě, podle toho zda se registrace podařila
     */
    public ResponseEntity<?> save(User user) {
        // Check if passwords match
        if (!user.getPassword().equals(user.getPasswordAgain())) {
            return ResponseHelper.errorMessage(
                    "password_not_match",
                    "Passwords are not matching"
            );
        }

        if (user.getEmail() == "") {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "email is not fill");
        }

        if (user.getUsername() == "") {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "username is not fill");
        }
        if (user.getPassword() == "") {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "password is not fill");
        }


        // Check if name and surname are the same
        if (user.getUserDetails().getName() != "" || user.getUserDetails().getSurname() != "") {
            if (user.getUserDetails().getName().equalsIgnoreCase(user.getUserDetails().getSurname())) {
                return ResponseHelper.errorMessage(
                        "name_surname_same",
                        "Name and surname cannot be the same"
                );
            }
        }
        if (user.getUserDetails().getName() == "") {
            user.getUserDetails().setName(null);
        }

        if (user.getUserDetails().getSurname() == "") {
            user.getUserDetails().setSurname(null);
        }


        String phone = user.getUserDetails().getPhone();
        if (!(phone.matches(telReg) || phone.matches(telReg2))) {
            return ResponseHelper.errorMessage(
                    "Wrong number format",
                    "Phone number has wrong format"
            );
        }


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
            Optional<Role> role = roleRepo.findByType(Roles.USER.name());

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
            return ResponseHelper.errorMessage(
                    Error.INVALID_ARGUMENTS.name(),
                    "date of birth not fill"
            );
        }
    }

    /**
     * Autentizace uživatele kontrolou jeho emailu a hesla.
     * Kontroluje vstupní údaje zda jsou validní
     * @param email    email uživatele
     * @param password heslo uživatele
     * @return ResponseEntity obsahující JWT token pokud je autentizace úspěšná. Když autentizace neprojde, vrátí chybovou hlášku.
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
     * Vrací uživatele podle příslušného ID. Kontroluje, zda id existuje.
     *
     * @param id ID uživatele
     * @return ResponseEntity obsahující újdaje uživatele, pokud je úspěšná,
     * chybovou hlášku pokud uživatel s daným ID neexistuje.
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
     * Vráti informace o uživateli z aktuílní relace, získané pomocí tokenu. Kontroluje
     * validnost údajů.
     *
     * @param header hlavička obsahující autorizační token
     * @return ResponseEntity obsahující údaje o uživateli, pokud je autorizace úspěšná, chybovou hlášku pokud je neúspěšná
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

    /**
     * Metoda vrátí seznam pohledů UserView
     */
    public List<UserView> getUsersInfo() {
        return userViewRepo.findAll();
    }

    /**
     * Metoda vrátí roli uživatele
     *
     * @param user uživatel
     * @return role uživatele - enumerace
     */
    @Transactional
    public List<Role> getUsersRole(User user) {
        Set<Role> roles = user.getRoles();
        List<Role> usersRoles = new ArrayList<>();
        usersRoles.addAll(roles);
        return usersRoles;
    }

    /**
     * Metoda smaže veškeré údaje o uživateli - poznámka: metoda smaže údaje z tabulky Users,
     * trigger funkce databáze se postará o tabulku UserDetails
     *
     * @param id      ID uživatele
     * @param request Server request
     * @return vrací hlášku o úpěšném smazání, pokud k němu dojde. Jinak vrací chybovou hlášku
     */
    public ResponseEntity<?> deleteUser(long id, HttpServletRequest request) {
        User user = userRepo.findById(id);
        User userFromToken = jwtUtil.getUserFromRequest(request, userRepo);
        if (user == null) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "user not found");
        } else if (user.getId() != userFromToken.getId()) {
            return ResponseHelper.errorMessage(Error.NO_ACCESS.name(), "you dont have access to delete user");
        } else {
            userRepo.delete(user);
            return ResponseHelper.successMessage("user deleted");
        }
    }

    /**
     * Metoda aktualizuje údaje o uživateli
     *
     * @param user               objekt uživatele obsahující nové údaje uživatele
     * @param httpServletRequest Server request
     * @return vrací nový autorizační token uživatele, pokud se podaří aktualizace údajů, jinak vrací chybovou hlášku
     */
    public ResponseEntity<?> updateUser(Map<String, String> user, HttpServletRequest httpServletRequest) {
        User userFromToken = jwtUtil.getUserFromRequest(httpServletRequest, userRepo);

        String username = user.get("username");
        String email = user.get("email");
        String phone = user.get("phone");
        String surname = user.get("surname");
        String dateOfBirth = user.get("dateOfBirth");
        String name = user.get("name");
        LocalDate localDateOfBirth = LocalDate.parse(dateOfBirth);

        if (user == null) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "user not found");
        }


        userRepo.updateUser(userFromToken.getUserDetails().getId(), email, username, localDateOfBirth, name, phone, surname);

        return ResponseHelper.successMessage(jwtUtil.generateToken(email));
    }

    /**
     * Metoda nastaví uživateli nové heslo
     *
     * @param body               objekt obsahující staré a nové heslo
     * @param httpServletRequest Server request
     * @return vrací hlášku o úspěchu, pokud se heslo podařilo změnit, jinak vrací chybovou hlášku
     */
    public ResponseEntity<?> updatePassword(Map<String, String> body, HttpServletRequest httpServletRequest) {
        User userFromToken = jwtUtil.getUserFromRequest(httpServletRequest, userRepo);

        String oldPassword = body.get("oldPassword");
        if (oldPassword == null) {
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "null old password");
        }

        if (!passwordEncoder.matches(oldPassword, userFromToken.getPassword())) {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "old password is incorrect");
        }

        String password = body.get("password");
        if (password == null) {
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "null password");
        }
        String password2 = body.get("password2");
        if (password2 == null) {
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "null second password");
        }

        if (password.equals(userFromToken.getPassword())) {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "new and old password are same");
        }

        if (!password.equals(password2)) {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "passwords is not same");
        }

        userRepo.updatePassword(userFromToken.getId(), userFromToken.getPassword(), passwordEncoder.encode(password));
        return ResponseHelper.successMessage("password changed");
    }

    /**
     * Metoda odhlásí uživatele a přidá jeho autentizační token do blackListu
     *
     * @param request Server request
     * @return vrací hlášku o provedení logoutu
     */
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.replace("Bearer ", "");

        TokenBlackList tokenBlackList = new TokenBlackList(token);
        tokenBlackListRepo.save(tokenBlackList);
        return ResponseHelper.successMessage("logout");
    }

    /**
     * Metoda zkontroluje, zda je token na blackListu
     *
     * @param token String hodnota tokenu
     * @return boolean hodnota, podle toho, zda je token na blackListu nebo není
     */
    public boolean existsTokenInBlackList(String token) {
        if (tokenBlackListRepo.existsInBlackListByToken(token).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

}
