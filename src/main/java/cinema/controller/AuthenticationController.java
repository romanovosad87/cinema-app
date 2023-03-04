package cinema.controller;

import cinema.dto.request.UserRequestDto;
import cinema.model.User;
import cinema.service.AuthenticationService;
import cinema.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);
    private final UserService userService;
    private final AuthenticationService authService;

    public AuthenticationController(UserService userService, AuthenticationService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/index")
    public String hello() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserRequestDto userRequestDto = new UserRequestDto();
        model.addAttribute("user", userRequestDto);
        return "register";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserRequestDto userRequestDto,
                               BindingResult result,
                               Model model) {
        Optional<User> userFromDb = userService.findByEmail(userRequestDto.getEmail());
        if (userFromDb.isPresent()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
            LOGGER.error("""
                    Can't register, is already an account registered
                    with the same email. Params: email = {}""",
                    userRequestDto.getEmail());
        }
        if (allErrors(result).contains("Passwords do not match!")) {
            result.rejectValue("password", null, "Passwords do not match!");
            result.rejectValue("repeatPassword", null, "Passwords do not match!");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userRequestDto);
            return "/register";
        }
        authService.register(userRequestDto.getEmail(), userRequestDto.getPassword());
        LOGGER.info("User registered. Params: email = {}", userRequestDto.getEmail());
        return "redirect:/register?success";
    }

    private List<String> allErrors(BindingResult result) {
        return result.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
