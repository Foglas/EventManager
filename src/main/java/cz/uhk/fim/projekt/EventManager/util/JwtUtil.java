package cz.uhk.fim.projekt.EventManager.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private final String SECRET = "secret_key";
  private final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds

  public String generateToken(String username) {
    return JWT
      .create()
      .withSubject(username)
      .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .sign(Algorithm.HMAC512(SECRET.getBytes()));
  }

  public String getEmailFromToken(String token) {
    return JWT
      .require(Algorithm.HMAC512(SECRET.getBytes()))
      .build()
      .verify(token)
      .getSubject();
  }
}
