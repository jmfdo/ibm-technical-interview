package com.ibm.backend.controller;

import com.ibm.backend.dto.ClientDTO;
import com.ibm.backend.dto.CustomClientDTO;
import com.ibm.backend.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    @Autowired
    private ClientsService clientsService;

    @GetMapping("/adminuser/get-all-clients")
    public ResponseEntity<ClientDTO> getAllClients () {
        return ResponseEntity.ok(clientsService.getAllClients());
    }

    @PostMapping("/adminuser/add-client")
    public ResponseEntity<ClientDTO> addClient(@RequestBody CustomClientDTO request) {
        return ResponseEntity.ok(clientsService.addClient(request));
    }
}
