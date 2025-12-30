package arithmetic.calculator.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthUserDTO(@NotBlank  String username, @NotBlank String password, @NotBlank String email) {}
