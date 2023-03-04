package cinema.service.mapper;

import cinema.dto.response.OrderResponseDto;
import cinema.model.Order;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper implements ResponseDtoMapper<OrderResponseDto, Order> {
    @Override
    public OrderResponseDto mapToDto(Order order) {
        Long id = order.getId();
        Long userId = order.getUser().getId();
        LocalDateTime orderTime = order.getOrderTime();
        List<Long> ticketsId = new ArrayList<>();
        order.getTickets()
                .forEach(ticket -> ticketsId.add(ticket.getId()));
        return new OrderResponseDto(id, ticketsId, userId, orderTime);
    }
}
