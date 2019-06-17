package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.entity.User;
import fraktikant.tflcstefan.hybrit.app.service.MailService;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public void deletedAccNotify(User user) {

        //send notficication to user

    }
}
