package com.ibm.backend.service;

import com.ibm.backend.dto.CustomRentDTO;
import com.ibm.backend.dto.RentDTO;
import com.ibm.backend.dto.UserDTO;
import com.ibm.backend.entity.Clients;
import com.ibm.backend.entity.Devices;
import com.ibm.backend.entity.Rents;
import com.ibm.backend.entity.Users;
import com.ibm.backend.enums.RentState;
import com.ibm.backend.repository.ClientsRepo;
import com.ibm.backend.repository.DevicesRepo;
import com.ibm.backend.repository.RentsRepo;
import com.ibm.backend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentsService {

    @Autowired
    private RentsRepo rentsRepo;

    @Autowired
    private ClientsRepo clientsRepo;

    @Autowired
    private DevicesRepo devicesRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private UsersService usersService;

    @Autowired
    private DTOConversionService dtoConversionService;

    public RentDTO getAllRents() {
        RentDTO response = new RentDTO();

        try {
            List<Rents> result = rentsRepo.findAll();
            List<CustomRentDTO> resultList = dtoConversionService.convertToRentDTOs(result);
            if (!result.isEmpty()) {
                response.setRents(resultList);
                response.setStatusCode(200);
                response.setMessage("Consulta realizada con éxito");
            } else {
                response.setStatusCode(404);
                response.setMessage("No se encontraron alquileres");
            }
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Ha ocurrido un error: " + e.getMessage());
            return response;
        }
    }

    public RentDTO addRent(CustomRentDTO addRentRequest) {
        RentDTO response = new RentDTO();

        try {
            LocalDate rentDate = addRentRequest.getRentDate();
            LocalDate expDate = addRentRequest.getExpDate();
            LocalDate today = LocalDate.now();
            LocalDate twoDaysLater = today.plusDays(2);
            LocalDate twelveDaysLaterRent = rentDate.plusDays(12);
            Rents rent = new Rents();

            Clients client = clientsRepo.findById(addRentRequest.getClientId()).orElseThrow();
            Devices device = devicesRepo.findById(addRentRequest.getDeviceId()).orElseThrow();
            Users user = usersRepo.findById(addRentRequest.getUserId()).orElseThrow();

            if (rentDate.isBefore(today)) {
                response.setStatusCode(400);
                response.setMessage("La fecha no puede ser anterior a hoy");
            } else if (rentDate.isBefore(twoDaysLater)) {
                response.setStatusCode(400);
                response.setMessage("La fecha solo puede ser 2 dias despues");
            } else if (expDate.isAfter(twelveDaysLaterRent)) {
                response.setStatusCode(400);
                response.setMessage("Fecha limite de 12 dias");
            } else if (device.getAmount() == 0) {
                response.setStatusCode(400);
                response.setMessage("Sin stock");
            } else {
                rent.setRentState(RentState.PENDIENTE);
                rent.setRentDate(addRentRequest.getRentDate());
                rent.setExpDate(addRentRequest.getExpDate());
                device.setTimesRequested(device.getTimesRequested() + 1);
                client.setTimesRented(client.getTimesRented() + 1);
                rent.setClients(client);
                rent.setDevices(device);
                client.addRent(rent);
                device.addRent(rent);
                user.addRent(rent);
                device.setAmount(device.getAmount() - 1);
            }

            devicesRepo.save(device);
            Rents rentsResult = rentsRepo.save(rent);

            if (rentsResult.getId() > 0) {
                response.setMessage("Alquiler guardado con éxito");
                response.setStatusCode(200);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }
}
