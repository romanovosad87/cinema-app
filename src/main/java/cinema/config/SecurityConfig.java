package cinema.config;

import cinema.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String ADMIN = Role.RoleName.ADMIN.name();
    private static final String USER = Role.RoleName.USER.name();
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
        authorize
                .requestMatchers("/register", "/index").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/by-email").hasRole(ADMIN)
                .requestMatchers(HttpMethod.PUT, "/movie-sessions/{id}").hasRole(ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/movie-sessions/{id}").hasRole(ADMIN)
                .requestMatchers(HttpMethod.POST, "/movies", "/movie-sessions",
                        "/cinema-halls").hasRole(ADMIN)
                .requestMatchers(HttpMethod.GET, "/orders", "/shopping-carts/by-user").hasRole(USER)
                .requestMatchers(HttpMethod.POST, "/orders/complete").hasRole(USER)
                .requestMatchers(HttpMethod.PUT, "/shopping-carts/movie-sessions").hasRole(USER)
                .requestMatchers(HttpMethod.GET, "/movies", "/cinema-halls",
                        "/movie-sessions/available").hasAnyRole(ADMIN, USER)
                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/movies", true).permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
                .httpBasic()
                .and()
                .csrf().disable();

        return http.build();
    }
}
