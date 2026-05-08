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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationRepositoryAdapterTest {

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
                .id("notif-001").userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Test").body("Body").read(false)
                .createdAt(LocalDateTime.now()).build();

        NotificationDocument doc = NotificationDocument.builder()
                .id("notif-001").userId("user-123")
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
                .id("notif-001").userId("user-123")
                .type("PARCHE_MESSAGE").channel("IN_APP")
                .title("Test").body("Body").read(false).build();

        Notification notification = Notification.builder()
                .id("notif-001").userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .build();

        when(mongoRepository.findById("notif-001")).thenReturn(Optional.of(doc));
        when(mapper.toDomain(doc)).thenReturn(notification);

        Optional<Notification> result = adapter.findById("notif-001");

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo("notif-001");
    }

    @Test
    @DisplayName("findById debe retornar vacío cuando no existe")
    void findById_shouldReturnEmpty_whenNotFound() {
        when(mongoRepository.findById("notif-inexistente")).thenReturn(Optional.empty());

        Optional<Notification> result = adapter.findById("notif-inexistente");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByUserIdPaged debe retornar lista mapeada de notificaciones")
    void findByUserIdPaged_shouldReturnMappedList() {
        NotificationDocument doc = NotificationDocument.builder()
                .id("n1").userId("user-123")
                .type("PARCHE_MESSAGE").channel("IN_APP")
                .title("T").body("B").read(false).build();

        Notification notification = Notification.builder()
                .id("n1").userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .build();

        when(mongoRepository.findByUserIdOrderByCreatedAtDesc(eq("user-123"), any(Pageable.class)))
                .thenReturn(List.of(doc));
        when(mapper.toDomain(doc)).thenReturn(notification);

        List<Notification> result = adapter.findByUserIdPaged("user-123", 0, 20);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("n1");
    }

    @Test
    @DisplayName("countUnreadByUserId debe retornar el conteo del repositorio")
    void countUnreadByUserId_shouldReturnCountFromRepository() {
        when(mongoRepository.countByUserIdAndReadFalse("user-123")).thenReturn(5);

        int count = adapter.countUnreadByUserId("user-123");

        assertThat(count).isEqualTo(5);
    }

    @Test
    @DisplayName("markAllAsReadByUserId debe delegar al repositorio")
    void markAllAsReadByUserId_shouldDelegateToRepository() {
        adapter.markAllAsReadByUserId("user-123");

        verify(mongoRepository).markAllAsReadByUserId("user-123");
    }
}
