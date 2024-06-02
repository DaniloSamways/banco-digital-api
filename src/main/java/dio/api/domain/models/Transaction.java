package dio.api.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="sender_id")
    private String senderId;

    @Column(name="receiver_id")
    private String receiverId;

    private BigDecimal amount;

    @CreationTimestamp
    private Timestamp timestamp;
}
