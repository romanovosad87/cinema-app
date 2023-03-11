package cinema.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.ReflectionUtils.setField;

import cinema.dto.request.UserRegisterRequestDto;
import cinema.lib.FieldsValueMatchValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Field;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FieldsValueMatchValidatorTest {
    private FieldsValueMatchValidator fieldsValueMatchValidator;
    private UserRegisterRequestDto userRegisterRequestDto;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        fieldsValueMatchValidator = new FieldsValueMatchValidator();
        userRegisterRequestDto = new UserRegisterRequestDto();
        try {
            Field field = FieldsValueMatchValidator.class.getDeclaredField("field");
            field.setAccessible(true);
            Field fieldMatch = FieldsValueMatchValidator.class.getDeclaredField("fieldMatch");
            fieldMatch.setAccessible(true);
            setField(field, fieldsValueMatchValidator, "password");
            setField(fieldMatch, fieldsValueMatchValidator, "repeatPassword");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Can't set field to '%s'",
                    FieldsValueMatchValidator.class), e);
        }
    }

    @Test
    @Order(1)
    void isValid_matchedRequest_ok() {
        userRegisterRequestDto.setPassword("1234");
        userRegisterRequestDto.setRepeatPassword("1234");
        boolean actual = fieldsValueMatchValidator.isValid(userRegisterRequestDto, constraintValidatorContext);
        assertTrue(actual,
                "Method should return true if password matches repeat password");
    }

    @Test
    @Order(2)
    void isValid_notMatchedRequest_notOk() {
        userRegisterRequestDto.setPassword("8765");
        userRegisterRequestDto.setRepeatPassword("1234");
        boolean actual = fieldsValueMatchValidator.isValid(userRegisterRequestDto, constraintValidatorContext);
        assertFalse(actual,
                "Method should return false if password do not matches repeat password");
    }
}
