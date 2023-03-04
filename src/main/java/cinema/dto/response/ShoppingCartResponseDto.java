package cinema.dto.response;

import java.util.List;

public record ShoppingCartResponseDto(
        Long userId,
        List<Long> ticketIds
) {
}
