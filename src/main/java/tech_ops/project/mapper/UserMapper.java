package tech_ops.project.mapper;

import org.springframework.stereotype.Component;
import tech_ops.project.dto.UserDto;
import tech_ops.project.entity.User;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setPatronymic(user.getPatronymic());
        dto.setMilitaryRank(user.getMilitaryRank());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
