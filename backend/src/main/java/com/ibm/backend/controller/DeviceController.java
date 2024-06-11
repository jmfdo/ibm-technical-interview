package com.ibm.backend.controller;

import com.ibm.backend.dto.ClientReqRes;
import com.ibm.backend.dto.DeviceReqRes;
import com.ibm.backend.service.ClientsService;
import com.ibm.backend.service.DevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeviceController {

    @Autowired
    private DevicesService devicesService;

    @GetMapping("/adminuser/get-all-devices")
    public ResponseEntity<DeviceReqRes> getAllClients () {
        return ResponseEntity.ok(devicesService.getAllDevices());
    }

    @PostMapping("/adminuser/add-device")
    public ResponseEntity<DeviceReqRes> addClient(@RequestBody DeviceReqRes request) {
        return ResponseEntity.ok(devicesService.addDevice(request));
    }

    @DeleteMapping("/adminuser/delete-device/{deviceId}")
    public ResponseEntity<DeviceReqRes> deleteClient(@PathVariable Integer deviceId) {
        return ResponseEntity.ok(devicesService.deleteDevice(deviceId));
    }
}
