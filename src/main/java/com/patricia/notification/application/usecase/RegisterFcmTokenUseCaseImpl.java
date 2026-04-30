package com.patricia.notification.application.usecase;

import com.patricia.notification.domain.model.FcmToken;
import com.patricia.notification.domain.ports.in.RegisterFcmTokenUseCase;
import com.patricia.notification.domain.ports.out.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RegisterFcmTokenUseCaseImpl implements RegisterFcmTokenUseCase {

    private final FcmTokenRepository fcmTokenRepository;

    @Override
    public FcmToken execute(String userId, String token, String device) {
        return fcmTokenRepository.findByUserIdAndDevice(userId, device)
                .map(existing -> {
                    existing.refresh(token);
                    return fcmTokenRepository.save(existing);
                })
                .orElseGet(() -> fcmTokenRepository.save(
                        FcmToken.builder()
                                .userId(userId)
                                .token(token)
                                .device(device)
                                .updatedAt(LocalDateTime.now())
                                .build()
                ));
    }
}