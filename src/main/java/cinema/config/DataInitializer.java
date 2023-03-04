package cinema.config;

import static cinema.model.Role.RoleName.ADMIN;
import static cinema.model.Role.RoleName.USER;

import cinema.model.Movie;
import cinema.model.Role;
import cinema.model.User;
import cinema.service.MovieService;
import cinema.service.RoleService;
import cinema.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;
    private final MovieService movieService;

    public DataInitializer(RoleService roleService, UserService userService,
                           MovieService movieService) {
        this.roleService = roleService;
        this.userService = userService;
        this.movieService = movieService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(USER);
        roleService.add(userRole);
        User user = new User();
        user.setEmail("admin@i.ua");
        user.setPassword("admin123");
        user.setRoles(Set.of(adminRole));
        userService.add(user);
        Movie amelie = new Movie();
        amelie.setTitle("Amélie");
        amelie.setDescription("French-language romantic comedy film");
        movieService.add(amelie);
    }
}
