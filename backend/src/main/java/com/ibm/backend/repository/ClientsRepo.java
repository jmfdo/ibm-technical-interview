package com.ibm.backend.repository;

import com.ibm.backend.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepo extends JpaRepository<Clients, Integer> {

}
