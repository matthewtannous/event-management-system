package com.mat.frontend.api.dto;

import java.time.LocalDateTime;

public record EventRequest(
        String name,
        String description,
        LocalDateTime fromTime,
        LocalDateTime toTime,
        Double ticketPrice,
        String location,
        Integer capacity,
        Integer registered
) {
}
