package com.patricia.notification;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.infrastructure.adapters.adapter.NotificationRepositoryAdapter;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationDocument;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoNotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationRepositoryAdapterTest {

    private static final UUID USER_ID     = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID NOTIF_ID_1  = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID NOTIF_ID_2  = UUID.fromString("00000000-0000-0000-0000-000000000011");
    private static final UUID NOTIF_MISS  = UUID.fromString("00000000-0000-0000-0000-000000000099");

    @Mock
    private MongoNotificationRepository mongoRepository;

    @Mock
    private NotificationPersistenceMapper mapper;

    @InjectMocks
    private NotificationRepositoryAdapter adapter;

    @Test
    @DisplayName("save debe convertir a documento, persistir y retornar dominio")
    void save_shouldConvertAndPersistAndReturn() {
        Notification notification = Notification.builder()
                .id(NOTIF_ID_1).userId(USER_ID)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Test").body("Body").read(false)
                .createdAt(LocalDateTime.now()).build();

        NotificationDocument doc = NotificationDocument.builder()
                .id(NOTIF_ID_1.toString()).userId(USER_ID.toString())
                .type("PARCHE_MESSAGE").channel("IN_APP")
                .title("Test").body("Body").read(false).build();

        when(mapper.toDocument(notification)).thenReturn(doc);
        when(mongoRepository.save(doc)).thenReturn(doc);
        when(mapper.toDomain(doc)).thenReturn(notification);

        Notification result = adapter.save(notification);

        assertThat(result).isEqualTo(notification);
        verify(mongoRepository).save(doc);
    }

    @Test
    @DisplayName("findById debe retornar notificación cuando existe")
    void findById_shouldReturnNotification_whenFound() {
        NotificationDocument doc = NotificationDocument.builder()
                .id(NOTIF_ID_1.toString()).userId(USER_ID.toString())
                .type("PARCHE_MESSAGE").channel("IN_APP")
                .title("Test").body("Body").read(false).build();

        Notification notification = Notification.builder()
                .id(NOTIF_ID_1).userId(USER_ID)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .build();

        when(mongoRepository.findById(NOTIF_ID_1.toString())).thenReturn(Optional.of(doc));
        when(mapper.toDomain(doc)).thenReturn(notification);

        Optional<Notification> result = adapter.findById(NOTIF_ID_1);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(NOTIF_ID_1);
    }

    @Test
    @DisplayName("findById debe retornar vacío cuando no existe")
    void findById_shouldReturnEmpty_whenNotFound() {
        when(mongoRepository.findById(NOTIF_MISS.toString())).thenReturn(Optional.empty());

        Optional<Notification> result = adapter.findById(NOTIF_MISS);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByUserIdPaged debe retornar lista mapeada de notificaciones")
    void findByUserIdPaged_shouldReturnMappedList() {
        NotificationDocument doc = NotificationDocument.builder()
                .id(NOTIF_ID_2.toString()).userId(USER_ID.toString())
                .type("PARCHE_MESSAGE").channel("IN_APP")
                .title("T").body("B").read(false).build();

        Notification notification = Notification.builder()
                .id(NOTIF_ID_2).userId(USER_ID)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .build();

        when(mongoRepository.findByUserIdOrderByCreatedAtDesc(eq(USER_ID.toString()), any(Pageable.class)))
                .thenReturn(List.of(doc));
        when(mapper.toDomain(doc)).thenReturn(notification);

        List<Notification> result = adapter.findByUserIdPaged(USER_ID, 0, 20);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(NOTIF_ID_2);
    }

    @Test
    @DisplayName("countUnreadByUserId debe retornar el conteo del repositorio")
    void countUnreadByUserId_shouldReturnCountFromRepository() {
        when(mongoRepository.countByUserIdAndReadFalse(USER_ID.toString())).thenReturn(5);

        int count = adapter.countUnreadByUserId(USER_ID);

        assertThat(count).isEqualTo(5);
    }

    @Test
    @DisplayName("markAllAsReadByUserId debe delegar al repositorio")
    void markAllAsReadByUserId_shouldDelegateToRepository() {
        adapter.markAllAsReadByUserId(USER_ID);

        verify(mongoRepository).markAllAsReadByUserId(USER_ID.toString());
    }
}
