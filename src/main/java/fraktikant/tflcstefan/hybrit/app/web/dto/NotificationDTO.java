package fraktikant.tflcstefan.hybrit.app.web.dto;

import fraktikant.tflcstefan.hybrit.app.entity.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {

    private String jobDesc;
    private String jobLocation;
    private Boolean fullTime;
    private NotificationType notificationType;
    private LocalTime notificationTime;
    private String username;

}
