package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.FcmToken;

public interface RegisterFcmTokenUseCase {
    FcmToken execute(String userId, String token, String device);
}