package arithmetic.calculator.api.presentation.controller;

import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.presentation.dto.ErrorResponseDTO;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import arithmetic.calculator.api.presentation.dto.PageResponseDTO;
import arithmetic.calculator.api.service.IOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
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
        return new ResponseEntity<>(this.operationService.calculate(request), HttpStatus.CREATED);
    }


    @GetMapping(path = "history")
    @Operation(
            summary = "Get user's operation history",
            description = "Returns a paginated list of operations performed by the user, with optional filters by type and date range.",
            tags = {"Operations"},
            parameters = {
                    @Parameter(
                            name = "operation",
                            description = "Type of operation to filter (e.g., ADDITION, SUBTRACTION, etc.)",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = EOperationType.class)
                    ),
                    @Parameter(
                            name = "startDate",
                            description = "Start of date range filter (ISO-8601 format)",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", format = "date-time")
                    ),
                    @Parameter(
                            name = "endDate",
                            description = "End of date range filter (ISO-8601 format)",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", format = "date-time")
                    ),
                    @Parameter(
                            name = "field",
                            description = "Field to sort by (e.g., 'timestamp')",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", defaultValue = "timestamp")
                    ),
                    @Parameter(
                            name = "direction",
                            description = "Sort direction: 'asc' or 'desc'",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", allowableValues = {"asc", "desc"}, defaultValue = "asc")
                    ),
                    @Parameter(
                            name = "page",
                            description = "Page number (zero-based)",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "integer", defaultValue = "0")
                    ),
                    @Parameter(
                            name = "size",
                            description = "Page size",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "integer", defaultValue = "10")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved operation history",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PageResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
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
    public ResponseEntity<PageResponseDTO<OperationResponseDTO>> getUserHistory(@RequestParam(required = false) EOperationType operation,
                                                          @RequestParam(required = false) LocalDateTime startDate,
                                                          @RequestParam(required = false) LocalDateTime endDate,
                                                          @RequestParam(defaultValue = "timestamp") String field,
                                                          @RequestParam(defaultValue = "asc") String direction,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {

        PageResponseDTO<OperationResponseDTO> response = this.operationService.getUserHistory(operation, startDate, endDate, field, direction, page, size);

        return ResponseEntity.ok(response);
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
        return ResponseEntity.ok(this.operationService.getOperationById(id));
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
        return ResponseEntity.ok(this.operationService.delOperationById(id));
    }
}
