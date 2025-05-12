package arithmetic.calculator.api.provider;

import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.persistence.model.UserModel;
import arithmetic.calculator.api.presentation.dto.AuthLoginDTO;
import arithmetic.calculator.api.presentation.dto.AuthResponseDTO;
import arithmetic.calculator.api.presentation.dto.AuthUserDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DataProvider {
    public static UserModel mockUser() {
        return new UserModel("Lawliet", "Lawliet", "lawliet@lawliet.com");
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

    public static OperationResponseDTO mockOperationResponse() {
        return new OperationResponseDTO(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                                        EOperationType.ADDITION,
                                        BigDecimal.valueOf(5),
                                        BigDecimal.valueOf(5),
                                        BigDecimal.TEN,
                                        LocalDateTime.of(2025, 1, 1, 12, 0),
                                 1L);
    }
}
