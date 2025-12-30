package arithmetic.calculator.api.provider;

import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.persistence.model.Operation;
import arithmetic.calculator.api.persistence.model.UserModel;
import arithmetic.calculator.api.presentation.dto.AuthLoginDTO;
import arithmetic.calculator.api.presentation.dto.AuthResponseDTO;
import arithmetic.calculator.api.presentation.dto.AuthUserDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class DataProvider {
    public static UserModel mockUser() {
        return new UserModel(1L, "Lawliet", "Lawliet", "lawliet@lawliet.com", LocalDateTime.of(2025, 1, 1, 12, 0), List.of());
    }

    public static AuthUserDTO mockAuthUser() {
        return new AuthUserDTO("Lawliet", "Lawliet", "lawliet@lawliet.com");
    }

    public static AuthLoginDTO mockAuthLogin() {
        return new AuthLoginDTO("Lawliet", "Lawliet");
    }

    public static AuthResponseDTO mockAuthResponse() {
        return new AuthResponseDTO("Lawliet", "User created successfully", "abc.def.ghi");
    }

    public static Operation mockOperation() {
        return new Operation(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                             EOperationType.ADDITION,
                             BigDecimal.valueOf(5),
                             BigDecimal.valueOf(5),
                             BigDecimal.TEN,
                             LocalDateTime.of(2025, 1, 1, 12, 0),
                             mockUser());
    }

    public static OperationResponseDTO mockAdditionResponse() {
        return new OperationResponseDTO(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                                        EOperationType.ADDITION,
                                        BigDecimal.valueOf(5),
                                        BigDecimal.valueOf(5),
                                        BigDecimal.TEN,
                                        LocalDateTime.of(2025, 1, 1, 12, 0),
                                 1L);
    }

    public static OperationResponseDTO mockSubtractionResponse() {
        return new OperationResponseDTO(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
                                        EOperationType.SUBTRACTION,
                                        BigDecimal.valueOf(9),
                                        BigDecimal.valueOf(3),
                                        BigDecimal.valueOf(6),
                                        LocalDateTime.of(2025, 1, 1, 12, 0),
                                 1L);
    }

    public static OperationResponseDTO mockMultiplicationResponse() {
        return new OperationResponseDTO(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
                                        EOperationType.MULTIPLICATION,
                                        BigDecimal.valueOf(4),
                                        BigDecimal.valueOf(3),
                                        BigDecimal.valueOf(12),
                                        LocalDateTime.of(2025, 1, 1, 12, 0),
                                 1L);
    }

    public static OperationResponseDTO mockDivisionResponse() {
        return new OperationResponseDTO(UUID.fromString("550e8400-e29b-41d4-a716-446655440003"),
                                        EOperationType.DIVISION,
                                        BigDecimal.valueOf(10),
                                        BigDecimal.valueOf(4),
                                        BigDecimal.valueOf(2.50),
                                        LocalDateTime.of(2025, 1, 1, 12, 0),
                                 1L);
    }

    public static OperationResponseDTO mockPowerResponse() {
        return new OperationResponseDTO(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"),
                                        EOperationType.POWER,
                                        BigDecimal.valueOf(2),
                                        BigDecimal.valueOf(3),
                                        BigDecimal.valueOf(8),
                                        LocalDateTime.of(2025, 1, 1, 12, 0),
                                 1L);
    }

    public static OperationResponseDTO mockSquareRootResponse() {
        return new OperationResponseDTO(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"),
                                        EOperationType.SQUARE_ROOT,
                                        BigDecimal.valueOf(9),
                                null,
                                        BigDecimal.valueOf(3.0),
                                        LocalDateTime.of(2025, 1, 1, 12, 0),
                                  1L);
    }
}
