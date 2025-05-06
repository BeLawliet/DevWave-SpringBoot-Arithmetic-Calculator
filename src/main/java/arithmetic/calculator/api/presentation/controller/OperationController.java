package arithmetic.calculator.api.presentation.controller;

import arithmetic.calculator.api.presentation.dto.ErrorResponseDTO;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import arithmetic.calculator.api.service.IOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping(path = "api")
@RequiredArgsConstructor
@Tag(
    name = "Operations",
    description = "Controller for Operations"
)
public class OperationController {
    private final IOperationService operationService;

    @PostMapping(path = "calculate")
    @Operation(
            summary = "Calculate some operation",
            description = "Does operations using the provided data and returns a result.",
            tags = {"Operations"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Calculate some arithmetic operation",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Create an arithmetic calculation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<OperationResponseDTO> calculate(@Valid @RequestBody OperationRequestDTO request) {
        return new ResponseEntity<>(operationService.calculate(request), HttpStatus.CREATED);
    }

    @GetMapping(path = "history/{id}")
    @Operation(
            summary = "Get operation by Id",
            description = "Get an operation using the provided Id and returns a result.",
            tags = {"Operations"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Get an operation by Id",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get an operation by Id",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OperationResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<OperationResponseDTO> getOperationById(@PathVariable UUID id) {
        return ResponseEntity.ok(operationService.getOperationById(id));
    }

    @DeleteMapping(path = "history/{id}")
    @Operation(
            summary = "Delete operation by Id",
            description = "Delete an operation using the provided Id.",
            tags = {"Operations"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Delete an operation by Id",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Delete an operation by Id",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<String> delOperationById(@PathVariable UUID id) {
        return ResponseEntity.ok(operationService.delOperationById(id));
    }
}
