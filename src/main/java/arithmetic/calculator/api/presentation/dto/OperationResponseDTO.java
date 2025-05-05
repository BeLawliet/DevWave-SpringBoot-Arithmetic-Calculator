package arithmetic.calculator.api.presentation.dto;

import arithmetic.calculator.api.persistence.model.EOperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationResponseDTO {
        private UUID id;
        private EOperationType operation;
        private BigDecimal operandA;
        private BigDecimal operandB;
        private BigDecimal result;
        private LocalDateTime timestamp;
        private Long userId;
}
