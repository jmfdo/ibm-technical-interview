package com.ibm.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "rents")
@Data
public class Rents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false)
    private int deviceId;

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
    private int clientId;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date rentDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date expDate;

}
