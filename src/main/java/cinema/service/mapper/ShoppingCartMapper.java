package cinema.service.mapper;

import cinema.dto.response.ShoppingCartResponseDto;
import cinema.model.ShoppingCart;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper implements
        ResponseDtoMapper<ShoppingCartResponseDto, ShoppingCart> {

    @Override
    public ShoppingCartResponseDto mapToDto(ShoppingCart shoppingCart) {
        Long userId = shoppingCart.getUser().getId();
        List<Long> ticketsId = new ArrayList<>();
        shoppingCart.getTickets()
                .forEach(ticket -> ticketsId.add(ticket.getId()));
        return new ShoppingCartResponseDto(userId, ticketsId);
    }
}
