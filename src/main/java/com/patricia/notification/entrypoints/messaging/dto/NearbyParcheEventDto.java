package com.patricia.notification.entrypoints.messaging.dto;

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
    private UUID targetUserId;
    private UUID parcheId;
    private String parcheName;
    private Double distanceKm;
}