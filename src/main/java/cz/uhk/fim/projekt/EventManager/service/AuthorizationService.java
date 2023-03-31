package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  public void filterRequest(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain
  ) throws IOException, ServletException {
    boolean isAuthApi = request.getServletPath().startsWith("/api/auth/");

    /// no need for jwt token authorization
    if (!isAuthApi) {
      chain.doFilter(request, response);
    }

    try {
      String header = request.getHeader("Authorization");

      if (header == null || !header.startsWith("Bearer ")) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }

      String token = header.replace("Bearer ", "");

      String email = jwtUtil.getEmailFromToken(token);

      User user = userService.findUserByEmail(email);

      if (user == null) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      } else {
        chain.doFilter(request, response);
      }
    } catch (Exception exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
  }
}
