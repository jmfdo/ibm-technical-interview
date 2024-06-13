package com.ibm.backend.controller;

import com.ibm.backend.dto.CustomRentDTO;
import com.ibm.backend.dto.RentDTO;
import com.ibm.backend.service.RentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RentController {

    @Autowired
    private RentsService rentsService;

    @GetMapping("/adminuser/get-all-rents")
    public ResponseEntity<RentDTO> getAllRents () {
        return ResponseEntity.ok(rentsService.getAllRents());
    }

    @PostMapping("/adminuser/add-rent")
    public ResponseEntity<RentDTO> addRent (@RequestBody CustomRentDTO request) {
        return ResponseEntity.ok(rentsService.addRent(request));
    }
}
