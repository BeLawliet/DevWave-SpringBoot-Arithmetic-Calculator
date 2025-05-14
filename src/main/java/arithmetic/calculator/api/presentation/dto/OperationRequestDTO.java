package arithmetic.calculator.api.presentation.dto;

import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.util.annotation.DivisionByZero;
import arithmetic.calculator.api.util.annotation.SqrtNegative;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DivisionByZero(message = "Division by zero is not allowed")
@SqrtNegative(message = "Cannot calculate square root of zero or negative number")
public class OperationRequestDTO {
    @Nonnull
    private EOperationType operation;

    @DecimalMin(value = "-1000000")
    @DecimalMax(value = "1000000")
    private BigDecimal operandA = BigDecimal.ZERO;

    @DecimalMin(value = "-1000000")
    @DecimalMax(value = "1000000")
    private BigDecimal operandB = BigDecimal.ZERO;
}
