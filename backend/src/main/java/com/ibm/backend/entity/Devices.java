package com.ibm.backend.entity;

import com.ibm.backend.enums.DeviceType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name="devices")
@Data
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false, nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "devices")
    List<Rents> rents;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceType deviceType;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int amount;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int timesRequested;

    public void addRent(Rents rent) {
        rents.add(rent);
        rent.setDevices(this);
    }
}
