package com.ibm.backend.controller;

import com.ibm.backend.dto.UserDTO;
import com.ibm.backend.entity.Users;
import com.ibm.backend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> register (@RequestBody UserDTO request) {
        return ResponseEntity.ok(usersService.register(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserDTO> login (@RequestBody UserDTO request) {
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

    @GetMapping("/admin/get-user/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId){
        return ResponseEntity.ok(usersService.getUsersById(userId));
    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId, @RequestBody Users requestResponse){
        return ResponseEntity.ok(usersService.updateUser(userId, requestResponse));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<UserDTO> getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDTO response = usersService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(usersService.deleteUser(userId));
    }
}
