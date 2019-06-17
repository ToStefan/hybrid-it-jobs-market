package fraktikant.tflcstefan.hybrit.app.web.controller;

import fraktikant.tflcstefan.hybrit.app.service.impl.UserServiceImpl;
import fraktikant.tflcstefan.hybrit.app.web.dto.PageDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping(value = "/search")
    public ResponseEntity<List<UserDTO>> search(@RequestBody PageDTO pageDTO) {
        List<UserDTO> dtos = userService.findAll(pageDTO);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        UserDTO dtos = userService.findById(id);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        UserDTO retVal = userService.create(userDTO);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        UserDTO retVal = userService.update(userDTO);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> pageCount() {

        Long elements = userService.elementsCount();
        Long page = null;

        if(elements % 3 == 0) {
            page = elements / 3;
        } else {
            page = elements / 3;
            page += 1;
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}