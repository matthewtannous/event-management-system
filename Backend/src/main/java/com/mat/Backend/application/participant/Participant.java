package com.mat.Backend.application.participant;

import com.mat.Backend.application.event.Event;
import com.mat.Backend.application.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "participants", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "event_id"})})
@Getter
@Setter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
