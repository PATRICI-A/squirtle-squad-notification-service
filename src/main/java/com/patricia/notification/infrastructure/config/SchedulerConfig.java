package com.patricia.notification.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Enables Spring's scheduled task execution for the notification service.
 *
 * <p>Required for {@link com.patricia.notification.application.service.EventReminderService}
 * to process event reminders on a fixed-delay schedule.</p>
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {
}
