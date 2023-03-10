package cinema.dto.request;

import cinema.lib.FieldsValueMatch;
import cinema.lib.ValidEmail;
import jakarta.validation.constraints.Size;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRegisterRequestDto {
    @ValidEmail
    private String email;
    @Size(message = "Password length should be between 8 and 40",
            min = 8, max = 40)
    private String password;
    private String repeatPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
