package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Event DTO for achievement unlocked events published by the gamification service.
 *
 * <p>Triggered when a user unlocks a collectable achievement (mona) on the platform.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievementUnlockedEventDto {

    /** ID of the user who unlocked the achievement. */
    @NotNull
    private UUID userId;

    /** Display name of the unlocked achievement. */
    @NotBlank
    private String achievementName;

    /** ID of the achievement, used as referenceId in the notification. */
    @NotNull
    private UUID achievementId;
}
