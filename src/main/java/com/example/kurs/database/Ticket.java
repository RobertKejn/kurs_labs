package com.example.kurs.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

//@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime plannedAt;
    @Transient
    private LocalDateTime deadline;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TicketType type;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private TicketStatus status;
    @ManyToOne
    @JoinColumn(name = "operator_id")
    @JsonBackReference
    private Operator operator;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;
}
