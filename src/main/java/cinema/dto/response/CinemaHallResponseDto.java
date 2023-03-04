package cinema.dto.response;

public record CinemaHallResponseDto(
        Long id,
        int capacity,
        String description
) {
}
