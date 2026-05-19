package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
import com.patricia.notification.entrypoints.messaging.dto.AchievementUnlockedEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ consumer for gamification notification events.
 *
 * <p>Handles achievement unlocked events published by the gamification service.
 * Malformed messages are rejected to the dead-letter queue.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AchievementNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final EventDtoValidator eventDtoValidator;

    /**
     * Notifies a user that they have unlocked a new achievement.
     *
     * @param event the achievement unlocked event from the gamification service
     */
    @RabbitListener(queues = "${rabbitmq.queue.achievement-unlocked}")
    public void handleAchievementUnlocked(AchievementUnlockedEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: logro desbloqueado para {}", event.getUserId());
        sendNotificationUseCase.execute(
                event.getUserId(),
                NotificationType.ACHIEVEMENT_UNLOCKED,
                "¡Nuevo logro desbloqueado!",
                "Desbloqueaste: " + event.getAchievementName(),
                event.getAchievementId()
        );
    }
}
