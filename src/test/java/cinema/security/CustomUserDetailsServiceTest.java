package cinema.security;

import static cinema.model.Role.RoleName.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.UserService;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomUserDetailsServiceTest {
    private static final String EMAIL = "valid@i.ua";
    private static final String PASSWORD = "1234";
    private UserDetailsService userDetailsService;
    @Mock
    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setRoles(Set.of(new Role(USER)));
        userDetailsService = new CustomUserDetailService(userService);
    }

    @Test
    @Order(1)
    void loadUserByUsername_validEmail_ok() {
        when(userService.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        UserDetails actual = userDetailsService.loadUserByUsername(EMAIL);
        assertNotNull(actual);
        assertEquals(EMAIL, actual.getUsername(),
                "Method should return userDetails with userName '%s' but returned '%s'"
                        .formatted(EMAIL, actual.getUsername()));
        assertEquals(PASSWORD, actual.getPassword(),
                "Method should return userDetails with password '%s' but returned '%s'"
                        .formatted(PASSWORD, actual.getPassword()));
        boolean ifRoleUserExist = actual.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_" + USER.name()));
        assertTrue(ifRoleUserExist,
                "Method should return userDetails with authority '%s'"
                        .formatted("ROLE_" + USER.name()));
    }

    @Test
    @Order(1)
    void loadUserByUsername_notExistedEmail_notOk() {
        String notExistingEmail = "not_exist@i.ua";
        when(userService.findByEmail(notExistingEmail)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(notExistingEmail),
                "Method should throw '%s' when not existing email '%s' is passed"
                        .formatted(UsernameNotFoundException.class, notExistingEmail));
        assertEquals(String.format("Can't find user by %s", notExistingEmail), exception.getMessage());
    }
}
