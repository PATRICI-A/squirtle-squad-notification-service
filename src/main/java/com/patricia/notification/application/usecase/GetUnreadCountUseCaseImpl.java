package com.patricia.notification.application.usecase;

import com.patricia.notification.domain.ports.in.GetUnreadCountUseCase;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUnreadCountUseCaseImpl implements GetUnreadCountUseCase {

    private final NotificationRepository notificationRepository;

    @Override
    public int execute(String userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }
}