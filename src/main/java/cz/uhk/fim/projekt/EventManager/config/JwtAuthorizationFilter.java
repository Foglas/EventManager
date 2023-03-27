package cz.uhk.fim.projekt.EventManager.config;

import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private JwtUtil jwtUtil;

  private UserService userService;

  public JwtAuthorizationFilter(
    AuthenticationManager authenticationManager,
    JwtUtil jwtUtil,
    UserService userService
  ) {
    super(authenticationManager);
    this.jwtUtil = jwtUtil;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain
  ) throws IOException, ServletException {
    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    String token = header.replace("Bearer ", "");
    String email = jwtUtil.getEmailFromToken(token);
    User user = userService.findUserByEmail(email);
    if (user == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
      user.getUserDetails(),
      null,
      null
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }
}
