package arithmetic.calculator.api.presentation.dto;

import java.util.List;

public record ErrorResponseDTO(int status, String message, List<String> details) {}
