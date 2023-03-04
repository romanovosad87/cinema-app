package cinema.dto.request;

import cinema.util.DateTimePatternUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record MovieSessionRequestDto(
        @Positive
        Long movieId,
        @Positive
        Long cinemaHallId,
        @JsonFormat(pattern = DateTimePatternUtil.DATE_TIME_PATTERN)
        @FutureOrPresent
        LocalDateTime showTime
) {
}
