package com.patricia.notification.domain.model.enums;

/**
 * Catalog of all notification types supported by the platform.
 *
 * <p>Each value maps to a boolean flag in {@link com.patricia.notification.domain.model.NotificationPreferences},
 * allowing users to enable or disable individual categories independently.</p>
 */
public enum NotificationType {

    /** A user sent a connection request to another user. */
    CONNECTION_REQUEST,

    /** A new message was posted in a parche the user belongs to. */
    PARCHE_MESSAGE,

    /** An upcoming event is approaching the 24h or 1h reminder threshold. */
    EVENT_REMINDER,

    /** A parche is happening near the user's current location. */
    NEARBY_PARCHE,

    /** The user unlocked an achievement. */
    ACHIEVEMENT_UNLOCKED,

    /** The user received an invitation to join a parche. */
    PARCHE_INVITATION,

    /** A one-time password was generated for identity verification. */
    OTP_VERIFICATION,

    /** A password reset code was issued for the user's account. */
    PASSWORD_RESET,

    /** A parche member accepted the user's invitation. */
    INVITATION_ACCEPTED,

    /** The user was invited to a private parche by a captain. */
    INVITATION_SENT,

    /** A new student joined a parche the user captains. */
    MEMBER_JOINED,

    /** Another user sent a match request to this user. */
    MATCH_RECEIVED,

    /** The user's match request was accepted or rejected by the recipient. */
    MATCH_RESPONSE,

    /** A friendship was established between the user and another user. */
    FRIENDSHIP_CREATED
}
