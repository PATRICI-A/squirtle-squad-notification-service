package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
import com.patricia.notification.entrypoints.messaging.dto.ChatMessageEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ consumer for direct chat notification events.
 *
 * <p>Handles direct message events published by the chat service when one student
 * sends a private message to another. Malformed messages are rejected to the
 * dead-letter queue.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChatNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final EventDtoValidator eventDtoValidator;

    /**
     * Notifies a user that they received a new direct message.
     *
     * @param event the chat message event from the chat service
     */
    @RabbitListener(queues = "${rabbitmq.queue.chat-message}")
    public void handleChatMessage(ChatMessageEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: mensaje directo para {}", event.getRecipientUserId());
        sendNotificationUseCase.execute(
                event.getRecipientUserId(),
                NotificationType.CHAT_MESSAGE,
                "Nuevo mensaje",
                event.getSenderName() + " te envió un mensaje",
                event.getConversationId()
        );
    }
}