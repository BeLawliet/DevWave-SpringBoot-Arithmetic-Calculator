package arithmetic.calculator.api.service;

import arithmetic.calculator.api.presentation.dto.RegisterUserDTO;

public interface IUserService {
    String registerNewUser(RegisterUserDTO request);
}
