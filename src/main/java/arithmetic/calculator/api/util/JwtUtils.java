package arithmetic.calculator.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.UUID;

@Component
public class JwtUtils {
    @Value("${security.jwt.secret.key}")
    private String secretKey;

    @Value("${security.jwt.user.generator}")
    private String userGenetator;

    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        String username = authentication.getPrincipal().toString();

        return JWT.create()
                  .withIssuer(this.userGenetator)
                  .withSubject(username)
                  .withIssuedAt(Instant.now())
                  .withExpiresAt(Instant.now().plusMillis(1800000))
                  .withJWTId(UUID.randomUUID().toString())
                  .withNotBefore(Instant.now())
                  .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

            JWTVerifier verifier = JWT.require(algorithm)
                                      .withIssuer(this.userGenetator)
                                      .build();

            return verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException("Token invalid, not authorized.");
        }
    }

    public String getUsernameFromToken(DecodedJWT tokenDecoded) {
        return tokenDecoded.getSubject();
    }
}
