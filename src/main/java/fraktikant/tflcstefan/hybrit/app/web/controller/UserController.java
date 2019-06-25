package fraktikant.tflcstefan.hybrit.app.web.controller;

import fraktikant.tflcstefan.hybrit.app.service.impl.MailServiceImpl;
import fraktikant.tflcstefan.hybrit.app.util.Constants;
import fraktikant.tflcstefan.hybrit.app.service.impl.UserServiceImpl;
import fraktikant.tflcstefan.hybrit.app.web.dto.PageDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserServiceImpl userService;
    private final MailServiceImpl mailService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/search")
    public ResponseEntity<List<UserDTO>> search(@RequestBody PageDTO pageDTO) {
        List<UserDTO> dtos = userService.findAll(pageDTO);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        UserDTO retVal = userService.update(userDTO);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        userService.remove(id);
        mailService.deletedUserNotify(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/preference/{username}")
    public ResponseEntity<UserDTO> userPrefeence(@PathVariable("username") String username){
        UserDTO user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/count")
    public ResponseEntity<Long> pageCount() {

        Long elements = userService.elementsCount();
        Long page = null;

        if(elements % Constants.adminPanelPageSize == 0) {
            page = elements / Constants.adminPanelPageSize;
        } else {
            page = elements / Constants.adminPanelPageSize;
            page += 1;
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}