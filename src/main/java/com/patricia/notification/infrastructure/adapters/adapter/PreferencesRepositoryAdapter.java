package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.ports.out.PreferencesRepository;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationPreferencesPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoPreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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