package bank.security.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emails")
public class Email {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_id_seq")
    @SequenceGenerator(name = "email_id_seq", sequenceName = "email_id_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @Column(name = "email_text", unique = true, nullable = false)
    private String emailText;
}
