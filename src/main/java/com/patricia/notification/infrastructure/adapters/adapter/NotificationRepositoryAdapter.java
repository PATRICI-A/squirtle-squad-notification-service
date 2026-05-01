package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<Notification> findById(String id) {
        return mongoRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Notification> findByUserIdPaged(String userId, int page, int size) {
        return mongoRepository.findByUserIdOrderByCreatedAtDesc(
                        userId, PageRequest.of(page, size))
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public int countUnreadByUserId(String userId) {
        return mongoRepository.countByUserIdAndReadFalse(userId);
    }

    @Override
    public void markAllAsReadByUserId(String userId) {
        mongoRepository.markAllAsReadByUserId(userId);
    }
}