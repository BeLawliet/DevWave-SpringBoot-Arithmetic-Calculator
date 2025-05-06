package arithmetic.calculator.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
    info = @Info(
        title = "Arithmetic Calculator API",
        description = "This is an API REST to arithmetic operations",
        termsOfService = "https://github.com/BeLawliet",
        version = "v1.0.0",
        contact = @Contact(
            name = "Lawliet Dev",
            email = "elcostexd995@gmail.com"
        )
    ),
    servers = {
        @Server(
            description = "Local Server",
            url = "http://localhost:8080"
        )
    },
    security = @SecurityRequirement(
        name = "Security Token"
    )
)
@SecurityScheme(
    name = "Security Token",
    description = "Access Token for my API",
    type = SecuritySchemeType.HTTP,
    paramName = HttpHeaders.AUTHORIZATION,
    in = SecuritySchemeIn.HEADER,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {}
