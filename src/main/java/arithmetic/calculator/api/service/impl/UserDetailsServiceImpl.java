package arithmetic.calculator.api.service.impl;

import arithmetic.calculator.api.persistence.model.UserModel;
import arithmetic.calculator.api.persistence.repository.IUserRepository;
import arithmetic.calculator.api.presentation.dto.AuthLoginDTO;
import arithmetic.calculator.api.presentation.dto.AuthResponseDTO;
import arithmetic.calculator.api.presentation.dto.AuthUserDTO;
import arithmetic.calculator.api.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = this.userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("Username or password incorrect"));
        return new User(userModel.getUsername(), userModel.getPassword(), true, true, true, true, List.of());
    }

    public AuthResponseDTO registerUser(AuthUserDTO request) {
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new IllegalArgumentException("All fields are requireds");
        }

        UserModel newUser = new UserModel(request.username(), passwordEncoder.encode(request.password()), request.email());

        UserModel userSaved = this.userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved.getUsername(), userSaved.getPassword(), List.of());

        String accessToken = this.jwtUtils.createToken(authentication);

        return new AuthResponseDTO(userSaved.getUsername(), "User created successfully", accessToken, true);
    }

    public AuthResponseDTO loginUser(AuthLoginDTO request) {
        String username = request.username();
        String password = request.password();

        Authentication authentication = this.authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = this.jwtUtils.createToken(authentication);

        return new AuthResponseDTO(username, "User logged successfully", accessToken, true);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), List.of());
    }
}
