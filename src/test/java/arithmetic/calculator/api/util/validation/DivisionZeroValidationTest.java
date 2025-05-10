package arithmetic.calculator.api.util.validation;

import static org.junit.jupiter.api.Assertions.*;
import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class DivisionZeroValidationTest {
    private DivisionZeroValidation validation;
    private OperationRequestDTO request;

    @BeforeEach
    void setUp() {
        validation = new DivisionZeroValidation();
    }

    @Test
    void testValidationDivision() {
        request = new OperationRequestDTO(EOperationType.DIVISION, BigDecimal.TEN, BigDecimal.ONE);
        assertTrue(validation.isValid(request, null));
    }

    @Test
    void testDivisionByZeroOperandA() {
        request = new OperationRequestDTO(EOperationType.DIVISION, BigDecimal.ZERO, BigDecimal.ONE);
        assertFalse(validation.isValid(request, null));
    }

    @Test
    void testDivisionByZeroOperandB() {
        request = new OperationRequestDTO(EOperationType.DIVISION, BigDecimal.ONE, BigDecimal.ZERO);
        assertFalse(validation.isValid(request, null));
    }

    @Test
    void testNotDivision() {
        request = new OperationRequestDTO(EOperationType.ADDITION, BigDecimal.ZERO, BigDecimal.ZERO);
        assertTrue(validation.isValid(request, null));
    }
}
