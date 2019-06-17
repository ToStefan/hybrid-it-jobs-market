package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.service.GitHubJobsService;
import fraktikant.tflcstefan.hybrit.app.web.dto.JobDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class GitHubJobsServiceImpl implements GitHubJobsService {

    private final UserServiceImpl userService;

    @Override
    public List<JobDTO> recommendedJobs(Long userId) {

        UserDTO userDto = userService.findById(userId);
        String url = "https://jobs.github.com/positions.json?markdown=true&description=" + userDto.getJobDesc() +
                "&location=" + userDto.getJobLocation();
        if (userDto.getFullTime()) {
            url += "&full_time=true";
        }

        RestTemplate restTemplate = new RestTemplate();
        List<JobDTO> response = restTemplate.getForObject(url, List.class);

        return response;
    }

    @Override
    public JobDTO getJobById(String jobId) {

        String url = "https://jobs.github.com/positions/" + jobId + ".json?markdown=true";
        RestTemplate restTemplate = new RestTemplate();
        JobDTO response = restTemplate.getForObject(url, JobDTO.class);

        return response;
    }
}
