package cinema.dto.response;

import java.util.List;

public record UserResponseDto(
        Long id,
        String email,
        List<String> roles
) {
}
