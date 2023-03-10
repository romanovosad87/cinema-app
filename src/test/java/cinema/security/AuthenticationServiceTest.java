package cinema.security;

import static cinema.model.Role.RoleName.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.AuthenticationService;
import cinema.service.RoleService;
import cinema.service.ShoppingCartService;
import cinema.service.UserService;
import cinema.service.impl.AuthenticationServiceImpl;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationServiceTest {
    private static final String EMAIL = "valid@i.ua";
    private static final String PASSWORD = "1234";
    private AuthenticationService authenticationService;
    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private ShoppingCartService shoppingCartService;
    private User user;
    private User savedUser;
    private Role userRole;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        authenticationService = new AuthenticationServiceImpl(userService, shoppingCartService,
                roleService);
        user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setRoles(Set.of(new Role(USER)));

        savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(EMAIL);
        savedUser.setPassword(passwordEncoder.encode(PASSWORD));
        savedUser.setRoles(Set.of(new Role(USER)));

        userRole = new Role();
        userRole.setId(1L);
        userRole.setRoleName(USER);
    }

    @Test
    @Order(1)
    void register_validUser_ok() {
        User actual = registerUser(user);
        assertNotNull(actual,
                "Method should return registered user '%s' for email '%s' and password '%s'"
                        .formatted(savedUser, user.getEmail(), user.getPassword()));
        assertEquals(savedUser, actual,
                "Method should return user '%s' but returned '%s'"
                        .formatted(savedUser, actual));
    }

    @Test
    @Order(2)
    void register_nullEmail_notOk() {
        user.setEmail(null);
        User actual = registerUser(user);
        assertNull(actual,
                "Method should return null with 'null' email");
    }

    @Test
    @Order(3)
    void register_nullPassword_notOk() {
        user.setPassword(null);

        when(roleService.getRoleByName("USER")).thenReturn(new Role(USER));
        lenient().when(userService.add(user)).thenReturn(savedUser);

        User actual = authenticationService.register(EMAIL, PASSWORD);
        assertNull(actual,
                "Method should return null with 'null' password");
    }

    private User registerUser(User user) {
        when(roleService.getRoleByName(USER.name())).thenReturn(userRole);
        lenient().when(userService.add(user)).thenReturn(savedUser);
        return authenticationService.register(EMAIL, PASSWORD);
    }
}
