package fraktikant.tflcstefan.hybrit.app.service;

import fraktikant.tflcstefan.hybrit.app.entity.User;

public interface MailService {

    void deletedAccNotify(User user);

}
