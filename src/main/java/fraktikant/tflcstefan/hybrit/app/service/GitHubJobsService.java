package fraktikant.tflcstefan.hybrit.app.service;

import fraktikant.tflcstefan.hybrit.app.web.dto.JobDTO;

import java.util.List;

public interface GitHubJobsService {

    List<JobDTO> recommendedJobs(Long userId);
    JobDTO getJobById(String jobId);

}
