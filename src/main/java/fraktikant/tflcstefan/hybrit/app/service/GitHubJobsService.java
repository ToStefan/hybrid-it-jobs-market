package fraktikant.tflcstefan.hybrit.app.service;

import fraktikant.tflcstefan.hybrit.app.web.dto.JobDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.NotificationDTO;

import java.util.List;

public interface GitHubJobsService {

    List<JobDTO> recommendedJobs(String loggedUsername);
    List<JobDTO> searchForJobs(JobDTO jobDto);
    List<JobDTO> newJobs(NotificationDTO notificationDTO);
    JobDTO getJobById(String jobId);

}
