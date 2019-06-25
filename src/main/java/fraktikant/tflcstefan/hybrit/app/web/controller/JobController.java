package fraktikant.tflcstefan.hybrit.app.web.controller;

import fraktikant.tflcstefan.hybrit.app.service.impl.GitHubJobsServiceImpl;
import fraktikant.tflcstefan.hybrit.app.web.dto.JobDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/jobs")
public class JobController {

    private final GitHubJobsServiceImpl gitHubJobsService;

    @PostMapping(value = "/search")
    public ResponseEntity<List<JobDTO>> searchJobs(@RequestBody JobDTO jobDto) {
        List<JobDTO> jobs = gitHubJobsService.searchForJobs(jobDto);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{job-id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable("job-id") String id) {
        JobDTO job = gitHubJobsService.getJobById(id);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/user/{username}")
    public ResponseEntity<List<JobDTO>> recommendedJobs(@PathVariable("username") String username) {
        List<JobDTO> jobs = gitHubJobsService.recommendedJobs(username);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
}
