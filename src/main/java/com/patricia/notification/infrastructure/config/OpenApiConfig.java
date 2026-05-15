package com.patricia.notification.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI / Swagger UI configuration for the notification service.
 *
 * <p>Registers global API metadata and injects the {@code X-User-Id} header
 * parameter into every operation so callers are reminded to supply it.</p>
 */
@Configuration
public class OpenApiConfig {

    /**
     * Builds the OpenAPI metadata (title, description, version, contact, servers).
     *
     * @return the configured {@link OpenAPI} instance
     */
    @Bean
    public OpenAPI notificationServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PATRICI.A — Notification Service")
                        .description("""
                                Microservice M04 of the PATRICI.A project (Squirtle Squad).
                                Manages real-time notifications via WebSocket and persists them in MongoDB.

                                **Authentication:** The API Gateway propagates the `X-User-Id` header with the authenticated user's ID.
                                All endpoints require this header.

                                **Delivery channel:** IN_APP only. Notifications are pushed via WebSocket
                                when the user is connected; otherwise they are persisted in MongoDB for later retrieval.

                                **WebSocket:** `ws://localhost:8082/ws/notifications` (STOMP over SockJS)
                                — user-specific topic: `/topic/notifications/{userId}`
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Squirtle Squad — Escuela Colombiana de Ingeniería Julio Garavito")
                                .email("carreaporfa@gmail.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("Local")));
    }

    /**
     * Adds the {@code X-User-Id} header as a required parameter to every API operation
     * so it appears in the Swagger UI try-it-out form.
     *
     * @return the customizer applied to all registered operations
     */
    @Bean
    public OperationCustomizer userIdHeaderCustomizer() {
        return (operation, handlerMethod) -> {
            operation.addParametersItem(new Parameter()
                    .in("header")
                    .name("X-User-Id")
                    .description("Authenticated user ID propagated by the API Gateway")
                    .required(true)
                    .example("user-123"));
            return operation;
        };
    }
}
