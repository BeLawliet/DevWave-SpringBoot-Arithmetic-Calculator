package arithmetic.calculator.api.util.validation;

import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.util.annotation.DivisionByZero;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class DivisionZeroValidation implements ConstraintValidator<DivisionByZero, OperationRequestDTO> {
    @Override
    public boolean isValid(OperationRequestDTO request, ConstraintValidatorContext context) {
        boolean isOpAZero = request.getOperandA().compareTo(BigDecimal.ZERO) == 0;
        boolean isOpBZero = request.getOperandB().compareTo(BigDecimal.ZERO) == 0;
        boolean isDivision = request.getOperation().equals(EOperationType.DIVISION);

        return !(isDivision && (isOpAZero || isOpBZero));
    }
}
