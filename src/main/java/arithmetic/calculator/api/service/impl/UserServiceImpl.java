package arithmetic.calculator.api.service.impl;

import arithmetic.calculator.api.persistence.model.UserModel;
import arithmetic.calculator.api.persistence.repository.IUserRepository;
import arithmetic.calculator.api.presentation.dto.RegisterUserDTO;
import arithmetic.calculator.api.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String registerNewUser(RegisterUserDTO request) {
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new IllegalArgumentException("All fields are requireds");
        }

        UserModel newUser = new UserModel(request.username(), passwordEncoder.encode(request.password()), request.email());

        this.repository.save(newUser);

        return "User saved successfully";
    }
}
