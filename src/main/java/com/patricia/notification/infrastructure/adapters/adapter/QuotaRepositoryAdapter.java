package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.NotificationQuota;
import com.patricia.notification.domain.ports.out.QuotaRepository;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationQuotaPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoQuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuotaRepositoryAdapter implements QuotaRepository {

    private final MongoQuotaRepository mongoRepository;
    private final NotificationQuotaPersistenceMapper mapper;

    @Override
    public NotificationQuota save(NotificationQuota quota) {
        return mapper.toDomain(mongoRepository.save(mapper.toDocument(quota)));
    }

    @Override
    public Optional<NotificationQuota> findByUserIdAndDate(
            String userId, LocalDate date) {
        return mongoRepository.findByUserIdAndDate(userId, date).map(mapper::toDomain);
    }
}