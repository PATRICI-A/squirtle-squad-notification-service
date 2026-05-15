package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyParcheEventDto {
    @NotNull
    private UUID targetUserId;

    @NotNull
    private UUID parcheId;

    @NotBlank
    private String parcheName;

    @NotNull
    @Positive
    private Double distanceKm;
}
