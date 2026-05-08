package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.entrypoints.messaging.dto.NearbyParcheEventDto;
import com.patricia.notification.entrypoints.messaging.dto.ParcheInvitationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParcheNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;

    @RabbitListener(queues = "${rabbitmq.queue.parche-invitation}")
    public void handleParcheInvitation(ParcheInvitationEventDto event) {
        log.info("Evento recibido: invitación a parche para {}", event.getTargetUserId());
        sendNotificationUseCase.execute(
                event.getTargetUserId(),
                NotificationType.PARCHE_INVITATION,
                "Te invitaron a un parche",
                event.getInviterUserName() + " te invitó a: " + event.getParcheName(),
                event.getParcheId()
        );
    }

    @RabbitListener(queues = "${rabbitmq.queue.nearby-parche}")
    public void handleNearbyParche(NearbyParcheEventDto event) {
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