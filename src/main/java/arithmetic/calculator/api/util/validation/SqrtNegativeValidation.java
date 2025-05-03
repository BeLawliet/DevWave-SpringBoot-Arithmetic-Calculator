package arithmetic.calculator.api.util.validation;

import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.util.annotation.SqrtNegative;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class SqrtNegativeValidation implements ConstraintValidator<SqrtNegative, OperationRequestDTO> {
    @Override
    public boolean isValid(OperationRequestDTO request, ConstraintValidatorContext context) {
        boolean operation = request.getOperation().equals(EOperationType.SQUARE_ROOT);
        boolean isNegative = request.getOperandA().compareTo(BigDecimal.ZERO) <= 0;

        return !(operation && isNegative);
    }
}
