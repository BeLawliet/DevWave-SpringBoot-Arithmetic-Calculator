package arithmetic.calculator.api.util.validation;

import static org.junit.jupiter.api.Assertions.*;
import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class SqrtNegativeValidationTest {
    private SqrtNegativeValidation validation;
    private OperationRequestDTO request;

    @BeforeEach
    void setUp() {
        validation = new SqrtNegativeValidation();
    }

    @Test
    void testValidationSqrtNegative() {
        request = new OperationRequestDTO(EOperationType.SQUARE_ROOT, BigDecimal.valueOf(-1), BigDecimal.ZERO);
        assertFalse(validation.isValid(request, null));
    }

    @Test
    void testValidationSqrtNotNegative() {
        request = new OperationRequestDTO(EOperationType.SQUARE_ROOT, BigDecimal.TEN, BigDecimal.ZERO);
        assertTrue(validation.isValid(request, null));
    }

    @Test
    void testValidationNotSqrt() {
        request = new OperationRequestDTO(EOperationType.ADDITION, BigDecimal.TEN, BigDecimal.ZERO);
        assertTrue(validation.isValid(request, null));
    }
}
