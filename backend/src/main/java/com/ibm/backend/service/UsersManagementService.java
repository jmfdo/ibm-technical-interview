package com.ibm.backend.service;

import com.ibm.backend.dto.RequestResponse;
import com.ibm.backend.entity.Users;
import com.ibm.backend.enums.UserRole;
import com.ibm.backend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UsersManagementService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
     private PasswordEncoder passwordEncoder;

    public RequestResponse register(RequestResponse registrationRequest){
        RequestResponse response = new RequestResponse();

        try {
            Users user = new Users();
            user.setEmail(registrationRequest.getEmail());
            user.setRole(UserRole.valueOf(registrationRequest.getRole()));
            user.setName(registrationRequest.getName());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            Users usersResult = usersRepo.save(user);

            if(usersResult.getId()>0){
                response.setUsers(usersResult);
                response.setMessage("User saved successfully");
                response.setStatusCode(200);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public RequestResponse login(RequestResponse loginRequest) {
        RequestResponse response = new RequestResponse();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response .setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully logged in");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public RequestResponse refreshToken(RequestResponse refreshTokenRequest) {
        RequestResponse response = new RequestResponse();

        try {
            String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            Users users = usersRepo.findByEmail(ourEmail).orElseThrow();

            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hrs");
                response.setMessage("Successfully refreshed token");
            }
            response.setStatusCode(200);
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    public RequestResponse getAllUsers(){
        RequestResponse requestResponse = new RequestResponse();

        try{
            List<Users> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                requestResponse.setUsersList(result);
                requestResponse.setStatusCode(200);
                requestResponse.setMessage("Successful");
            }else {
                requestResponse.setStatusCode(404);
                requestResponse.setMessage("No users found");
            }
            return requestResponse;
        } catch (Exception e) {
            requestResponse.setStatusCode(500);
            requestResponse.setMessage("Error ocurred: " + e.getMessage());
            return requestResponse;
        }
    }

    public RequestResponse getUsersById(Integer id) {
        RequestResponse requestResponse = new RequestResponse();

        try {
            Users userById = usersRepo.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
            requestResponse.setUsers(userById);
            requestResponse.setStatusCode(200);
            requestResponse.setMessage("Users with id "+userById+" found successfully");
        } catch (Exception e) {
            requestResponse.setStatusCode(500);
            requestResponse.setMessage("Error occured: "+e.getMessage());
        }
        return requestResponse;
    }

    public RequestResponse deleteUser(Integer userId) {
        RequestResponse requestResponse = new RequestResponse();

        try {
            Optional<Users> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                requestResponse.setStatusCode(200);
                requestResponse.setMessage("User deleted successfully");
            } else {
                requestResponse.setStatusCode(404);
                requestResponse.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            requestResponse.setStatusCode(500);
            requestResponse.setMessage("Error occurred whiledeleting user: " + e.getMessage());
        }
        return requestResponse;
    }

    public RequestResponse updateUser(Integer userId, Users updateUser) {
        RequestResponse requestResponse = new RequestResponse();

        try {
            Optional<Users> userOptional = usersRepo.findById(userId);

            if (userOptional.isPresent()) {
                Users existingUser = userOptional.get();
                existingUser.setEmail(updateUser.getEmail());
                existingUser.setName(updateUser.getName());
                existingUser.setRole(updateUser.getRole());

                if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
                }

                Users savedUser = usersRepo.save(existingUser);
                requestResponse.setUsers(savedUser);
                requestResponse.setStatusCode(200);
                requestResponse.setMessage("User updated successfully");
            }
        } catch (Exception e){
            requestResponse.setStatusCode(500);
            requestResponse.setMessage("Error occurred while updating the user: "+e.getMessage());
        }
        return requestResponse;
    }

    public RequestResponse getMyInfo(String email) {
        RequestResponse requestResponse = new RequestResponse();
         try {
             Optional<Users> userOptional = usersRepo.findByEmail(email);

             if (userOptional.isPresent()) {
                 requestResponse.setUsers(userOptional.get());
                 requestResponse.setStatusCode(200);
                 requestResponse.setMessage("Successful");
             } else {
                 requestResponse.setStatusCode(404);
                 requestResponse.setMessage("User not found for update");
             }
         } catch (Exception e) {
             requestResponse.setStatusCode(500);
             requestResponse.setMessage("Error occurred while getting the user info: "+e.getMessage());
         }
         return requestResponse;
    }
}
