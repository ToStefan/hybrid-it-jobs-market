package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.entity.NotificationType;
import fraktikant.tflcstefan.hybrit.app.entity.Role;
import fraktikant.tflcstefan.hybrit.app.entity.RoleName;
import fraktikant.tflcstefan.hybrit.app.exception.UserVerificationException;
import fraktikant.tflcstefan.hybrit.app.exception.UsernameAlreadyExistException;
import fraktikant.tflcstefan.hybrit.app.repository.RoleRepository;
import fraktikant.tflcstefan.hybrit.app.repository.UserRepository;
import fraktikant.tflcstefan.hybrit.app.security.JwtAuthenticationResponse;
import fraktikant.tflcstefan.hybrit.app.security.JwtTokenUtil;
import fraktikant.tflcstefan.hybrit.app.security.UserAuthenticationService;
import fraktikant.tflcstefan.hybrit.app.security.UserPrincipal;
import fraktikant.tflcstefan.hybrit.app.service.AuthenticationService;
import fraktikant.tflcstefan.hybrit.app.util.Constants;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import fraktikant.tflcstefan.hybrit.app.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailServiceImpl mailService;
    private final UserMapper userMapper;

    @Override
    public JwtAuthenticationResponse login(UserDTO userDto) {

        authenticate(userDto.getUsername(), userDto.getPassword());
        UserDetails userDetails = userAuthenticationService.loadUserByUsername(userDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtAuthenticationResponse(token);
    }

    @Override
    public UserDTO register(UserDTO userDto) {

        if ((userRepository.existsByUsername(userDto.getUsername())) == true) {
            throw new UsernameAlreadyExistException(userDto.getUsername());
        }

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_USER));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(roles);
        userDto.setNotificationType(NotificationType.NEVER);
        userDto.setFullTime(true);
        userDto.setIsEnabled(false);
        userDto.setNotificationOn(false);

        UserDTO user = userMapper.toDTO(userRepository.save(userMapper.toEntity(userDto)));
        mailService.userConfirmation(user);
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
        } catch (AuthenticationException e) {
            throw new UserVerificationException();
        }
    }
}
