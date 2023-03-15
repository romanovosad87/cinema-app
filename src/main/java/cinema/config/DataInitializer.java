package cinema.config;

import static cinema.model.Role.RoleName.ADMIN;
import static cinema.model.Role.RoleName.USER;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.RoleService;
import cinema.service.ShoppingCartService;
import cinema.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public DataInitializer(RoleService roleService, UserService userService,
                           ShoppingCartService shoppingCartService) {
        this.roleService = roleService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(USER);
        roleService.add(userRole);

        User admin = new User();
        admin.setEmail("admin@i.ua");
        admin.setPassword("admin123");
        admin.setRoles(Set.of(adminRole));
        userService.add(admin);

        User user = new User();
        user.setEmail("user@i.ua");
        user.setPassword("user5678");
        user.setRoles(Set.of(userRole));
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
    }
}
