package com.patricia.notification.domain.validation;

/**
 * Central repository of business rule constants for notifications.
 *
 * <p>All validation annotations and validators reference these constants as the
 * single source of truth, ensuring rule changes propagate automatically across
 * DTOs, validators, and documentation.</p>
 */
public final class NotificationRules {

    /** Maximum number of characters allowed in a notification title. */
    public static final int MAX_TITLE_LENGTH = 80;

    /** Maximum number of characters allowed in a notification body. */
    public static final int MAX_BODY_LENGTH = 200;

    /** Maximum number of days into the future an event reminder date may be scheduled. */
    public static final int MAX_EVENT_FUTURE_DAYS = 365;

    private NotificationRules() {}
}
