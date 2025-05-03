package arithmetic.calculator.api.service.impl;

import arithmetic.calculator.api.persistence.model.Operation;
import arithmetic.calculator.api.persistence.repository.IOperationRepository;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import arithmetic.calculator.api.service.IOperationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Override
    public OperationResponseDTO calculate(OperationRequestDTO request) {
        BigDecimal opA = request.getOperandA();
        BigDecimal opB = request.getOperandB();

        BigDecimal result = switch (request.getOperation()) {
            case ADDITION -> opA.add(opB);

            case SUBTRACTION -> opA.subtract(opB);

            case MULTIPLICATION -> opA.multiply(opB);

            case DIVISION -> opA.divide(opB, 2, RoundingMode.HALF_UP);

            case POWER -> opA.pow(opB.intValueExact());

            case SQUARE_ROOT -> BigDecimal.valueOf(Math.sqrt(opA.doubleValue())).round(new MathContext(7));
        };

        Operation newRegister = new Operation(request.getOperation(), opA, opB, result);
        Operation saved = this.operationRepository.save(newRegister);

        return this.modelMapper.map(saved, OperationResponseDTO.class);
    }

    @Override
    public OperationResponseDTO getOperationById(UUID id) {
        Optional<Operation> optOperation = this.operationRepository.findById(id);

        if (optOperation.isEmpty()) {
            throw new IllegalArgumentException(String.format("Operation with ID { %s } not found", id));
        }

        return this.modelMapper.map(optOperation.get(), OperationResponseDTO.class);
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
}
