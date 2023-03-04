package cinema.service.mapper;

import cinema.dto.request.CinemaHallRequestDto;
import cinema.dto.response.CinemaHallResponseDto;
import cinema.model.CinemaHall;
import org.springframework.stereotype.Component;

@Component
public class CinemaHallMapper implements RequestDtoMapper<CinemaHallRequestDto, CinemaHall>,
        ResponseDtoMapper<CinemaHallResponseDto, CinemaHall> {
    @Override
    public CinemaHall mapToModel(CinemaHallRequestDto dto) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription(dto.description());
        cinemaHall.setCapacity(dto.capacity());
        return cinemaHall;
    }

    @Override
    public CinemaHallResponseDto mapToDto(CinemaHall cinemaHall) {
        Long id = cinemaHall.getId();
        int capacity = cinemaHall.getCapacity();
        String description = cinemaHall.getDescription();
        return new CinemaHallResponseDto(id, capacity, description);
    }
}
