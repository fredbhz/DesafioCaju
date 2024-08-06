package com.example.desafioCaju.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Transações",
                version = "v1",
                description = "API para autorização de transações de cartão de crédito"
        )
)
public class OpenApiConfig { }
