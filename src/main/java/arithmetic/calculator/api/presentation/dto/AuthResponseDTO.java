package arithmetic.calculator.api.presentation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "token", "status"})
public record AuthResponseDTO(String username, String message, String token, boolean status) {}
