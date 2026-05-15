package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
import com.patricia.notification.entrypoints.messaging.dto.ConnectionRequestEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ consumer for social interaction notification events.
 *
 * <p>Handles connection request events published by the social service.
 * Malformed messages are rejected to the dead-letter queue.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SocialNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final EventDtoValidator eventDtoValidator;

    /**
     * Sends a connection request notification to the target user.
     *
     * @param event the connection request event from the social service
     */
    @RabbitListener(queues = "${rabbitmq.queue.connection-request}")
    public void handleConnectionRequest(ConnectionRequestEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: solicitud de conexión para {}", event.getTargetUserId());
        sendNotificationUseCase.execute(
                event.getTargetUserId(),
                NotificationType.CONNECTION_REQUEST,
                "Nueva solicitud de conexión",
                event.getRequesterUserName() + " quiere conectarse contigo",
                event.getRequesterId()
        );
    }
}
