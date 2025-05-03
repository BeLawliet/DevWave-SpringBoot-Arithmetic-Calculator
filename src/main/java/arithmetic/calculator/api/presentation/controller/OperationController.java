package arithmetic.calculator.api.presentation.controller;

import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import arithmetic.calculator.api.service.IOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping(path = "api")
@RequiredArgsConstructor
public class OperationController {
    private final IOperationService operationService;

    @PostMapping(path = "calculate")
    public ResponseEntity<OperationResponseDTO> calculate(@RequestBody OperationRequestDTO request) {
        return ResponseEntity.ok(operationService.calculate(request));
    }

    @GetMapping(path = "history/{id}")
    public ResponseEntity<OperationResponseDTO> getOperationById(@PathVariable UUID id) {
        return ResponseEntity.ok(operationService.getOperationById(id));
    }

    @DeleteMapping(path = "history/{id}")
    public ResponseEntity<String> delOperationById(@PathVariable UUID id) {
        return ResponseEntity.ok(operationService.delOperationById(id));
    }
}
