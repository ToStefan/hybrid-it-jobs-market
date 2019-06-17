package fraktikant.tflcstefan.hybrit.app.service;

import fraktikant.tflcstefan.hybrit.app.web.dto.PageDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll(PageDTO pageDTO);
    UserDTO findById(Long id);
    UserDTO create(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    void remove(Long id);

}

