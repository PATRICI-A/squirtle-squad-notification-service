package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.ports.out.PreferencesRepository;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationPreferencesPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoPreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter that implements {@link PreferencesRepository} using MongoDB.
 *
 * <p>Translates between domain {@link NotificationPreferences} objects and
 * {@link com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationPreferencesDocument}
 * MongoDB documents via {@link NotificationPreferencesPersistenceMapper}.</p>
 */
@Component
@RequiredArgsConstructor
public class PreferencesRepositoryAdapter implements PreferencesRepository {

    private final MongoPreferencesRepository mongoRepository;
    private final NotificationPreferencesPersistenceMapper mapper;

    @Override
    public NotificationPreferences save(NotificationPreferences preferences) {
        return mapper.toDomain(mongoRepository.save(mapper.toDocument(preferences)));
    }

    @Override
    public Optional<NotificationPreferences> findByUserId(UUID userId) {
        return mongoRepository.findByUserId(userId.toString()).map(mapper::toDomain);
    }
}
