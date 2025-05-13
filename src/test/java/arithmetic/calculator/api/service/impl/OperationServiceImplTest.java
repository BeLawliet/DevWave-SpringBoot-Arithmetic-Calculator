package arithmetic.calculator.api.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.persistence.model.Operation;
import arithmetic.calculator.api.persistence.repository.IOperationRepository;
import arithmetic.calculator.api.persistence.repository.IUserRepository;
import arithmetic.calculator.api.presentation.dto.OperationRequestDTO;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import arithmetic.calculator.api.provider.DataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class OperationServiceImplTest {
    @Mock
    private IOperationRepository operationRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OperationServiceImpl operationService;

    @Test
    void testCalculateAdditionOperation() {
        // Arrange
        this.mockAuthenticatedUser();

        Operation operation = DataProvider.mockOperation();
        OperationRequestDTO request = new OperationRequestDTO(EOperationType.ADDITION, BigDecimal.valueOf(5), BigDecimal.valueOf(5));

        when(this.operationRepository.save(any(Operation.class))).thenReturn(operation);
        when(this.modelMapper.map(operation, OperationResponseDTO.class)).thenReturn(DataProvider.mockAdditionResponse());

        // Act
        OperationResponseDTO response = this.operationService.calculate(request);

        // Asserts
        assertEquals(EOperationType.ADDITION, response.getOperation());
        assertEquals(BigDecimal.TEN, response.getResult());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void testCalculateSubtractionOperation() {
        // Arrange
        this.mockAuthenticatedUser();

        Operation operation = DataProvider.mockOperation();
        OperationRequestDTO request = new OperationRequestDTO(EOperationType.SUBTRACTION, BigDecimal.valueOf(9), BigDecimal.valueOf(3));

        when(this.operationRepository.save(any(Operation.class))).thenReturn(operation);
        when(this.modelMapper.map(operation, OperationResponseDTO.class)).thenReturn(DataProvider.mockSubtractionResponse());

        OperationResponseDTO response = this.operationService.calculate(request);

        // Asserts
        assertEquals(EOperationType.SUBTRACTION, response.getOperation());
        assertEquals(BigDecimal.valueOf(6), response.getResult());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void testCalculateMultiplyOperation() {
        // Arrange
        this.mockAuthenticatedUser();

        Operation operation = DataProvider.mockOperation();
        OperationRequestDTO request = new OperationRequestDTO(EOperationType.MULTIPLICATION, BigDecimal.valueOf(4), BigDecimal.valueOf(3));

        when(this.operationRepository.save(any(Operation.class))).thenReturn(operation);
        when(this.modelMapper.map(operation, OperationResponseDTO.class)).thenReturn(DataProvider.mockMultiplicationResponse());

        OperationResponseDTO response = this.operationService.calculate(request);

        // Asserts
        assertEquals(EOperationType.MULTIPLICATION, response.getOperation());
        assertEquals(BigDecimal.valueOf(12), response.getResult());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void testCalculateDivisionOperation() {
        // Arrange
        this.mockAuthenticatedUser();

        Operation operation = DataProvider.mockOperation();
        OperationRequestDTO request = new OperationRequestDTO(EOperationType.DIVISION, BigDecimal.valueOf(10), BigDecimal.valueOf(4));

        when(this.operationRepository.save(any(Operation.class))).thenReturn(operation);
        when(this.modelMapper.map(operation, OperationResponseDTO.class)).thenReturn(DataProvider.mockDivisionResponse());

        OperationResponseDTO response = this.operationService.calculate(request);

        // Asserts
        assertEquals(EOperationType.DIVISION, response.getOperation());
        assertEquals(BigDecimal.valueOf(2.50), response.getResult());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void testCalculatePowerOperation() {
        // Arrange
        this.mockAuthenticatedUser();

        Operation operation = DataProvider.mockOperation();
        OperationRequestDTO request = new OperationRequestDTO(EOperationType.POWER, BigDecimal.valueOf(2), BigDecimal.valueOf(3));

        when(this.operationRepository.save(any(Operation.class))).thenReturn(operation);
        when(this.modelMapper.map(operation, OperationResponseDTO.class)).thenReturn(DataProvider.mockPowerResponse());

        OperationResponseDTO response = this.operationService.calculate(request);

        // Asserts
        assertEquals(EOperationType.POWER, response.getOperation());
        assertEquals(BigDecimal.valueOf(8), response.getResult());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void testCalculateSquareRootOperation() {
        // Arrange
        this.mockAuthenticatedUser();

        Operation operation = DataProvider.mockOperation();

        OperationRequestDTO request = new OperationRequestDTO(EOperationType.SQUARE_ROOT, BigDecimal.valueOf(9), null);

        when(this.operationRepository.save(any(Operation.class))).thenReturn(operation);
        when(this.modelMapper.map(operation, OperationResponseDTO.class)).thenReturn(DataProvider.mockSquareRootResponse());

        // Act
        OperationResponseDTO response = this.operationService.calculate(request);

        // Assert
        assertEquals(EOperationType.SQUARE_ROOT, response.getOperation());
        assertEquals(BigDecimal.valueOf(3.0), response.getResult());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void testGetOperationByIdSuccess() {
        // Arrange
        Operation operation = DataProvider.mockOperation();
        UUID id = UUID.randomUUID();

        when(this.operationRepository.findById(id)).thenReturn(Optional.of(operation));
        when(this.modelMapper.map(operation, OperationResponseDTO.class)).thenReturn(DataProvider.mockAdditionResponse());

        // Act
        OperationResponseDTO response = this.operationService.getOperationById(id);

        // Assert
        assertEquals(EOperationType.ADDITION, response.getOperation());
        assertEquals(BigDecimal.TEN, response.getResult());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void testGetOperationByIdException() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(this.operationRepository.findById(id)).thenReturn(Optional.empty());

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.operationService.getOperationById(id));
        assertEquals("Operation with ID { " + id + " } not found", exception.getMessage());
    }

    @Test
    void testDelOperationByIdSuccess() {
        // Arrange
        Operation operation = DataProvider.mockOperation();
        UUID id = UUID.randomUUID();

        when(this.operationRepository.findById(id)).thenReturn(Optional.of(operation));

        // Act
        String result = this.operationService.delOperationById(id);

        // Assert
        verify(this.operationRepository).delete(operation);
        assertEquals("Operation with ID { " + id + " } deleted", result);
    }

    @Test
    void testDelOperationByIdException() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(this.operationRepository.findById(id)).thenReturn(Optional.empty());

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.operationService.delOperationById(id));
        assertEquals("Operation with ID { " + id + " } not found", exception.getMessage());
    }

    @Test
    void testCalculateException() {
        // Arrange
        this.mockAuthenticatedUser();

        OperationRequestDTO request = new OperationRequestDTO(EOperationType.ADDITION, BigDecimal.valueOf(5), BigDecimal.valueOf(5));

        when(this.userRepository.findByUsername("Lawliet")).thenReturn(Optional.empty());

        // Asserts
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> this.operationService.calculate(request));
        assertEquals("Authenticated user not found", exception.getMessage());
    }

    private void mockAuthenticatedUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("Lawliet");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(this.userRepository.findByUsername("Lawliet")).thenReturn(Optional.of(DataProvider.mockUser()));
    }
}
