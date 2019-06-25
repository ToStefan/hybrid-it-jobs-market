package fraktikant.tflcstefan.hybrit.app.service;

import fraktikant.tflcstefan.hybrit.app.web.dto.PageDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll(PageDTO pageDTO);
    UserDTO findByUsername(String username);
    UserDTO create(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    void remove(Long id);
    Long elementsCount();
    Boolean existsByUsername(String username);
}

