package com.jamipariseva.dto.location;

import com.jamipariseva.exception.BadRequestException;

public enum LocationLevel {

    DISTRICT,
    SUBDIVISION,
    CIRCLE,
    TEHSIL,
    VILLAGE,
    MOUJA,
    MOUZA;

    public static LocationLevel from(String value) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException("request_for is required");
        }
        try {
            return LocationLevel.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(
                    "Invalid request_for. Use: district, subdivision, circle, tehsil, village, mouja");
        }
    }

    public boolean isVillageLevel() {
        return this == VILLAGE || this == MOUJA || this == MOUZA;
    }
}
