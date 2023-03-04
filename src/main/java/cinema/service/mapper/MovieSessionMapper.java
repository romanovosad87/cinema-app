package cinema.service.mapper;

import cinema.dto.request.MovieSessionRequestDto;
import cinema.dto.response.MovieSessionResponseDto;
import cinema.model.MovieSession;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class MovieSessionMapper implements RequestDtoMapper<MovieSessionRequestDto, MovieSession>,
        ResponseDtoMapper<MovieSessionResponseDto, MovieSession> {
    private final CinemaHallService cinemaHallService;
    private final MovieService movieService;

    public MovieSessionMapper(CinemaHallService cinemaHallService, MovieService movieService) {
        this.cinemaHallService = cinemaHallService;
        this.movieService = movieService;
    }

    @Override
    public MovieSession mapToModel(MovieSessionRequestDto dto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(dto.movieId()));
        movieSession.setCinemaHall(cinemaHallService.get(dto.cinemaHallId()));
        movieSession.setShowTime(dto.showTime());
        return movieSession;
    }

    @Override
    public MovieSessionResponseDto mapToDto(MovieSession movieSession) {
        Long id = movieSession.getId();
        Long cinemaHallId = movieSession.getCinemaHall().getId();
        Long movieId = movieSession.getMovie().getId();
        String title = movieSession.getMovie().getTitle();
        LocalDateTime showTime = movieSession.getShowTime();
        return new MovieSessionResponseDto(id, movieId, title, cinemaHallId, showTime);
    }
}
