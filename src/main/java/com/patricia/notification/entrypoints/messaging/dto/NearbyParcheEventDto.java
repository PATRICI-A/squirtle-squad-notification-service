package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * Event DTO for nearby parche discovery events published by the parche service.
 *
 * <p>Triggered when a parche is detected within proximity of the target user's location.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyParcheEventDto {

    /** ID of the user who will receive the nearby parche notification. */
    @NotNull
    private UUID targetUserId;

    @NotNull
    private UUID parcheId;

    @NotBlank
    private String parcheName;

    /** Distance in kilometers between the user and the parche. Must be positive. */
    @NotNull
    @Positive
    private Double distanceKm;
}
