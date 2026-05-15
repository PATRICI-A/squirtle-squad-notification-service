package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
import com.patricia.notification.entrypoints.messaging.dto.ConnectionRequestEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SocialNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final EventDtoValidator eventDtoValidator;

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