package cz.uhk.fim.projekt.EventManager.config;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.annotation.web.builders.HttpSecurity.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.*;

import cz.uhk.fim.projekt.EventManager.service.CustomUserDetailsService;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private UserService userService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .csrf()
      .disable()
      .authorizeRequests()
      .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
      .permitAll() // permits static resources
      .requestMatchers(request -> { // custom request matcher for API endpoints
        String path = request.getServletPath();
        return path.startsWith("/api/auth/");
      })
      .authenticated()
      .anyRequest()
      .permitAll()
      .and()
      .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil))
      .addFilter(
        new JwtAuthorizationFilter(
          authenticationManager(),
          jwtUtil,
          userService
        )
      )
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .cors();

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*")); // add your allowed origins here
    configuration.setAllowedMethods(
      Arrays.asList("GET", "POST", "PUT", "DELETE")
    );
    configuration.setAllowedHeaders(
      Arrays.asList("Content-Type", "Authorization")
    );

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", configuration); // enable CORS only for /api/** requests

    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(userDetailsService);
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    return new ProviderManager(
      Collections.singletonList(authenticationProvider())
    );
  }
}
