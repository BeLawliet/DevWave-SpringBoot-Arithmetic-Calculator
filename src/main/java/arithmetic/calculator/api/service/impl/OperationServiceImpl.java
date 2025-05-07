package arithmetic.calculator.api.service.impl;

import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.persistence.model.Operation;
import arithmetic.calculator.api.persistence.model.UserModel;
import arithmetic.calculator.api.persistence.repository.IOperationRepository;
import arithmetic.calculator.api.persistence.repository.IUserRepository;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import arithmetic.calculator.api.presentation.dto.PageResponseDTO;
import arithmetic.calculator.api.service.IOperationService;
import arithmetic.calculator.api.util.OperationSpecsUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements IOperationService {
    private final IOperationRepository operationRepository;
    private final IUserRepository userRepository;
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

        Operation newRegister = new Operation(request.getOperation(), opA, opB, result, getCurrentUser());
        Operation saved = this.operationRepository.save(newRegister);

        return this.modelMapper.map(saved, OperationResponseDTO.class);
    }

    @Override
    public PageResponseDTO<OperationResponseDTO> getUserHistory(EOperationType operation, LocalDateTime startDate, LocalDateTime endDate, String field, String direction, int page, int size) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(field).descending() : Sort.by(field).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Operation> spec = Specification.where(OperationSpecsUtils.byUser(this.getCurrentUser().getId()));

        if (operation != null) {
            spec = spec.and(OperationSpecsUtils.byOperationType(operation));
        }

        if (startDate != null && endDate != null) {
            spec = spec.and(OperationSpecsUtils.betweenDates(startDate, endDate));
        }

        Page<Operation> resultPage = this.operationRepository.findAll(spec, pageable);
        Page<OperationResponseDTO> mapped = resultPage.map(op -> this.modelMapper.map(op, OperationResponseDTO.class));

        return new PageResponseDTO<>(mapped);
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

    private UserModel getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        return this.userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }
}
