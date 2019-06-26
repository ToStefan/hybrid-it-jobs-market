package fraktikant.tflcstefan.hybrit.app.scheduler;

import fraktikant.tflcstefan.hybrit.app.web.dto.NotificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/scheduler")
public class SchedulerController {

    private final SchedulerService schedulerService;

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody NotificationDTO notificationDTO){
        schedulerService.addTask(notificationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}")
    public void deleteTask(@PathVariable String username){
        schedulerService.removeTask(username);
    }
}
