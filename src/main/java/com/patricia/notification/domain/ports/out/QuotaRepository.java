package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.NotificationQuota;
import java.time.LocalDate;
import java.util.Optional;

public interface QuotaRepository {
    NotificationQuota save(NotificationQuota quota);
    Optional<NotificationQuota> findByUserIdAndDate(String userId, LocalDate date);
}