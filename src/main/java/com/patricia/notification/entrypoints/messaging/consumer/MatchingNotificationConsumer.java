package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.entrypoints.messaging.dto.FriendshipCreatedNotificationDto;
import com.patricia.notification.entrypoints.messaging.dto.MatchReceivedNotificationDto;
import com.patricia.notification.entrypoints.messaging.dto.MatchResponseNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;

    @RabbitListener(queues = "${rabbitmq.queue.match-received}")
    public void handleMatchReceived(MatchReceivedNotificationDto event) {
        log.info("Match recibido para usuario {}", event.getReceiverUserId());
        sendNotificationUseCase.execute(
                event.getReceiverUserId(),
                NotificationType.MATCH_RECEIVED,
                "Nuevo match recibido",
                "Tienes un nuevo match con " + event.getAffinityPercentage() + "% de afinidad",
                event.getSenderUserId()
        );
    }

    @RabbitListener(queues = "${rabbitmq.queue.match-response}")
    public void handleMatchResponse(MatchResponseNotificationDto event) {
        log.info("Respuesta de match para usuario {}", event.getSenderUserId());
        String body = event.getStatus() == com.patricia.notification.entrypoints.messaging.dto.MatchStatus.ACCEPTED
                ? "Tu match fue aceptado"
                : "Tu match fue rechazado";
        sendNotificationUseCase.execute(
                event.getSenderUserId(),
                NotificationType.MATCH_RESPONSE,
                "Respuesta a tu match",
                body,
                event.getReceiverUserId()
        );
    }

    @RabbitListener(queues = "${rabbitmq.queue.friendship-created}")
    public void handleFriendshipCreated(FriendshipCreatedNotificationDto event) {
        log.info("Amistad creada entre {} y {}", event.getUserId1(), event.getUserId2());
        sendNotificationUseCase.execute(
                event.getUserId2(),
                NotificationType.FRIENDSHIP_CREATED,
                "Nueva amistad creada",
                "¡Ahora son amigos!",
                event.getUserId1()
        );
    }
}