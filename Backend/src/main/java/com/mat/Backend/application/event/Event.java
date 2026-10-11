package com.mat.Backend.application.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
    Date fromTime;

    @Column(nullable = false)
    Date toTime;

    @Column(nullable = false)
    Double ticketPrice;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    Integer capacity;

    @Column(nullable = false)
    Integer registered;
}
