package com.ibm.backend.service;

import com.ibm.backend.dto.CustomUserDTO;
import com.ibm.backend.dto.UserDTO;
import com.ibm.backend.entity.Users;
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

    @Autowired
    private DTOConversionService dtoConversionService;

    public UserDTO register(CustomUserDTO registrationRequest){
        UserDTO response = new UserDTO();

        try {
            Users user = new Users();
            user.setEmail(registrationRequest.getEmail());
            user.setRole(registrationRequest.getRole());
            user.setName(registrationRequest.getName());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            Users usersResult = usersRepo.save(user);

            if(usersResult.getId()>0){
                response.setMessage("Usuario registrado con éxito");
                response.setStatusCode(200);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public UserDTO login(CustomUserDTO loginRequest) {
        UserDTO response = new UserDTO();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setRole(user.getRole());
            response.setExpirationTime("24Hrs");
            response.setId(user.getId());
            response.setMessage("Ha iniciado sesión");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public UserDTO refreshToken(UserDTO refreshTokenRequest) {
        UserDTO response = new UserDTO();

        try {
            String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            Users users = usersRepo.findByEmail(ourEmail).orElseThrow();

            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hrs");
                response.setMessage("Token actualizado existosamente");
            }
            response.setStatusCode(200);
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    public UserDTO getAllUsers(){
        UserDTO response = new UserDTO();

        try{
            List<Users> result = usersRepo.findAll();
            List<CustomUserDTO> resultList = dtoConversionService.convertToUserDTOs(result);
            if (!result.isEmpty()) {
                response.setUsers(resultList);
                response.setStatusCode(200);
                response.setMessage("Usuarios obtenidos exitosamente");
            } else {
                response.setStatusCode(404);
                response.setMessage("No se encontraron usuarios");
            }
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Ha ocurrido un error: " + e.getMessage());
            return response;
        }
    }
}
