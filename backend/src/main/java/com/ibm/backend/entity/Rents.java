package com.ibm.backend.entity;

import com.ibm.backend.enums.RentState;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "rents")
@Data
public class Rents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "rent_user")
    Users users;

    @ManyToOne()
    @JoinColumn(name = "rent_client")
    Clients clients;

    @ManyToOne()
    @JoinColumn(name = "rent_device")
    Devices devices;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RentState rentState;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate rentDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate expDate;

}
