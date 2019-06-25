package fraktikant.tflcstefan.hybrit.app.web.dto;

import fraktikant.tflcstefan.hybrit.app.entity.EmailNotification;
import fraktikant.tflcstefan.hybrit.app.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private Boolean isEnabled;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private List<Role> roles;
    private String jobDesc;
    private String jobLocation;
    private Boolean fullTime;
    private EmailNotification emailNotification;
    private LocalTime notificationTime;
}