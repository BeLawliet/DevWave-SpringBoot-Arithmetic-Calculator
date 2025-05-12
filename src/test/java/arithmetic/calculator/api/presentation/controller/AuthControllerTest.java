package arithmetic.calculator.api.presentation.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import arithmetic.calculator.api.config.filter.SecurityConfigTesting;
import arithmetic.calculator.api.presentation.dto.AuthLoginDTO;
import arithmetic.calculator.api.presentation.dto.AuthResponseDTO;
import arithmetic.calculator.api.presentation.dto.AuthUserDTO;
import arithmetic.calculator.api.provider.DataProvider;
import arithmetic.calculator.api.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@Import(SecurityConfigTesting.class)
class AuthControllerTest {
    @MockitoBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterUser() throws Exception {
        // Arrange
        AuthUserDTO request = DataProvider.mockAuthUser();
        AuthResponseDTO response = DataProvider.mockAuthResponse();
        String jsonRequest = this.objectMapper.writeValueAsString(request);

        when(this.userDetailsService.registerUser(request)).thenReturn(response);

        // Act
        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                                                              .content(jsonRequest))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.username").value("Lawliet"))
               .andExpect(jsonPath("$.message").value("User created successfully"))
               .andExpect(jsonPath("$.token").value("abc.def.ghi"));
    }

    @Test
    void testLoginUser() throws Exception {
        // Arrange
        AuthLoginDTO request = DataProvider.mockAuthLogin();
        AuthResponseDTO response = DataProvider.mockAuthResponse();
        String jsonRequest = this.objectMapper.writeValueAsString(request);

        when(this.userDetailsService.loginUser(request)).thenReturn(response);

        // Act
        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                                                           .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Lawliet"))
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.token").value("abc.def.ghi"));
    }

    @Test
    void testLoginFieldBlank() throws Exception {
        // Arrange
        String jsonRequest = """
            {
                "username": "Lawliet"
            }
        """;

        // Act
        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                                                           .content(jsonRequest))
               .andExpect(status().isBadRequest())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(400))
               .andExpect(jsonPath("$.message").value("Invalid operation parameters"))
               .andExpect(jsonPath("$.details[0]").value("must not be blank"));
    }

    @Test
    void testLoginException() throws Exception {
        // Arrange
        AuthLoginDTO request = DataProvider.mockAuthLogin();

        String jsonRequest = objectMapper.writeValueAsString(request);

        when(userDetailsService.loginUser(request)).thenThrow(new BadCredentialsException("Invalid username or password"));

        // Act
        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                                                           .content(jsonRequest))
               .andExpect(status().isBadRequest())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(400))
               .andExpect(jsonPath("$.message").value("Invalid parameters"))
               .andExpect(jsonPath("$.details[0]").value("Invalid username or password"));
    }

    @Test
    void testLoginUnexpectedException() throws Exception {
        // Arrange
        AuthLoginDTO request = DataProvider.mockAuthLogin();

        String jsonRequest = objectMapper.writeValueAsString(request);

        when(userDetailsService.loginUser(request)).thenThrow(new RuntimeException("Something went wrong"));

        // Act
        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                                                           .content(jsonRequest))
               .andExpect(status().isInternalServerError())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(500))
               .andExpect(jsonPath("$.message").value("Internal server error"))
               .andExpect(jsonPath("$.details[0]").value("Something went wrong"));
    }
}
