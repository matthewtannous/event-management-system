package com.mat.Backend.application.event;


import java.time.LocalDateTime;

public record EventResponse(Long id, String name, LocalDateTime fromTime, LocalDateTime toTime, Double ticketPrice,
                            String location, Integer capacity, Integer registered) {
}
