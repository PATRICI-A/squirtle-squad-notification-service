package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.domain.ports.out.EventReminderRepository;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.EventReminderPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoEventReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventReminderRepositoryAdapter implements EventReminderRepository {

    private final MongoEventReminderRepository mongoRepository;
    private final EventReminderPersistenceMapper mapper;

    @Override
    public EventReminder save(EventReminder reminder) {
        return mapper.toDomain(mongoRepository.save(mapper.toDocument(reminder)));
    }

    @Override
    public Optional<EventReminder> findByUserIdAndEventId(UUID userId, UUID eventId) {
        return mongoRepository.findByUserIdAndEventId(userId.toString(), eventId.toString())
                .map(mapper::toDomain);
    }

    @Override
    public List<EventReminder> findPendingReminders() {
        return mongoRepository.findPendingReminders()
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}