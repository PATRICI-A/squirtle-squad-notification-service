package com.patricia.notification;

import com.patricia.notification.application.dto.request.SendNotificationRequest;
import com.patricia.notification.application.dto.request.UpdatePreferencesRequest;
import com.patricia.notification.application.dto.response.NotificationPreferencesResponse;
import com.patricia.notification.application.dto.response.NotificationResponse;
import com.patricia.notification.application.dto.response.UnreadNotificationCountResponse;
import com.patricia.notification.application.mapper.NotificationMapper;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.*;
import com.patricia.notification.entrypoints.rest.controller.NotificationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock private SendNotificationUseCase sendNotificationUseCase;
    @Mock private GetNotificationsUseCase getNotificationsUseCase;
    @Mock private GetUnreadCountUseCase getUnreadCountUseCase;
    @Mock private MarkAsReadUseCase markAsReadUseCase;
    @Mock private GetPreferencesUseCase getPreferencesUseCase;
    @Mock private UpdatePreferencesUseCase updatePreferencesUseCase;
    @Mock private NotificationMapper mapper;

    @InjectMocks
    private NotificationController controller;

    @Test
    @DisplayName("sendNotification debe retornar 201 con la notificación creada")
    void sendNotification_shouldReturn201WithNotificationResponse() {
        SendNotificationRequest request = mock(SendNotificationRequest.class);
        when(request.getUserId()).thenReturn("user-123");
        when(request.getType()).thenReturn(NotificationType.PARCHE_MESSAGE);
        when(request.getTitle()).thenReturn("Título");
        when(request.getBody()).thenReturn("Cuerpo");
        when(request.getReferenceId()).thenReturn("ref-456");

        Notification notification = Notification.builder()
                .id("notif-001").userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Título").body("Cuerpo").read(false)
                .createdAt(LocalDateTime.now()).build();

        NotificationResponse response = NotificationResponse.builder()
                .id("notif-001").userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Título").body("Cuerpo").read(false).build();

        when(sendNotificationUseCase.execute("user-123", NotificationType.PARCHE_MESSAGE,
                "Título", "Cuerpo", "ref-456")).thenReturn(notification);
        when(mapper.toResponse(notification)).thenReturn(response);

        ResponseEntity<NotificationResponse> result = controller.sendNotification(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    @DisplayName("getNotifications debe retornar 200 con lista de notificaciones")
    void getNotifications_shouldReturn200WithList() {
        Notification notification = Notification.builder()
                .id("n1").userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("T").body("B").read(false).build();

        NotificationResponse response = NotificationResponse.builder()
                .id("n1").userId("user-123").build();

        when(getNotificationsUseCase.execute("user-123", 0, 20))
                .thenReturn(List.of(notification));
        when(mapper.toResponse(notification)).thenReturn(response);

        ResponseEntity<List<NotificationResponse>> result =
                controller.getNotifications("user-123", 0, 20);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(1);
    }

    @Test
    @DisplayName("getUnreadCount debe retornar 200 con el conteo")
    void getUnreadCount_shouldReturn200WithCount() {
        UnreadNotificationCountResponse countResponse =
                UnreadNotificationCountResponse.builder().count(7).build();

        when(getUnreadCountUseCase.execute("user-123")).thenReturn(7);
        when(mapper.toUnreadCountResponse(7)).thenReturn(countResponse);

        ResponseEntity<UnreadNotificationCountResponse> result =
                controller.getUnreadCount("user-123");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getCount()).isEqualTo(7);
    }

    @Test
    @DisplayName("markSingleAsRead debe retornar 204")
    void markSingleAsRead_shouldReturn204() {
        ResponseEntity<Void> result =
                controller.markSingleAsRead("notif-001", "user-123");

        verify(markAsReadUseCase).executeSingle("notif-001", "user-123");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("markAllAsRead debe retornar 204")
    void markAllAsRead_shouldReturn204() {
        ResponseEntity<Void> result = controller.markAllAsRead("user-123");

        verify(markAsReadUseCase).executeAll("user-123");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("getPreferences debe retornar 200 con preferencias del usuario")
    void getPreferences_shouldReturn200WithPreferences() {
        NotificationPreferences preferences = NotificationPreferences.builder()
                .userId("user-123").connectionRequest(true).parcheMessage(true).build();

        NotificationPreferencesResponse prefsResponse = NotificationPreferencesResponse.builder()
                .connectionRequest(true).parcheMessage(true).build();

        when(getPreferencesUseCase.execute("user-123")).thenReturn(preferences);
        when(mapper.toPreferencesResponse(preferences)).thenReturn(prefsResponse);

        ResponseEntity<NotificationPreferencesResponse> result =
                controller.getPreferences("user-123");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().isConnectionRequest()).isTrue();
    }

    @Test
    @DisplayName("updatePreferences debe retornar 200 con preferencias actualizadas")
    void updatePreferences_shouldReturn200WithUpdatedPreferences() {
        UpdatePreferencesRequest request = mock(UpdatePreferencesRequest.class);
        when(request.getType()).thenReturn(NotificationType.PARCHE_MESSAGE);
        when(request.getEnabled()).thenReturn(false);

        NotificationPreferences updated = NotificationPreferences.builder()
                .userId("user-123").parcheMessage(false).build();

        NotificationPreferencesResponse prefsResponse = NotificationPreferencesResponse.builder()
                .parcheMessage(false).build();

        when(updatePreferencesUseCase.execute("user-123", NotificationType.PARCHE_MESSAGE, false))
                .thenReturn(updated);
        when(mapper.toPreferencesResponse(updated)).thenReturn(prefsResponse);

        ResponseEntity<NotificationPreferencesResponse> result =
                controller.updatePreferences("user-123", request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().isParcheMessage()).isFalse();
    }
}
