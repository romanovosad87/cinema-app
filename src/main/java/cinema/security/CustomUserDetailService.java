package cinema.security;

import cinema.model.User;
import cinema.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(CustomUserDetailService.class);
    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> logAndThrowException(email));

        return org.springframework.security.core.userdetails.User.withUsername(email)
                .password(user.getPassword())
                .roles(user.getRoles()
                        .stream()
                        .map(role -> role.getRoleName().name())
                        .toArray(String[]::new))
                .build();
    }

    private UsernameNotFoundException logAndThrowException(String email) {
        LOGGER.error("Can't login. Params: email = {}", email);
        return new UsernameNotFoundException(String.format("Can't find user by %s", email));
    }
}


