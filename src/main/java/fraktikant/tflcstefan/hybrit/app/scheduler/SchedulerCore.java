package fraktikant.tflcstefan.hybrit.app.scheduler;

import fraktikant.tflcstefan.hybrit.app.service.impl.GitHubJobsServiceImpl;
import fraktikant.tflcstefan.hybrit.app.service.impl.MailServiceImpl;
import fraktikant.tflcstefan.hybrit.app.util.Builders;
import fraktikant.tflcstefan.hybrit.app.web.dto.JobDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.MailDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.NotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
public class SchedulerCore {

    public Map<Integer, ScheduledFuture<?>> tasks = new HashMap<>();

    private TaskScheduler taskScheduler;
    private MailServiceImpl mailService;
    private GitHubJobsServiceImpl gitHubJobsService;

    public SchedulerCore(TaskScheduler taskScheduler, MailServiceImpl mailService, GitHubJobsServiceImpl gitHubJobsService) {
        this.taskScheduler = taskScheduler;
        this.mailService = mailService;
        this.gitHubJobsService = gitHubJobsService;
    }

    public Integer addTaskToScheduler(String cron, MailDTO mail, NotificationDTO notificationDTO) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                List<JobDTO> jobs = gitHubJobsService.newJobs(notificationDTO);
                String mailBody = Builders.newJobsMailBody(jobs);
                mail.setContent(mailBody);
                mailService.sendEmail(mail);
            }
        };

        ScheduledFuture<?> scheduledTask = taskScheduler
                .schedule(task, new CronTrigger(cron, TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        Integer taskId = tasks.size() + 1;
        tasks.put(taskId, scheduledTask);
        log.info("Task scheduled, ID: " + taskId);
        return taskId;
    }

    public void removeTaskFromScheduler(Integer id) {
        ScheduledFuture<?> scheduledTask = tasks.get(id);
        tasks.remove(id);
    }
}