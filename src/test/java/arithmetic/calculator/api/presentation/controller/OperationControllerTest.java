package arithmetic.calculator.api.presentation.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import arithmetic.calculator.api.presentation.dto.PageResponseDTO;
import arithmetic.calculator.api.provider.DataProvider;
import arithmetic.calculator.api.service.IOperationService;
import arithmetic.calculator.api.util.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OperationControllerTest {
    private String accessToken;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockitoBean
    private IOperationService operationService;

    @BeforeEach
    void setUp() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("Lawliet", null, List.of());
        this.accessToken = "Bearer " + this.jwtUtils.createToken(authentication);
    }

    @Test
    void testCalculateValidRequest() throws Exception {
        // Arrange
        OperationResponseDTO response = DataProvider.mockAdditionResponse();

        String jsonRequest = """
                    {
                        "operation": "ADDITION",
                        "operandA": 5,
                        "operandB": 5
                    }
                """;

        when(this.operationService.calculate(any())).thenReturn(response);

        // Act
        mockMvc.perform(post("/api/calculate").contentType(MediaType.APPLICATION_JSON)
                                                        .header(HttpHeaders.AUTHORIZATION, this.accessToken)
                                                        .content(jsonRequest))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id").value("550e8400-e29b-41d4-a716-446655440000"))
               .andExpect(jsonPath("$.result").value("10"));
    }

    @Test
    void testGetHistoryValidRequest() throws Exception {
        // Arrange
        OperationResponseDTO op1 = DataProvider.mockAdditionResponse();

        OperationResponseDTO op2 = new OperationResponseDTO();
        op2.setId(UUID.randomUUID());
        op2.setOperation(EOperationType.valueOf("SUBTRACTION"));
        op2.setResult(BigDecimal.valueOf(2));

        PageResponseDTO<OperationResponseDTO> pageResponse = new PageResponseDTO<>();
        pageResponse.setContent(List.of(op1, op2));
        pageResponse.setTotalElements(2L);
        pageResponse.setTotalPages(1);
        pageResponse.setSize(10);
        pageResponse.setPage(0);

        when(this.operationService.getUserHistory(null, null, null, "timestamp", "asc", 0,10)).thenReturn(pageResponse);

        // Act
        mockMvc.perform(get("/api/history").param("field", "timestamp")
                                                     .param("direction", "asc")
                                                     .param("page", "0")
                                                     .param("size", "10")
                                                     .header(HttpHeaders.AUTHORIZATION, this.accessToken))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content").isArray())
               .andExpect(jsonPath("$.content.length()").value(2))
               .andExpect(jsonPath("$.totalElements").value(2))
               .andExpect(jsonPath("$.totalPages").value(1))
               .andExpect(jsonPath("$.size").value(10))
               .andExpect(jsonPath("$.page").value(0));
    }

    @Test
    void testGetOperationByIdValidRequest() throws Exception {
        // Arrange
        OperationResponseDTO response = DataProvider.mockAdditionResponse();

        when(this.operationService.getOperationById(response.getId())).thenReturn(response);

        // Act
        mockMvc.perform(get("/api/history/{id}", response.getId()).contentType(MediaType.APPLICATION_JSON)
                                                                            .header(HttpHeaders.AUTHORIZATION, this.accessToken))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id").value("550e8400-e29b-41d4-a716-446655440000"))
               .andExpect(jsonPath("$.result").value("10"));
    }

    @Test
    void testDelOperationByIdValidRequest() throws Exception {
        // Arrange
        String expectedMessage = "Operation with ID 550e8400-e29b-41d4-a716-446655440000 deleted";

        when(this.operationService.delOperationById(any())).thenReturn(expectedMessage);

        // Act
        mockMvc.perform(delete("/api/history/{id}", "550e8400-e29b-41d4-a716-446655440000").header(HttpHeaders.AUTHORIZATION, this.accessToken))
               .andExpect(status().isOk())
               .andExpect(content().string(expectedMessage));
    }

    @Test
    void testDelOperationBydIdException() throws Exception {
        // Arrange
        String expectedMessage = "Operation with ID 550e8400-e29b-41d4-a716-446655440000 deleted";

        when(this.operationService.delOperationById(any())).thenThrow(new IllegalArgumentException(expectedMessage));

        // Act
        mockMvc.perform(delete("/api/history/{id}", "550e8400-e29b-41d4-a716-446655440000").header(HttpHeaders.AUTHORIZATION, this.accessToken))
               .andExpect(status().isBadRequest())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(400))
               .andExpect(jsonPath("$.message").value("Invalid operation parameters"))
               .andExpect(jsonPath("$.details[0]").value(expectedMessage));
    }
}
