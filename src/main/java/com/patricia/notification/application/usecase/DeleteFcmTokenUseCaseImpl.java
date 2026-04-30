package com.patricia.notification.application.usecase;

import com.patricia.notification.domain.ports.in.DeleteFcmTokenUseCase;
import com.patricia.notification.domain.ports.out.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteFcmTokenUseCaseImpl implements DeleteFcmTokenUseCase {

    private final FcmTokenRepository fcmTokenRepository;

    @Override
    public void execute(String userId, String device) {
        fcmTokenRepository.deleteByUserIdAndDevice(userId, device);
    }
}