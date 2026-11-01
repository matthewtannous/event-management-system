package com.mat.Backend.application.announcement;

import com.mat.Backend.application.event.Event;
import com.mat.Backend.application.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Getter
@Setter
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean isPublic; // Whether the message can be seen by participants or only organizers

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // announcement creator

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private LocalDateTime createdAt = LocalDateTime.now();
}