package arithmetic.calculator.api.presentation.dto;

import arithmetic.calculator.api.persistence.model.EOperationType;
import java.math.BigDecimal;

public record OperationRequestDTO(EOperationType operation, BigDecimal operandA, BigDecimal operandB) { }
