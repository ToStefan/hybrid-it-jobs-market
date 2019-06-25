package fraktikant.tflcstefan.hybrit.app.web.controller;

import fraktikant.tflcstefan.hybrit.app.service.impl.ConfirmationTokenServiceImpl;
import fraktikant.tflcstefan.hybrit.app.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/token")
public class ConfirmationTokenController {

    private ConfirmationTokenServiceImpl confirmTokenService;

    @GetMapping(value = "/confirm/{token}")
    public void search(HttpServletResponse response, @PathVariable String token) throws IOException {

        if(confirmTokenService.confirmUser(token) == true){
            response.sendRedirect(Constants.basePath + "/token.html");
        }
    }
}
