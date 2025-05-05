package arithmetic.calculator.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginDTO(@NotBlank String username, @NotBlank String password) {}
