package cinema.service.mapper;

import cinema.dto.response.UserResponseDto;
import cinema.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements ResponseDtoMapper<UserResponseDto, User> {
    @Override
    public UserResponseDto mapToDto(User user) {
        Long id = user.getId();
        String email = user.getEmail();
        List<String> roles = new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(role.getRoleName().name()));
        return new UserResponseDto(id, email, roles);
    }
}
