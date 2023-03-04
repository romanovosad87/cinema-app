package cinema.dto.response;

import cinema.util.DateTimePatternUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        List<Long> ticketIds,
        Long userId,
        @JsonFormat(pattern = DateTimePatternUtil.DATE_TIME_SECOND_PATTERN)
        LocalDateTime orderTime
) {
}
