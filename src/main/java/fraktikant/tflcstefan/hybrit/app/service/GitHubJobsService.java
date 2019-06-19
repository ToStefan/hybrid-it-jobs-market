package fraktikant.tflcstefan.hybrit.app.service;

import fraktikant.tflcstefan.hybrit.app.web.dto.JobDTO;

import java.util.List;

public interface GitHubJobsService {

    List<JobDTO> recommendedJobs(String loggedUsername);
    List<JobDTO> searchForJobs(JobDTO jobDto);
    JobDTO getJobById(String jobId);

}
