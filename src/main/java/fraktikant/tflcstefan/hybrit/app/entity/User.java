package fraktikant.tflcstefan.hybrit.app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Column(name = "job-desc")
    private String jobDesc;

    @Column(name = "job-location")
    private String jobLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "working-type")
    private WorkingType workingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "email-notification", nullable = false)
    private EmailNotification emailNotification;

    @Column(name = "notification-time")
    private Date notificationTime;
}