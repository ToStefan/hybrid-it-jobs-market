package fraktikant.tflcstefan.hybrit.app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Column(name = "job_desc")
    private String jobDesc;

    @Column(name = "job_location")
    private String jobLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "working_type")
    private WorkingType workingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "email_notification", nullable = false)
    private EmailNotification emailNotification;

    @Column(name = "notification_time")
    private Date notificationTime;
}