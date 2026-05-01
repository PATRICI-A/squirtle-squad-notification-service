package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.FcmToken;
import com.patricia.notification.domain.ports.out.FcmTokenRepository;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.FcmTokenPersistenceMapper;
import com.patricia.notification.infrastructure.adapters.persistence.repository.MongoFcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FcmTokenRepositoryAdapter implements FcmTokenRepository {

    private final MongoFcmTokenRepository mongoRepository;
    private final FcmTokenPersistenceMapper mapper;

    @Override
    public FcmToken save(FcmToken fcmToken) {
        return mapper.toDomain(mongoRepository.save(mapper.toDocument(fcmToken)));
    }

    @Override
    public List<FcmToken> findAllByUserId(String userId) {
        return mongoRepository.findAllByUserId(userId)
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<FcmToken> findByUserIdAndDevice(String userId, String device) {
        return mongoRepository.findByUserIdAndDevice(userId, device).map(mapper::toDomain);
    }

    @Override
    public void deleteByUserIdAndDevice(String userId, String device) {
        mongoRepository.deleteByUserIdAndDevice(userId, device);
    }
}