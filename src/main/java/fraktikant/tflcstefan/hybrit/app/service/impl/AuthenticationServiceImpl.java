package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.entity.EmailNotification;
import fraktikant.tflcstefan.hybrit.app.entity.Role;
import fraktikant.tflcstefan.hybrit.app.entity.RoleName;
import fraktikant.tflcstefan.hybrit.app.exception.UsernameAlreadyExistException;
import fraktikant.tflcstefan.hybrit.app.exception.WrongUsernamePasswordException;
import fraktikant.tflcstefan.hybrit.app.security.*;
import fraktikant.tflcstefan.hybrit.app.service.AuthenticationService;
import fraktikant.tflcstefan.hybrit.app.service.UserService;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserAuthenticationService userAuthenticationService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationResponse login(UserDTO userDto) {

        authenticate(userDto.getUsername(), userDto.getPassword());
        UserDetails userDetails = userAuthenticationService.loadUserByUsername(userDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtAuthenticationResponse(token);
    }

    @Override
    public UserDTO register(UserDTO userDto) {

        if ((userService.existsByUsername(userDto.getUsername())) == true) {
            throw new UsernameAlreadyExistException(userDto.getUsername());
        }

        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName(RoleName.ROLE_USER));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(roles);
        userDto.setEmailNotification(EmailNotification.NEVER);

        UserDTO user = userService.create(userDto);
        return user;
    }

    @Override
    public UserPrincipal me(HttpServletRequest request) {

        String token = request.getHeader(Constants.tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserPrincipal user = (UserPrincipal) userAuthenticationService.loadUserByUsername(username);
        return user;
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new WrongUsernamePasswordException();
        }
    }
}
