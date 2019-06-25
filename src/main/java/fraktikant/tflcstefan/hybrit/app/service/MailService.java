package fraktikant.tflcstefan.hybrit.app.service;

import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;

public interface MailService {

    void deletedUserNotify(Long userId);
    void userConfirmation(UserDTO user);

}
