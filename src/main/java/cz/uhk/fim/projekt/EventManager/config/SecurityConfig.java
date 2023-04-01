package cz.uhk.fim.projekt.EventManager.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  AuthorizationFilter authorizationFilter;
  @Autowired
  public SecurityConfig(AuthorizationFilter authorizationFilter){
    this.authorizationFilter = authorizationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http.csrf().disable();
    http
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.cors();

    http
      .authorizeRequests()
      .requestMatchers("/api/**")
      .permitAll()
      .anyRequest()
      .authenticated().and().formLogin().and().httpBasic();

  //  http.addFilterBefore(
  //   authorizationFilter,
  //    BasicAuthenticationFilter.class
  //  );

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(
      Arrays.asList("GET", "POST", "PUT", "DELETE")
    );
    configuration.setAllowedHeaders(
      Arrays.asList("Content-Type", "Authorization")
    );

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", configuration);

    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
