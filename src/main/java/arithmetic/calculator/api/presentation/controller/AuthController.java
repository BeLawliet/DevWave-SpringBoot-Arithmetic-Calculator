package arithmetic.calculator.api.presentation.controller;

import arithmetic.calculator.api.presentation.dto.AuthLoginDTO;
import arithmetic.calculator.api.presentation.dto.AuthResponseDTO;
import arithmetic.calculator.api.presentation.dto.AuthUserDTO;
import arithmetic.calculator.api.presentation.dto.ErrorResponseDTO;
import arithmetic.calculator.api.service.impl.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/auth")
@RequiredArgsConstructor
@Tag(
    name = "Authentication",
    description = "Controller for Authentication"
)
public class AuthController {
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping(path = "register")
    @Operation(
        summary = "Register a new user",
        description = "Creates a new user account using the provided credentials and returns an authentication token.",
        tags = {"Authentication"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "User registration data",
                required = true
        ),
        responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class)
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
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody AuthUserDTO request) {
        return new ResponseEntity<>(this.userDetailsService.registerUser(request), HttpStatus.CREATED);
    }

    @PostMapping(path = "login")
    @Operation(
            summary = "Login a user",
            description = "Login a user account using the provided credentials and returns an authentication token.",
            tags = {"Authentication"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Logging user",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User logged successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponseDTO.class)
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
    public ResponseEntity<AuthResponseDTO> loginUser(@Valid @RequestBody AuthLoginDTO request) {
        return ResponseEntity.ok(this.userDetailsService.loginUser(request));
    }
}
