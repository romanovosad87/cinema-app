package cinema.dto.response;

import cinema.util.DateTimePatternUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record MovieSessionResponseDto(
        Long movieSessionId,
        Long movieId,
        String movieTitle,
        Long cinemaHallId,
        @JsonFormat(pattern = DateTimePatternUtil.DATE_TIME_PATTERN)
        LocalDateTime showTime
) {
}
