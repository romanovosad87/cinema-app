package cinema.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record CinemaHallRequestDto(
        @Min(10)
        int capacity,
        @Size(max = 200)
        String description
) {
}
