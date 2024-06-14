package com.ibm.backend.controller;

import com.ibm.backend.dto.CustomUserDTO;
import com.ibm.backend.dto.UserDTO;
import com.ibm.backend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> register (@RequestBody CustomUserDTO request) {
        return ResponseEntity.ok(usersService.register(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserDTO> login (@RequestBody CustomUserDTO request) {
        return ResponseEntity.ok(usersService.login(request));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<UserDTO> refreshToken (@RequestBody UserDTO request) {
        return ResponseEntity.ok(usersService.refreshToken(request));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<UserDTO> getAllUsers(){
        return ResponseEntity.ok(usersService.getAllUsers());
    }

}
