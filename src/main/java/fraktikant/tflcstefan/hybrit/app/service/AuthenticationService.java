package fraktikant.tflcstefan.hybrit.app.service;

import fraktikant.tflcstefan.hybrit.app.security.JwtAuthenticationResponse;
import fraktikant.tflcstefan.hybrit.app.security.UserPrincipal;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    JwtAuthenticationResponse login(UserDTO userDto);
    UserDTO register(UserDTO userDto);
    UserPrincipal me(HttpServletRequest request);
}
