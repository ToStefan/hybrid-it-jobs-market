package fraktikant.tflcstefan.hybrit.app.web.dto;

import fraktikant.tflcstefan.hybrit.app.entity.EmailNotification;
import fraktikant.tflcstefan.hybrit.app.entity.Role;
import fraktikant.tflcstefan.hybrit.app.entity.WorkingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private List<Role> roles;
    private String jobDesc;
    private String jobLocation;
    private WorkingType workingType;
    private EmailNotification emailNotification;
    private Date notificationTime;
}