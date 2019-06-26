package fraktikant.tflcstefan.hybrit.app.scheduler;

import fraktikant.tflcstefan.hybrit.app.entity.User;
import fraktikant.tflcstefan.hybrit.app.repository.UserRepository;
import fraktikant.tflcstefan.hybrit.app.util.Builders;
import fraktikant.tflcstefan.hybrit.app.web.dto.MailDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.NotificationDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SchedulerService {

    private final SchedulerCore schedulerCore;
    private final UserRepository userRepository;

    public void addTask(NotificationDTO notificationDTO){

        User user = userRepository.findByUsername(notificationDTO.getUsername());
        MailDTO mail = new MailDTO();
        mail.setRecipient(user.getEmail());
        mail.setSubject("New jobs");

        String cron = Builders.cronBuilder(notificationDTO);

        Integer taskId = schedulerCore.addTaskToScheduler(cron, mail, notificationDTO);

        user.setNotificationOn(true);
        user.setTaskId(taskId);
        userRepository.save(user);
    }

    public void removeTask(String username){
        User user = userRepository.findByUsername(username);
        schedulerCore.removeTaskFromScheduler(user.getTaskId());
        user.setTaskId(null);
        user.setNotificationOn(false);
        userRepository.save(user);
    }
}
