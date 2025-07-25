package arithmetic.calculator.api.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "operations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EOperationType operation;

    @Column(name = "operand_a")
    private BigDecimal operandA;

    @Column(name = "operand_b")
    private BigDecimal operandB;

    private BigDecimal result;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @PrePersist
    public void init() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        timestamp = LocalDateTime.now();
    }

    public Operation(EOperationType operation, BigDecimal operandA, BigDecimal operandB, BigDecimal result, UserModel user) {
        this.operation = operation;
        this.operandA = operandA;
        this.operandB = operandB;
        this.result = result;
        this.user = user;
    }
}
