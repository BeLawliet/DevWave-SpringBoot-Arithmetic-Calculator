package arithmetic.calculator.api.service;

import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import arithmetic.calculator.api.presentation.dto.PageResponseDTO;
import java.time.LocalDateTime;
import java.util.UUID;

public interface IOperationService {
    OperationResponseDTO calculate(OperationRequestDTO request);

    PageResponseDTO<OperationResponseDTO> getUserHistory(EOperationType operation, LocalDateTime startDate, LocalDateTime endDate, String field, String direction, int page, int size);

    OperationResponseDTO getOperationById(UUID id);

    String delOperationById(UUID id);
}
