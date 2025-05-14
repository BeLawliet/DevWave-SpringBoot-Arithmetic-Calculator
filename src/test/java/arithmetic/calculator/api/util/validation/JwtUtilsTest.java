package arithmetic.calculator.api.util.validation;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import arithmetic.calculator.api.util.JwtUtils;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JwtUtilsTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void testCreateAndValidationTokenSuccess() {
        // Arrange
        Authentication auth = new UsernamePasswordAuthenticationToken("Lawliet", null);

        // Act
        String accessToken = jwtUtils.createToken(auth);
        DecodedJWT decodedJWT = jwtUtils.validateToken(accessToken);

        // Assert
        assertNotNull(accessToken);
        assertEquals("Lawliet", decodedJWT.getSubject());
        assertEquals("TestUser", decodedJWT.getIssuer());
    }

    @Test
    void testInvalidTokenException() {
        JWTVerificationException exception = assertThrows(JWTVerificationException.class, () -> jwtUtils.validateToken("invalid.token"));
        assertEquals("Token invalid, not authorized.", exception.getMessage());
    }

    @Test
    void testGetUsernameFromToken() {
        // Arrange
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(decodedJWT.getSubject()).thenReturn("Lawliet");

        // Act
        String username = jwtUtils.getUsernameFromToken(decodedJWT);

        // Assert
        assertEquals("Lawliet", username);
    }
}
