package com.patricia.notification;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
import com.patricia.notification.entrypoints.messaging.consumer.ParcheNotificationConsumer;
import com.patricia.notification.entrypoints.messaging.dto.NearbyParcheEventDto;
import com.patricia.notification.entrypoints.messaging.dto.ParcheInvitationEventDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ParcheNotificationConsumerTest {

    private static final UUID USER_ID_1  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID PARCHE_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID PARCHE_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000011");

    @Mock
    private SendNotificationUseCase sendNotificationUseCase;

    @Mock
    private EventDtoValidator eventDtoValidator;

    @InjectMocks
    private ParcheNotificationConsumer consumer;

    @Test
    @DisplayName("handleParcheInvitation debe enviar notificación de invitación al parche")
    void handleParcheInvitation_shouldSendInvitationNotification() {
        ParcheInvitationEventDto event = ParcheInvitationEventDto.builder()
                .targetUserId(USER_ID_1)
                .inviterUserName("Juan")
                .parcheId(PARCHE_ID_1)
                .parcheName("Parche del viernes")
                .build();

        consumer.handleParcheInvitation(event);

        verify(sendNotificationUseCase).execute(
                eq(USER_ID_1),
                eq(NotificationType.PARCHE_INVITATION),
                eq("Te invitaron a un parche"),
                contains("Juan"),
                eq(PARCHE_ID_1)
        );
    }

    @Test
    @DisplayName("handleNearbyParche debe enviar notificación de parche cercano con distancia")
    void handleNearbyParche_shouldSendNearbyNotificationWithDistance() {
        NearbyParcheEventDto event = NearbyParcheEventDto.builder()
                .targetUserId(USER_ID_2)
                .parcheId(PARCHE_ID_2)
                .parcheName("Parche en la plaza")
                .distanceKm(0.5)
                .build();

        consumer.handleNearbyParche(event);

        verify(sendNotificationUseCase).execute(
                eq(USER_ID_2),
                eq(NotificationType.NEARBY_PARCHE),
                eq("Parche cercano"),
                contains("Parche en la plaza"),
                eq(PARCHE_ID_2)
        );
    }
}
