package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.entity.ConfirmationToken;
import fraktikant.tflcstefan.hybrit.app.entity.User;
import fraktikant.tflcstefan.hybrit.app.exception.EntityNotFoundException;
import fraktikant.tflcstefan.hybrit.app.repository.UserRepository;
import fraktikant.tflcstefan.hybrit.app.service.MailService;
import fraktikant.tflcstefan.hybrit.app.util.Builders;
import fraktikant.tflcstefan.hybrit.app.util.Constants;
import fraktikant.tflcstefan.hybrit.app.web.dto.MailDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import fraktikant.tflcstefan.hybrit.app.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final ConfirmationTokenServiceImpl confirmTokenService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public void deletedUserNotify(Long userId) {

        String reason = "Sorry :( :( :(";
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId));

        String content = "<h2 style=\"color:red;\">Your account is suspended</h2><p>Reason: " + reason + "</p>";
        MailDTO mail = new MailDTO(user.getEmail(), content, "Account suspension :(");
        sendEmail(mail);
    }

    @Override
    public void userConfirmation(UserDTO user) {

        ConfirmationToken token = confirmTokenService.generateToken(userMapper.toEntity(user));
        String confirmLink = Constants.basePath + "/api/token/confirm/" + token.getConfirmationToken();

        String body = Builders.confirmLinkMailBody(confirmLink);

        String subject = "Hybrid It Jobs Market - Account Confirmation";
        MailDTO mail = new MailDTO(user.getEmail(), body, subject);
        sendEmail(mail);
    }

    @Override
    public void sendEmail(MailDTO mail) {

        Message msg = new MimeMessage(getMailProps());
        try {
            msg.setFrom(new InternetAddress(Constants.appMail, false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getRecipient()));
            msg.setSubject(mail.getSubject());
            msg.setContent(mail.getContent(), "text/html");

            Transport.send(msg);

        } catch (MessagingException e) {
            log.error(String.valueOf(e.getStackTrace()));
        }
    }

    private Session getMailProps(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constants.appMail, Constants.appMailPass);
            }
        });
        return session;
    }
}
