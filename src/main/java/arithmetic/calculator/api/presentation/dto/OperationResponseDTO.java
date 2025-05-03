package arithmetic.calculator.api.presentation.dto;

import arithmetic.calculator.api.persistence.model.EOperationType;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OperationResponseDTO(UUID id, EOperationType operation, BigDecimal operandA, BigDecimal operandB, BigDecimal result, LocalDateTime timestamp) {}
