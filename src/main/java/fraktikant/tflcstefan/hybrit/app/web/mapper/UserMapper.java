package fraktikant.tflcstefan.hybrit.app.web.mapper;

import fraktikant.tflcstefan.hybrit.app.entity.User;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setIsEnabled(user.getIsEnabled());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        dto.setJobDesc(user.getJobDesc());
        dto.setJobLocation(user.getJobLocation());
        dto.setFullTime(user.getFullTime());
        dto.setEmailNotification(user.getEmailNotification());
        dto.setNotificationTime(user.getNotificationTime());
        return dto;
    }

    @Override
    public List<UserDTO> toDTO(List<User> entities) {
        return entities
                .stream()
                .map(user -> toDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        User entity = new User();
        entity.setId(userDTO.getId());
        entity.setIsEnabled(userDTO.getIsEnabled());
        entity.setFirstname(userDTO.getFirstname());
        entity.setLastname(userDTO.getLastname());
        entity.setUsername(userDTO.getUsername());
        entity.setPassword(userDTO.getPassword());
        entity.setEmail(userDTO.getEmail());
        entity.setRoles(userDTO.getRoles());
        entity.setJobDesc(userDTO.getJobDesc());
        entity.setJobLocation(userDTO.getJobLocation());
        entity.setFullTime(userDTO.getFullTime());
        entity.setEmailNotification(userDTO.getEmailNotification());
        entity.setNotificationTime(userDTO.getNotificationTime());
        return entity;
    }

    @Override
    public List<User> toEntity(List<UserDTO> dtos) {
        return dtos
                .stream()
                .map(userDTO -> toEntity(userDTO))
                .collect(Collectors.toList());
    }

}