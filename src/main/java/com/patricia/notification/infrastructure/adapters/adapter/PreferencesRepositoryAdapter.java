package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.ports.out.PreferencesRepository;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationPreferencesPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoPreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public Optional<NotificationPreferences> findByUserId(String userId) {
        return mongoRepository.findByUserId(userId).map(mapper::toDomain);
    }
}