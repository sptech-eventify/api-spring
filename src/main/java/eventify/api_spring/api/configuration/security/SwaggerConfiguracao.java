package eventify.api_spring.api.configuration.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Eventify", version = "v1"))
@SecurityScheme(
        name = "requiredAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "Bearer"
    )
public class SwaggerConfiguracao {}

