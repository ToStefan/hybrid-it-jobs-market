package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.entity.User;
import fraktikant.tflcstefan.hybrit.app.exception.EntityNotFoundException;
import fraktikant.tflcstefan.hybrit.app.repository.UserRepository;
import fraktikant.tflcstefan.hybrit.app.service.MailService;
import fraktikant.tflcstefan.hybrit.app.service.UserService;
import fraktikant.tflcstefan.hybrit.app.web.dto.PageDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import fraktikant.tflcstefan.hybrit.app.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;

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
    public UserDTO findById(Long id) {
        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id)));
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    @Override
    @Transactional
    public void remove(Long userId) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId));
        userRepository.deleteById(userId);
        mailService.deletedAccNotify(u);
    }

    @Override
    public Long elementsCount() {
        return userRepository.count();
    }
}