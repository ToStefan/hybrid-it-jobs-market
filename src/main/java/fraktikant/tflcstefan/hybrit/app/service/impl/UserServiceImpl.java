package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.entity.User;
import fraktikant.tflcstefan.hybrit.app.exception.EntityNotFoundException;
import fraktikant.tflcstefan.hybrit.app.repository.UserRepository;
import fraktikant.tflcstefan.hybrit.app.service.UserService;
import fraktikant.tflcstefan.hybrit.app.web.dto.PageDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import fraktikant.tflcstefan.hybrit.app.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll(PageDTO pageDTO) {
        return userRepository.findAll(pageDTO.toPageRequest())
                .get()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByUsername(String username) {
        UserDTO userDTO = userMapper.toDTO(userRepository.findByUsername(username));
        userDTO.setPassword(null);
        return userDTO;
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO) {
        UserDTO oldUser = userMapper.toDTO(userRepository.findByUsername(userDTO.getUsername()));
        oldUser.setNotificationTime(userDTO.getNotificationTime());
        oldUser.setNotificationType(userDTO.getNotificationType());
        oldUser.setJobDesc(userDTO.getJobDesc());
        oldUser.setJobLocation(userDTO.getJobLocation());
        oldUser.setFullTime(userDTO.getFullTime());

        UserDTO retVal = userMapper.toDTO(userRepository.save(userMapper.toEntity(oldUser)));
        retVal.setPassword(null);
        return retVal;
    }

    @Override
    @Transactional
    public void remove(Long userId) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId));
        userRepository.deleteById(userId);
    }

    @Override
    public Long elementsCount() {
        return userRepository.count();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}