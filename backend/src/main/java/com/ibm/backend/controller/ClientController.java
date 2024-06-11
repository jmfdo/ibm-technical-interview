package com.ibm.backend.controller;

import com.ibm.backend.dto.ClientReqRes;
import com.ibm.backend.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    @Autowired
    private ClientsService clientsService;

    @GetMapping("/adminuser/get-all-clients")
    public ResponseEntity<ClientReqRes> getAllClients () {
        return ResponseEntity.ok(clientsService.getAllClients());
    }

    @PostMapping("/adminuser/add-client")
    public ResponseEntity<ClientReqRes> addClient(@RequestBody ClientReqRes request) {
        return ResponseEntity.ok(clientsService.addClient(request));
    }

    @DeleteMapping("/adminuser/delete-client/{clientId}")
    public ResponseEntity<ClientReqRes> deleteClient(@PathVariable Integer clientId) {
        return ResponseEntity.ok(clientsService.deleteClient(clientId));
    }
}
