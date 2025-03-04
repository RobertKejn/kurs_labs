package com.example.kurs.database;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private String name;

    private LocalDate dob;

    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Ticket> tickets;
}
