package arithmetic.calculator.api.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import arithmetic.calculator.api.persistence.model.UserModel;
import arithmetic.calculator.api.persistence.repository.IUserRepository;
import arithmetic.calculator.api.presentation.dto.AuthLoginDTO;
import arithmetic.calculator.api.presentation.dto.AuthResponseDTO;
import arithmetic.calculator.api.presentation.dto.AuthUserDTO;
import arithmetic.calculator.api.provider.DataProvider;
import arithmetic.calculator.api.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void testLoadUserByUsernameReturnUserExists() {
        // Arrange
        UserModel userModel = DataProvider.mockUser();
        when(this.userRepository.findByUsername("Lawliet")).thenReturn(Optional.of(userModel));

        // Act
        UserDetails result = this.userDetailsService.loadUserByUsername("Lawliet");

        // Asserts
        assertEquals("Lawliet", result.getUsername());
        assertEquals("Lawliet", result.getPassword());
    }

    @Test
    void testLoadUserByUsernameReturnException() {
        // Arrange
        when(this.userRepository.findByUsername("Lawliet")).thenReturn(Optional.empty());

        // Asserts
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> this.userDetailsService.loadUserByUsername("Lawliet"));
        assertEquals("Username or password incorrect", exception.getMessage());
    }

    @Test
    void testRegisterUserSuccess() {
        // Arrange
        AuthUserDTO request = DataProvider.mockAuthUser();
        UserModel userModel = DataProvider.mockUser();

        when(this.passwordEncoder.encode("Lawliet")).thenReturn("Lawliet");
        when(this.userRepository.save(any())).thenReturn(userModel);
        when(this.jwtUtils.createToken(any())).thenReturn("abc.def.ghi");

        // Act
        AuthResponseDTO response = this.userDetailsService.registerUser(request);

        // Asserts
        assertEquals("Lawliet", response.username());
        assertEquals("User created successfully", response.message());
        assertEquals("abc.def.ghi", response.token());
    }

    @Test
    void testLoginUserSuccess() {
        // Arrange
        AuthLoginDTO request = DataProvider.mockAuthLogin();
        UserModel userModel = DataProvider.mockUser();

        when(this.userRepository.findByUsername("Lawliet")).thenReturn(Optional.of(userModel));
        when(this.passwordEncoder.matches("Lawliet", "Lawliet")).thenReturn(true);
        when(this.jwtUtils.createToken(any())).thenReturn("abc.def.ghi");

        // Act
        AuthResponseDTO response = this.userDetailsService.loginUser(request);

        // Asserts
        assertEquals("Lawliet", response.username());
        assertEquals("User logged successfully", response.message());
        assertEquals("abc.def.ghi", response.token());
    }

    @Test
    void testLoginUserException() {
        // Arrange
        AuthLoginDTO request = DataProvider.mockAuthLogin();
        UserModel userModel = DataProvider.mockUser();

        when(this.userRepository.findByUsername("Lawliet")).thenReturn(Optional.of(userModel));
        when(this.passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Asserts
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> this.userDetailsService.loginUser(request));
        assertEquals("Invalid username or password", exception.getMessage());
    }
}
