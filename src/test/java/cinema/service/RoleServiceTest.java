package cinema.service;

import static cinema.model.Role.RoleName.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import cinema.dao.RoleDao;
import cinema.model.Role;
import cinema.service.impl.RoleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleServiceTest {
    private RoleService roleService;
    @Mock
    private RoleDao roleDao;
    private Role role;
    private Role savedRole;

    @BeforeEach
    void setUp() {
        roleService = new RoleServiceImpl(roleDao);
        role = new Role();
        role.setRoleName(ADMIN);
        savedRole = new Role();
        savedRole.setId(1L);
        savedRole.setRoleName(ADMIN);
    }

    @Test
    @Order(1)
    void save_validUser_ok() {
        when(roleDao.add(role)).thenReturn(savedRole);
        Role actual = roleService.add(role);
        assertEquals(actual, savedRole,
                "Method should return saved role '%s'"
                        .formatted(savedRole));
    }

    @Test
    @Order(2)
    void getRoleByName_existingRole_ok() {
        when(roleDao.getRoleByName(role.getRoleName().name())).thenReturn(Optional.of(savedRole));
        Role actual = roleService.getRoleByName(role.getRoleName().name());
        assertEquals(actual, savedRole,
                "Method should return role '%s' but returned '%s' for roleName '%s'"
                        .formatted(savedRole, actual, role.getRoleName().name()));
    }

    @Test
    @Order(3)
    void getRoleByName_notExistingRole_notOk() {
        String notExistingRole = "ROLE_NOT_EXIST";
        when(roleDao.getRoleByName(notExistingRole)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> roleService.getRoleByName(notExistingRole),
                "Method should throw '%s' for not existing role name"
                        .formatted(EntityNotFoundException.class));
    }
}
