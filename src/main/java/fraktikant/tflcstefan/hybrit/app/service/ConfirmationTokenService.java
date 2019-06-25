package fraktikant.tflcstefan.hybrit.app.service;

import fraktikant.tflcstefan.hybrit.app.entity.ConfirmationToken;
import fraktikant.tflcstefan.hybrit.app.entity.User;

public interface ConfirmationTokenService {

    ConfirmationToken generateToken(User user);
    Boolean confirmUser(String confirmationToken);
}
