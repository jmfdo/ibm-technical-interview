package com.ibm.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "clients")
@Data
public class Clients {

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int timesRented;

    @OneToMany(mappedBy = "clients")
    List<Rents> rentsList;
}
