package cinema.service.mapper;

import cinema.dto.request.MovieRequestDto;
import cinema.dto.response.MovieResponseDto;
import cinema.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper implements RequestDtoMapper<MovieRequestDto, Movie>,
        ResponseDtoMapper<MovieResponseDto, Movie> {
    @Override
    public Movie mapToModel(MovieRequestDto dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.title());
        movie.setDescription(dto.description());
        return movie;
    }

    @Override
    public MovieResponseDto mapToDto(Movie movie) {
        Long id = movie.getId();
        String title = movie.getTitle();
        String description = movie.getDescription();
        return new MovieResponseDto(id, title, description);
    }
}
