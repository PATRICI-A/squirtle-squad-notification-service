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

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI notificationServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PATRICI.A — Notification Service")
                        .description("""
                                Microservicio M04 del proyecto PATRICI.A (Squirtle Squad).
                                Gestiona notificaciones en tiempo real via WebSocket y persistencia en MongoDB.

                                **Autenticación:** El API Gateway propaga el header `X-User-Id` con el ID del usuario autenticado.
                                Todos los endpoints requieren este header.

                                **Canal de entrega:** IN_APP únicamente. Las notificaciones se entregan via WebSocket
                                si el usuario está conectado; si no, quedan persistidas en MongoDB.

                                **WebSocket:** `ws://localhost:8082/ws/notifications` (STOMP sobre SockJS)
                                — canal por usuario: `/topic/notifications/{userId}`
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Squirtle Squad — Escuela Colombiana de Ingeniería Julio Garavito")
                                .email("carreaporfa@gmail.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("Local")));
    }

    @Bean
    public OperationCustomizer userIdHeaderCustomizer() {
        return (operation, handlerMethod) -> {
            operation.addParametersItem(new Parameter()
                    .in("header")
                    .name("X-User-Id")
                    .description("ID del usuario autenticado, propagado por el API Gateway")
                    .required(true)
                    .example("user-123"));
            return operation;
        };
    }
}
