package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.repository.UserRepository;
import fraktikant.tflcstefan.hybrit.app.service.UserService;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import fraktikant.tflcstefan.hybrit.app.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDTO> findAll() {
        return userMapper.toDTO(userRepository.findAll());
    }

    @Override
    public UserDTO findById(Long id) {
        return userMapper.toDTO(userRepository.getOne(id));
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    @Override
    public void remove(Long userId) {
        userRepository.deleteById(userId);
    }

}