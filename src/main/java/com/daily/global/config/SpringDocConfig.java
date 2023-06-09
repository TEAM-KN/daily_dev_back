package com.daily.global.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SpringDocConfig {

    private static final String APP_ID = "Daily";
    private static final String DEFAULT_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzY25vaDA2MTdAZ21haWwuY29tIiwiaWF0IjoxNjg2MzI0NzExLCJleHAiOjE2ODYzMjgzMTF9.ck_3R2WLSUdgdy58wTqewbmITMbIlwBDQEYsDORArYA";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v1.0.0")
                .title("Daily Dev API")
                .description("REST API TEST");

        String schemeName = "auth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(schemeName);
        Components components = new Components()
                .addSecuritySchemes(schemeName, new SecurityScheme().name(schemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);

    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            Parameter param = new Parameter()
                    .in(ParameterIn.HEADER.toString())
                    .schema(new StringSchema()._default(DEFAULT_TOKEN).name(APP_ID))
                    .name(APP_ID)
                    .description("TEST")
                    .required(true);
            operation.addParametersItem(param);
            return operation;
        };
    }
}
