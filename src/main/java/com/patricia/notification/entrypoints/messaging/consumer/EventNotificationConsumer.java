package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
import com.patricia.notification.entrypoints.messaging.dto.EventChangeEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ consumer for event-related notification events.
 *
 * <p>Handles event change and cancellation events published by the events service.
 * Malformed messages are rejected to the dead-letter queue.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final EventDtoValidator eventDtoValidator;

    /**
     * Notifies a user that an event they RSVPed to has been modified or cancelled.
     *
     * @param event the event change event from the events service
     */
    @RabbitListener(queues = "${rabbitmq.queue.event-change}")
    public void handleEventChange(EventChangeEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: cambio en evento {} para {}", event.getEventId(), event.getTargetUserId());
        sendNotificationUseCase.execute(
                event.getTargetUserId(),
                NotificationType.EVENT_CHANGE,
                "Cambio en tu evento: " + event.getEventName(),
                "El evento ha sido " + event.getChangeDescription(),
                event.getEventId()
        );
    }
}
