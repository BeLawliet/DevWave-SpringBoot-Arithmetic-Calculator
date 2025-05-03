package arithmetic.calculator.api.config;

import arithmetic.calculator.api.presentation.dto.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgument(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
                                 .getFieldErrors()
                                 .stream()
                                 .map(FieldError::getDefaultMessage)
                                 .toList();

        ErrorResponseDTO error = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), "Invalid operation parameters", details);

        logger.warn("MethodArgumentNotValidException: {}", details);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("IllegalArgumentException: {}", ex.getMessage());

        ErrorResponseDTO error = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), "Invalid operation parameters", List.of(ex.getMessage()));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        logger.error("Unexpected error", ex);

        ErrorResponseDTO error = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", List.of(ex.getMessage()));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
