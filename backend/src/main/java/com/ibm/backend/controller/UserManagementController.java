package com.ibm.backend.controller;

import com.ibm.backend.dto.RequestResponse;
import com.ibm.backend.entity.Users;
import com.ibm.backend.service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserManagementController {

    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/auth/register")
    public ResponseEntity<RequestResponse> register (@RequestBody RequestResponse request) {
        return ResponseEntity.ok(usersManagementService.register(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<RequestResponse> login (@RequestBody RequestResponse request) {
        return ResponseEntity.ok(usersManagementService.login(request));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<RequestResponse> refreshToken (@RequestBody RequestResponse request) {
        return ResponseEntity.ok(usersManagementService.refreshToken(request));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<RequestResponse> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());
    }

    @GetMapping("/admin/get-user/{userId}")
    public ResponseEntity<RequestResponse> getUserById(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));
    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<RequestResponse> getUserById(@PathVariable Integer userId, @RequestBody Users requestResponse){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, requestResponse));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<RequestResponse> getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        RequestResponse response = usersManagementService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<RequestResponse> deleteUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }
}
