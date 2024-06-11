package com.ibm.backend.repository;

import com.ibm.backend.entity.Devices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicesRepo extends JpaRepository<Devices, Integer> {
}
