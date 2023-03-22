package cz.uhk.fim.projekt.EventManager.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.*;
import static org.springframework.security.config.annotation.web.builders.HttpSecurity.*;


import cz.uhk.fim.projekt.EventManager.service.CustomUserDetailsService;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
http.csrf().disable().cors().disable()
    .authorizeRequests()
    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // permits static resources
    .requestMatchers(request -> { // custom request matcher for API endpoints
        String path = request.getServletPath();
        return path.startsWith("/api/auth/");
    }).permitAll()
    .anyRequest().authenticated()
    .and()
    .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil))
    .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService))
    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

return http.build();
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
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
        
    }
}
