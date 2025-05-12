package arithmetic.calculator.api.provider;

import arithmetic.calculator.api.persistence.model.UserModel;
import arithmetic.calculator.api.presentation.dto.AuthLoginDTO;
import arithmetic.calculator.api.presentation.dto.AuthResponseDTO;
import arithmetic.calculator.api.presentation.dto.AuthUserDTO;

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
}
