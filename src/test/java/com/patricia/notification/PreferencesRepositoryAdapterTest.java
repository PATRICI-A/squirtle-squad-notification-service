package com.patricia.notification;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.infrastructure.adapters.adapter.PreferencesRepositoryAdapter;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationPreferencesDocument;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationPreferencesPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoPreferencesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PreferencesRepositoryAdapterTest {

    @Mock
    private MongoPreferencesRepository mongoRepository;

    @Mock
    private NotificationPreferencesPersistenceMapper mapper;

    @InjectMocks
    private PreferencesRepositoryAdapter adapter;

    @Test
    @DisplayName("save debe convertir a documento, persistir y retornar dominio")
    void save_shouldConvertAndPersistAndReturn() {
        NotificationPreferences preferences = NotificationPreferences.builder()
                .userId("user-123")
                .connectionRequest(true)
                .parcheMessage(true)
                .build();

        NotificationPreferencesDocument doc = NotificationPreferencesDocument.builder()
                .userId("user-123")
                .connectionRequest(true)
                .parcheMessage(true)
                .build();

        when(mapper.toDocument(preferences)).thenReturn(doc);
        when(mongoRepository.save(doc)).thenReturn(doc);
        when(mapper.toDomain(doc)).thenReturn(preferences);

        NotificationPreferences result = adapter.save(preferences);

        assertThat(result).isEqualTo(preferences);
        verify(mongoRepository).save(doc);
    }

    @Test
    @DisplayName("findByUserId debe retornar preferencias cuando existen")
    void findByUserId_shouldReturnPreferences_whenFound() {
        NotificationPreferencesDocument doc = NotificationPreferencesDocument.builder()
                .id("pref-001")
                .userId("user-123")
                .connectionRequest(true)
                .build();

        NotificationPreferences preferences = NotificationPreferences.builder()
                .id("pref-001")
                .userId("user-123")
                .connectionRequest(true)
                .build();

        when(mongoRepository.findByUserId("user-123")).thenReturn(Optional.of(doc));
        when(mapper.toDomain(doc)).thenReturn(preferences);

        Optional<NotificationPreferences> result = adapter.findByUserId("user-123");

        assertThat(result).isPresent();
        assertThat(result.get().getUserId()).isEqualTo("user-123");
    }

    @Test
    @DisplayName("findByUserId debe retornar vacío cuando no existen preferencias")
    void findByUserId_shouldReturnEmpty_whenNotFound() {
        when(mongoRepository.findByUserId("user-nuevo")).thenReturn(Optional.empty());

        Optional<NotificationPreferences> result = adapter.findByUserId("user-nuevo");

        assertThat(result).isEmpty();
    }
}
