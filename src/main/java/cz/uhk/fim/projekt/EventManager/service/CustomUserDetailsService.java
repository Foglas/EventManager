package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {

    UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            cz.uhk.fim.projekt.EventManager.Domain.User user = userService.findUserByUserName(username);
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            return new User(user.getUsername(),user.getPassword(),enabled,accountNonExpired,credentialsNonExpired,accountNonLocked, List.of(new SimpleGrantedAuthority("ADMIN")));
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
