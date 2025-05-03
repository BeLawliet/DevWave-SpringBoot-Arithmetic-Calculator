package arithmetic.calculator.api.service.impl;

import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.persistence.model.Operation;
import arithmetic.calculator.api.persistence.repository.IOperationRepository;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import arithmetic.calculator.api.service.IOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements IOperationService {
    private final IOperationRepository operationRepository;

    @Override
    public OperationResponseDTO calculate(OperationRequestDTO request) {
        BigDecimal opA = request.operandA();
        BigDecimal opB = request.operandB();
        BigDecimal result;

        // Validar rangos
        if (opA.compareTo(new BigDecimal("-1000000")) < 0 || opA.compareTo(new BigDecimal("1000000")) > 0) {
            throw new IllegalArgumentException("operandA must be between -1,000,000 and 1,000,000");
        }

        if (request.operation() != EOperationType.SQUARE_ROOT) {
            if (opB == null) throw new IllegalArgumentException("operandB is required for this operation");

            if (opB.compareTo(new BigDecimal("-1000000")) < 0 || opB.compareTo(new BigDecimal("1000000")) > 0) {
                throw new IllegalArgumentException("operandB must be between -1,000,000 and 1,000,000");
            }
        }

        result = switch (request.operation()) {
            case ADDITION -> opA.add(opB);

            case SUBTRACTION -> opA.subtract(opB);

            case MULTIPLICATION -> opA.multiply(opB);

            case DIVISION -> {
                if (opB.compareTo(BigDecimal.ZERO) == 0) {
                    throw new IllegalArgumentException("Division by zero is not allowed");
                }

                yield opA.divide(opB, 2, RoundingMode.HALF_UP);
            }

            case POWER -> opA.pow(opB.intValueExact());

            case SQUARE_ROOT -> {
                if (opA.compareTo(BigDecimal.ZERO) < 0) {
                    throw new IllegalArgumentException("Cannot calculate square root of a negative number");
                }

                yield BigDecimal.valueOf(Math.sqrt(opA.doubleValue())).round(new MathContext(7));
            }
        };

        Operation newRegister = new Operation(request.operation(), opA, opB, result);
        Operation saved = this.operationRepository.save(newRegister);

        return toOperationResponseDTO(saved);
    }

    @Override
    public OperationResponseDTO getOperationById(UUID id) {
        Optional<Operation> optOperation = this.operationRepository.findById(id);

        if (optOperation.isEmpty()) {
            throw new IllegalArgumentException(String.format("Operation with ID { %s } not found", id));
        }

        return toOperationResponseDTO(optOperation.get());
    }

    @Override
    public String delOperationById(UUID id) {
        Optional<Operation> optOperation = this.operationRepository.findById(id);

        if (optOperation.isEmpty()) {
            throw new IllegalArgumentException(String.format("Operation with ID { %s } not found", id));
        }

        this.operationRepository.delete(optOperation.get());

        return String.format("Operation with ID { %s } deleted", id);
    }

    private OperationResponseDTO toOperationResponseDTO(Operation operation) {
        return new OperationResponseDTO(operation.getId(),
                                        operation.getOperation(),
                                        operation.getOperandA(),
                                        operation.getOperandB(),
                                        operation.getResult(),
                                        operation.getTimestamp());
    }
}
