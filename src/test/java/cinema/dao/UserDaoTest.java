package cinema.dao;

import static cinema.model.Role.RoleName.ADMIN;
import static cinema.model.Role.RoleName.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cinema.dao.impl.RoleDaoImpl;
import cinema.dao.impl.UserDaoImpl;
import cinema.model.Role;
import cinema.model.User;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoTest extends AbstractTest {
    private static final String EMAIL = "valid@i.ua";
    private static final String PASSWORD = "1234";
    private UserDao userDao;
    private User user;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Role.class, User.class};
    }

    @BeforeEach
    void setUp() {
        RoleDao roleDao = new RoleDaoImpl(getSessionFactory());
        Role adminRole = new Role();
        adminRole.setRoleName(ADMIN);
        final Role adminRoleFromDb = roleDao.add(adminRole);

        Role userRole = new Role();
        userRole.setRoleName(USER);
        final Role userRoleFromDb = roleDao.add(userRole);

        userDao = new UserDaoImpl(getSessionFactory());
        user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setRoles(Set.of(adminRoleFromDb, userRoleFromDb));
    }

    @Test
    @Order(1)
    void add_validUser_ok() {
        User savedUser = userDao.add(user);
        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId(),
                "Method should return user with id '%s'"
                        .formatted(1L));
    }

    @Test
    @Order(2)
    void findByEmail_existingEmail_ok() {
        User savedUser = userDao.add(user);
        Optional<User> optionalUser = userDao.findByEmail(EMAIL);
        assertTrue(optionalUser.isPresent(),
                "Method should return optional user '%s' from email '%s'"
                        .formatted(savedUser, EMAIL));
        assertEquals(EMAIL, optionalUser.get().getEmail(),
                "Method should return optional user with email '%s' but returned '%s'"
                        .formatted(EMAIL, optionalUser.get().getEmail()));
    }

    @Test
    @Order(3)
    void findByEmail_notExistingEmail_notOk() {
        String notExistingEmail = "notExist@i.ua";
        userDao.add(user);
        Optional<User> optionalUser = userDao.findByEmail(notExistingEmail);
        assertFalse(optionalUser.isPresent(),
                "Method should return false from not existing email '%s'"
                        .formatted(notExistingEmail));
    }

    @Test
    @Order(4)
    void get_validId_ok() {
        User savedUser = userDao.add(user);
        Optional<User> optionalUser = userDao.get(savedUser.getId());
        assertTrue(optionalUser.isPresent(),
                "Method should return optional user '%s' from id '%s'"
                        .formatted(savedUser, savedUser.getId()));
        assertEquals(EMAIL, optionalUser.get().getEmail(),
                "Method should return optional user with email '%s' but returned '%s'"
                        .formatted(EMAIL, optionalUser.get().getEmail()));
    }

    @Test
    @Order(5)
    void get_notExistingId_notOk() {
        long notExistingId = 2L;
        userDao.add(user);
        Optional<User> optionalUser = userDao.get(notExistingId);
        assertFalse(optionalUser.isPresent(),
                "Method should return false from id not existing id '%s'"
                        .formatted(notExistingId));
    }
}
