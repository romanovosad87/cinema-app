package cinema.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MovieRequestDto(
        @NotNull
        String title,
        @Size(max = 200)
        String description
) {
}
