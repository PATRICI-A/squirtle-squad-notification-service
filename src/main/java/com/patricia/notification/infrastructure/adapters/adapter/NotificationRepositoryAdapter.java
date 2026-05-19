package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter that implements {@link NotificationRepository} using MongoDB.
 *
 * <p>Translates between domain {@link Notification} objects and
 * {@link com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationDocument}
 * MongoDB documents via {@link NotificationPersistenceMapper}.</p>
 */
@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepository {

    private final MongoNotificationRepository mongoRepository;
    private final NotificationPersistenceMapper mapper;

    @Override
    public Notification save(Notification notification) {
        return mapper.toDomain(mongoRepository.save(mapper.toDocument(notification)));
    }

    @Override
    public Optional<Notification> findById(UUID id) {
        return mongoRepository.findById(id.toString()).map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Delegates pagination to Spring Data's {@link PageRequest}; results are
     * pre-sorted by {@code createdAt} descending at the query level.</p>
     */
    @Override
    public List<Notification> findByUserIdPaged(UUID userId, int page, int size) {
        return mongoRepository.findByUserIdAndArchivedFalseOrderByCreatedAtDesc(
                        userId.toString(), PageRequest.of(page, size))
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public int countUnreadByUserId(UUID userId) {
        return mongoRepository.countByUserIdAndReadFalseAndArchivedFalse(userId.toString());
    }

    @Override
    public void markAllAsReadByUserId(UUID userId) {
        mongoRepository.markAllAsReadByUserId(userId.toString());
    }

    @Override
    public void archiveOlderThan(LocalDateTime cutoff) {
        mongoRepository.archiveOlderThan(cutoff, LocalDateTime.now());
    }
}
