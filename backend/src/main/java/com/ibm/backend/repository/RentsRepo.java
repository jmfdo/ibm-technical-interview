package com.ibm.backend.repository;

import com.ibm.backend.entity.Rents;
import com.ibm.backend.enums.RentState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentsRepo extends JpaRepository<Rents, Integer> {
}
