package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
import com.patricia.notification.entrypoints.messaging.dto.InvitationAcceptedEventDto;
import com.patricia.notification.entrypoints.messaging.dto.InvitationSentEventDto;
import com.patricia.notification.entrypoints.messaging.dto.MemberJoinedEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HangoutNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final EventDtoValidator eventDtoValidator;

    @RabbitListener(queues = "${rabbitmq.queue.invitation-accepted}")
    public void handleInvitationAccepted(InvitationAcceptedEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: invitación aceptada, capitán {}", event.getCaptainId());
        sendNotificationUseCase.execute(
                event.getCaptainId(),
                NotificationType.INVITATION_ACCEPTED,
                "Invitación aceptada",
                "Un estudiante aceptó tu invitación al parche.",
                event.getParcheId()
        );
    }

    @RabbitListener(queues = "${rabbitmq.queue.invitation-sent}")
    public void handleInvitationSent(InvitationSentEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: invitación enviada a {}", event.getInvitedStudentId());
        sendNotificationUseCase.execute(
                event.getInvitedStudentId(),
                NotificationType.INVITATION_SENT,
                "Te invitaron a un parche privado",
                "Recibiste una invitación a un parche privado.",
                event.getParcheId()
        );
    }

    @RabbitListener(queues = "${rabbitmq.queue.member-joined}")
    public void handleMemberJoined(MemberJoinedEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: nuevo miembro en parche {}", event.getNombreParche());
        sendNotificationUseCase.execute(
                event.getCapitanId(),
                NotificationType.MEMBER_JOINED,
                "Nuevo miembro en tu parche",
                "Un estudiante se unió a tu parche: " + event.getNombreParche(),
                null
        );
    }
}