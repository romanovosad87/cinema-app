package cinema.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import cinema.dao.UserDao;
import cinema.model.User;
import cinema.service.impl.UserServiceImpl;
import java.util.Optional;
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
class UserServiceTest {
    private static final String EMAIL = "valid@i.ua";
    private static final String PASSWORD = "1234";
    private UserService userService;
    @Mock
    private UserDao userDao;
    private User user;
    private User savedUser;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(passwordEncoder, userDao);
        user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(EMAIL);
        savedUser.setPassword(passwordEncoder.encode(PASSWORD));
    }

    @Test
    @Order(1)
    void save_validUser_Ok() {
        when(userDao.add(user)).thenReturn(savedUser);
        User actual = userService.add(user);
        assertNotNull(actual,
                "Method should return saved user '%s'"
                        .formatted(savedUser));
        assertEquals(savedUser, actual);

    }

    @Test
    @Order(2)
    void get_existingUser_ok() {
        when(userDao.get(savedUser.getId())).thenReturn(Optional.of(savedUser));
        User actual = userService.get(savedUser.getId());
        assertNotNull(actual,
                "Method should return user '%s' for id '%s'"
                        .formatted(savedUser, savedUser.getId()));
        assertEquals(savedUser, actual);
    }

    @Test
    @Order(3)
    void get_notExistedUser_notOk() {
        long notExistingId = 100L;
        lenient().when(userDao.get(savedUser.getId())).thenReturn(Optional.of(savedUser));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.get(notExistingId),
                "Method should throw '%s' from not existing id '%s'"
                        .formatted(RuntimeException.class, notExistingId));
        assertEquals(String.format("User with id %s not found", notExistingId),
                exception.getMessage());
    }

    @Test
    @Order(4)
    void findByEmail_existingEmail_ok() {
        when(userDao.findByEmail(EMAIL)).thenReturn(Optional.of(savedUser));
        Optional<User> optionalUser = userService.findByEmail(EMAIL);
        assertTrue(optionalUser.isPresent(),
                "Method should return optional user '%s' for email '%s'"
                        .formatted(savedUser, EMAIL));
        assertEquals(savedUser, optionalUser.get());
    }

    @Test
    @Order(5)
    void findByEmail_notExistingEmail_notOk() {
        String notExistingEmail = "notExist@i.ua";
        lenient().when(userDao.findByEmail(EMAIL)).thenReturn(Optional.of(savedUser));
        Optional<User> optionalUser = userService.findByEmail(notExistingEmail);
        assertFalse(optionalUser.isPresent(),
                "Method should return optional empty for email '%s'"
                        .formatted(notExistingEmail));
    }
}
