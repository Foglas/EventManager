package cz.uhk.fim.projekt.EventManager.Security;

import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.Permission;
import cz.uhk.fim.projekt.EventManager.Domain.Role;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.enums.Permissions;
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

                List<Role> userRoles = userService.getUsersRole(user);
                List<Permission> permissions = new ArrayList<>();

                for (Role role: userRoles) {
                    permissions.addAll(role.getPermissions());
                }
                String method = request.getMethod();

                boolean isAdminApi = request.getServletPath().startsWith("/api/auth/admin");
                if (user == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                } else if (isAdminApi){

                    if (method.equals("GET")){
                        for (Permission permission : permissions) {
                            if (permission.getDestricption() == Permissions.READ_ALL) {
                                chain.doFilter(request, response);
                                return;
                            }
                        }
                    } else if (method.equals("POST")){
                        for (Permission permission : permissions) {
                            if (permission.getDestricption() == Permissions.WRITE_ALL) {
                                chain.doFilter(request, response);
                                return;
                            }
                        }
                    } else if(method.equals("DELETE")){
                        for (Permission permission : permissions) {
                            if (permission.getDestricption() == Permissions.DELETE_ALL) {
                                chain.doFilter(request, response);
                                return;
                            }
                        }
                    } else if (method.equals("UPDATE")){
                        for (Permission permission : permissions) {
                            if (permission.getDestricption() == Permissions.UPDATE_ALL) {
                                chain.doFilter(request, response);
                                return;
                            }
                        }
                    }
                } else {

                    if (method.equals("GET")){
                        for (Permission permission : permissions) {
                            if (permission.getDestricption() == Permissions.READ) {
                                chain.doFilter(request, response);
                                return;
                            }
                        }
                    } else if (method.equals("POST")){
                        for (Permission permission : permissions) {
                            if (permission.getDestricption() == Permissions.WRITE) {
                                chain.doFilter(request, response);
                                return;
                            }
                        }
                    } else if(method.equals("DELETE")){
                        for (Permission permission : permissions) {
                            if (permission.getDestricption() == Permissions.DELETE) {
                                chain.doFilter(request, response);
                                return;
                            }
                        }
                    } else if (method.equals("UPDATE")){
                        for (Permission permission : permissions) {
                            if (permission.getDestricption() == Permissions.UPDATE) {
                                chain.doFilter(request, response);
                                return;
                            }
                        }
                    }
                }
            } catch (Exception exception) {
            exception.printStackTrace();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            }

        }
    }
}

