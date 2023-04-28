package cz.uhk.fim.projekt.EventManager.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Třída zajišťující práci s tokenem.
 */
@Component
public class JwtUtil {

  private final String SECRET = "secret_key";
  private final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds

  /**
   * Metoda generuje token na základě SECRET, EXPIRATION-TIME a username.
   * @param username username uživatele, jemuž je přidělen token
   * @return vrátí token
   */
  public String generateToken(String username) {
    return JWT
      .create()
      .withSubject(username)
      .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .sign(Algorithm.HMAC512(SECRET.getBytes()));
  }

  /**
   * Metoda starající se o zjištění emailu z tokenu
   * @param token token, z kterého se má zjistit email
   * @return email uživatele, jemuž patřil token
   */
  public String getEmailFromToken(String token) {
    return JWT
      .require(Algorithm.HMAC512(SECRET.getBytes()))
      .build()
      .verify(token)
      .getSubject();
  }

  /**
   * Metoda která zjišťuje usera z requestu
   * @param request request
   * @param userRepo user repository
   * @return user, který vyvolal request
   */
  public User getUserFromRequest(HttpServletRequest request, UserRepo userRepo){
    String token = request.getHeader("Authorization");
    token = token.replace("Bearer ", "");

    String email = getEmailFromToken(token);
    return userRepo.findUserByEmailIgnoreCase(email);
  }
}
