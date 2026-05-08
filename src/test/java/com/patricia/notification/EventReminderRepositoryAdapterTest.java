package com.patricia.notification;

import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.infrastructure.adapters.adapter.EventReminderRepositoryAdapter;
import com.patricia.notification.infrastructure.adapters.persistence.entity.EventReminderDocument;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.EventReminderPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoEventReminderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventReminderRepositoryAdapterTest {

    @Mock
    private MongoEventReminderRepository mongoRepository;

    @Mock
    private EventReminderPersistenceMapper mapper;

    @InjectMocks
    private EventReminderRepositoryAdapter adapter;

    @Test
    @DisplayName("save debe convertir a documento, persistir y retornar dominio")
    void save_shouldConvertAndPersistAndReturn() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(1);
        EventReminder reminder = EventReminder.builder()
                .id("r1").userId("user-123").eventId("event-456")
                .eventDate(eventDate).reminded24h(false).reminded1h(false).build();

        EventReminderDocument doc = EventReminderDocument.builder()
                .id("r1").userId("user-123").eventId("event-456")
                .eventDate(eventDate).reminded24h(false).reminded1h(false).build();

        when(mapper.toDocument(reminder)).thenReturn(doc);
        when(mongoRepository.save(doc)).thenReturn(doc);
        when(mapper.toDomain(doc)).thenReturn(reminder);

        EventReminder result = adapter.save(reminder);

        assertThat(result).isEqualTo(reminder);
        verify(mongoRepository).save(doc);
    }

    @Test
    @DisplayName("findByUserIdAndEventId debe retornar recordatorio cuando existe")
    void findByUserIdAndEventId_shouldReturnReminder_whenFound() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(1);
        EventReminderDocument doc = EventReminderDocument.builder()
                .id("r1").userId("user-123").eventId("event-456")
                .eventDate(eventDate).reminded24h(false).reminded1h(false).build();

        EventReminder reminder = EventReminder.builder()
                .id("r1").userId("user-123").eventId("event-456")
                .eventDate(eventDate).reminded24h(false).reminded1h(false).build();

        when(mongoRepository.findByUserIdAndEventId("user-123", "event-456"))
                .thenReturn(Optional.of(doc));
        when(mapper.toDomain(doc)).thenReturn(reminder);

        Optional<EventReminder> result = adapter.findByUserIdAndEventId("user-123", "event-456");

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo("r1");
    }

    @Test
    @DisplayName("findByUserIdAndEventId debe retornar vacío cuando no existe")
    void findByUserIdAndEventId_shouldReturnEmpty_whenNotFound() {
        when(mongoRepository.findByUserIdAndEventId("user-123", "event-inexistente"))
                .thenReturn(Optional.empty());

        Optional<EventReminder> result = adapter.findByUserIdAndEventId("user-123", "event-inexistente");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findPendingReminders debe retornar lista de recordatorios mapeados")
    void findPendingReminders_shouldReturnMappedList() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(1);
        EventReminderDocument doc = EventReminderDocument.builder()
                .id("r2").userId("user-456").eventId("event-789")
                .eventDate(eventDate).reminded24h(false).reminded1h(false).build();

        EventReminder reminder = EventReminder.builder()
                .id("r2").userId("user-456").eventId("event-789")
                .eventDate(eventDate).reminded24h(false).reminded1h(false).build();

        when(mongoRepository.findPendingReminders()).thenReturn(List.of(doc));
        when(mapper.toDomain(doc)).thenReturn(reminder);

        List<EventReminder> result = adapter.findPendingReminders();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("r2");
    }

    @Test
    @DisplayName("findPendingReminders debe retornar lista vacía cuando no hay pendientes")
    void findPendingReminders_shouldReturnEmptyList_whenNoPending() {
        when(mongoRepository.findPendingReminders()).thenReturn(List.of());

        List<EventReminder> result = adapter.findPendingReminders();

        assertThat(result).isEmpty();
    }
}
