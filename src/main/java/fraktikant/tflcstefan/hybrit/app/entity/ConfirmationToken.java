package fraktikant.tflcstefan.hybrit.app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "confirmation_tokens")
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="confirmation_token", nullable = false)
    private String confirmationToken;

    @Column(name="created_date", nullable = false)
    private Date createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        this.createdDate = new Date();
        this.confirmationToken = UUID.randomUUID().toString();
    }
}
