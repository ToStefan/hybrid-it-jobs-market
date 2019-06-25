package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.entity.User;
import fraktikant.tflcstefan.hybrit.app.repository.UserRepository;
import fraktikant.tflcstefan.hybrit.app.service.GitHubJobsService;
import fraktikant.tflcstefan.hybrit.app.web.dto.JobDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
@Service
public class GitHubJobsServiceImpl implements GitHubJobsService {

    private final UserRepository userRepository;

    @Override
    public List<JobDTO> recommendedJobs(String loggedUsername) {

        User user = userRepository.findByUsername(loggedUsername);
        String url = "https://jobs.github.com/positions.json?markdown=true&description=" + user.getJobDesc() +
                "&location=" + user.getJobLocation();
        if (user.getFullTime() == true) {
            url += "&full_time=true";
        }

        RestTemplate restTemplate = new RestTemplate();
        List<JobDTO> response = restTemplate.getForObject(url, List.class);

        return response;
    }

    @Override
    public List<JobDTO> searchForJobs(JobDTO jobDto) {

        String url = "https://jobs.github.com/positions.json?markdown=true&description=" + jobDto.getDescription() +
                "&location=" + jobDto.getLocation();
        if (jobDto.getType() == "Full Time") {
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
