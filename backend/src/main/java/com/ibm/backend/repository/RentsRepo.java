package com.ibm.backend.repository;

import com.ibm.backend.entity.Rents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentsRepo extends JpaRepository<Rents, Integer> {

}
