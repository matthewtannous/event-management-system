package com.mat.Backend.application.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record EventRequest(
        @NotBlank String name,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fromTime,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime toTime,
        @NotNull @PositiveOrZero Double ticketPrice, @NotBlank String location, @NotNull Integer capacity,
        @PositiveOrZero Integer registered // can be blank when creating new event
) {
}
