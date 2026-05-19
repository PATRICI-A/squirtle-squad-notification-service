package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
import com.patricia.notification.entrypoints.messaging.dto.ChatMessageEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final EventDtoValidator eventDtoValidator;

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