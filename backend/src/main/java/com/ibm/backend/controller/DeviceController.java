package com.ibm.backend.controller;

import com.ibm.backend.dto.CustomDeviceDTO;
import com.ibm.backend.dto.DeviceDTO;
import com.ibm.backend.service.DevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeviceController {

    @Autowired
    private DevicesService devicesService;

    @GetMapping("/adminuser/get-all-devices")
    public ResponseEntity<DeviceDTO> getAllClients () {
        return ResponseEntity.ok(devicesService.getAllDevices());
    }

    @PostMapping("/adminuser/add-device")
    public ResponseEntity<DeviceDTO> addClient(@RequestBody CustomDeviceDTO request) {
        return ResponseEntity.ok(devicesService.addDevice(request));
    }

}
