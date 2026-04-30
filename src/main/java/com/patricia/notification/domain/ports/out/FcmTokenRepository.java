package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.FcmToken;
import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository {
    FcmToken save(FcmToken fcmToken);
    List<FcmToken> findAllByUserId(String userId);
    Optional<FcmToken> findByUserIdAndDevice(String userId, String device);
    void deleteByUserIdAndDevice(String userId, String device);
}