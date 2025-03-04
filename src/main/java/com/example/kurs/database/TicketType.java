package com.example.kurs.database;

import jakarta.persistence.*;

import java.time.Duration;

@Entity
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String type;

    private Duration taskDuration;
    private Duration dedlineDuration;
}
