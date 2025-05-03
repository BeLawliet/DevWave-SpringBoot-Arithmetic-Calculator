package arithmetic.calculator.api.service;

import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;

import java.util.UUID;

public interface IOperationService {
    OperationResponseDTO calculate(OperationRequestDTO request);
    OperationResponseDTO getOperationById(UUID id);
    String delOperationById(UUID id);
}
