package cz.uhk.fim.projekt.EventManager.config;

import cz.uhk.fim.projekt.EventManager.service.AuthorizationService;
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

  private AuthorizationService authService;

  @Autowired
  public AuthorizationFilter(AuthorizationService authorizationService) {
    this.authService = authorizationService;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain
  ) throws IOException, ServletException {
    authService.filterRequest(request, response, chain);
  }
}
