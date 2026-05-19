package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
import com.patricia.notification.entrypoints.messaging.dto.NearbyParcheEventDto;
import com.patricia.notification.entrypoints.messaging.dto.ParcheInvitationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ consumer for parche-related notification events.
 *
 * <p>Handles parche invitation and nearby parche events published by the parche service.
 * Malformed messages are rejected to the dead-letter queue.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ParcheNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final EventDtoValidator eventDtoValidator;

    /**
     * Sends a parche invitation notification to the target user.
     *
     * @param event the parche invitation event from the parche service
     */
    @RabbitListener(queues = "${rabbitmq.queue.parche-invitation}")
    public void handleParcheInvitation(ParcheInvitationEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: invitación a parche para {}", event.getTargetUserId());
        sendNotificationUseCase.execute(
                event.getTargetUserId(),
                NotificationType.INVITATION_SENT,
                "Te invitaron a un parche",
                event.getInviterUserName() + " te invitó a: " + event.getParcheName(),
                event.getParcheId()
        );
    }

    /**
     * Sends a nearby parche notification to the target user.
     *
     * @param event the nearby parche event from the parche service
     */
    @RabbitListener(queues = "${rabbitmq.queue.nearby-parche}")
    public void handleNearbyParche(NearbyParcheEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: parche cercano para {}", event.getTargetUserId());
        sendNotificationUseCase.execute(
                event.getTargetUserId(),
                NotificationType.NEARBY_PARCHE,
                "Parche cercano",
                "Hay un parche cerca: " + event.getParcheName()
                        + " a " + event.getDistanceKm() + " km",
                event.getParcheId()
        );
    }
}
