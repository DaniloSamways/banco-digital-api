package dio.api.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Length(min = 11, max = 14)
    @Column(unique = true)
    private String document;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    @Length(min = 8)
    private String password;

    private BigDecimal balance;
}
