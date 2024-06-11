package com.ibm.backend.service;

import com.ibm.backend.dto.UserReqRes;
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
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
     private PasswordEncoder passwordEncoder;

    public UserReqRes register(UserReqRes registrationRequest){
        UserReqRes response = new UserReqRes();

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

    public UserReqRes login(UserReqRes loginRequest) {
        UserReqRes response = new UserReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setRole(String.valueOf(user.getRole()));
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully logged in");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public UserReqRes refreshToken(UserReqRes refreshTokenRequest) {
        UserReqRes response = new UserReqRes();

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

    public UserReqRes getAllUsers(){
        UserReqRes response = new UserReqRes();

        try{
            List<Users> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                response.setUsersList(result);
                response.setStatusCode(200);
                response.setMessage("Successful");
            }else {
                response.setStatusCode(404);
                response.setMessage("No users found");
            }
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
            return response;
        }
    }

    public UserReqRes getUsersById(Integer id) {
        UserReqRes response = new UserReqRes();

        try {
            Users userById = usersRepo.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
            response.setUsers(userById);
            response.setStatusCode(200);
            response.setMessage("User with id "+id+" found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: "+e.getMessage());
        }
        return response;
    }

    public UserReqRes deleteUser(Integer userId) {
        UserReqRes response = new UserReqRes();

        try {
            Optional<Users> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                response.setStatusCode(200);
                response.setMessage("User deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return response;
    }

    public UserReqRes updateUser(Integer userId, Users updateUser) {
        UserReqRes response = new UserReqRes();

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
                response.setUsers(savedUser);
                response.setStatusCode(200);
                response.setMessage("User updated successfully");
            }
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred while updating the user: "+e.getMessage());
        }
        return response;
    }

    public UserReqRes getMyInfo(String email) {
        UserReqRes response = new UserReqRes();
         try {
             Optional<Users> userOptional = usersRepo.findByEmail(email);

             if (userOptional.isPresent()) {
                 response.setUsers(userOptional.get());
                 response.setStatusCode(200);
                 response.setMessage("Successful");
             } else {
                 response.setStatusCode(404);
                 response.setMessage("User not found for update");
             }
         } catch (Exception e) {
             response.setStatusCode(500);
             response.setMessage("Error occurred while getting the user info: "+e.getMessage());
         }
         return response;
    }
}
