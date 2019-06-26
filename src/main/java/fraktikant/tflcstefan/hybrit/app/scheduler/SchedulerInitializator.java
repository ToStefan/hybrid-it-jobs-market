package fraktikant.tflcstefan.hybrit.app.scheduler;

import fraktikant.tflcstefan.hybrit.app.entity.User;
import fraktikant.tflcstefan.hybrit.app.repository.UserRepository;
import fraktikant.tflcstefan.hybrit.app.util.Builders;
import fraktikant.tflcstefan.hybrit.app.web.dto.MailDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.NotificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@AllArgsConstructor
@Component
public class SchedulerInitializator {

    private final SchedulerCore schedulerCore;
    private final UserRepository userRepository;

    @PostConstruct
    private void init() {
        List<User> users = userRepository.findAllWhereTaskIdNotNull();
        for (User user : users) {
            MailDTO mail = new MailDTO(user.getEmail(), null, "New jobs");
            NotificationDTO notifDTO = new NotificationDTO(user.getJobDesc(), user.getJobLocation(),
                    user.getFullTime(), user.getNotificationType(), user.getNotificationTime(), user.getUsername());
            String cron = Builders.cronBuilder(notifDTO);
            schedulerCore.addTaskToScheduler(cron, mail, notifDTO);
        }
    }
}
