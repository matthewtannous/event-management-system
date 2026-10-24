package com.mat.Backend.application.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    LocalDateTime fromTime;

    @Column(nullable = false)
    LocalDateTime toTime;

    @Column(nullable = false)
    Double ticketPrice;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    @Positive
    Integer capacity;

    @PositiveOrZero
    Integer registered; // registered always starts at 0
}
