package cz.uhk.fim.projekt.EventManager.config;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class AuthorizationFilter extends OncePerRequestFilter {

  private JwtUtil jwtUtil;

  private UserService userService;


  @Autowired
  public AuthorizationFilter(JwtUtil jwtUtil, UserService userService) {
    this.jwtUtil = jwtUtil;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
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
      } else {
        chain.doFilter(request, response);
      }
    } catch (Exception exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
  }

