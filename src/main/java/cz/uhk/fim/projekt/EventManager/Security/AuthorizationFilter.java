package cz.uhk.fim.projekt.EventManager.Security;

import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.Role;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.enums.Roles;
import cz.uhk.fim.projekt.EventManager.service.UserService;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

        } else { //potrebuje autorizaci tokenu
            try {
                String header = request.getHeader("Authorization");

                if (header == null || !header.startsWith("Bearer ")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                String token = header.replace("Bearer ", "");
                String email = jwtUtil.getEmailFromToken(token);

                User user = userService.findUserByEmail(email);

                boolean isAdminApi = request.getServletPath().startsWith("/api/auth/admin");
                if (user == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                } else if (isAdminApi){

                    List<Role> userRoles = userService.getUsersRole(user);

                    for (Role role: userRoles) {
                        if (role.getType() == Roles.ADMIN){
                            chain.doFilter(request, response);
                        return;
                        } else {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            return;
                        }
                    }

                } else {
                    chain.doFilter(request, response);
                }
            } catch (Exception exception) {
            exception.printStackTrace();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            }

        }
    }
}

