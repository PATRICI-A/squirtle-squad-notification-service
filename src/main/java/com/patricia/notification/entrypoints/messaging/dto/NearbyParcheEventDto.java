package com.patricia.notification.entrypoints.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyParcheEventDto {
    private String targetUserId;
    private String parcheId;
    private String parcheName;
    private Double distanceKm;
}