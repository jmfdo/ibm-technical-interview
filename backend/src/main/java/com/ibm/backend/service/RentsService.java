package com.ibm.backend.service;

import com.ibm.backend.dto.CustomRentDTO;
import com.ibm.backend.dto.RentDTO;
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
                response.setMessage("Successful");
            } else {
                response.setStatusCode(404);
                response.setMessage("No rents found");
            }
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
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
                response.setMessage("Date cant be before today");
            } else if (rentDate.isBefore(twoDaysLater)) {
                response.setStatusCode(400);
                response.setMessage("Date cant be before two days");
            } else if (expDate.isAfter(twelveDaysLaterRent)) {
                response.setStatusCode(400);
                response.setMessage("Expiration date only until 12 days");
            } else {
                rent.setDeviceId(addRentRequest.getDeviceId());
                rent.setClientId(addRentRequest.getClientId());
                rent.setUserId(addRentRequest.getUserId());
                rent.setRentState(RentState.PENDING);
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
                response.setMessage("Rent saved successfully");
                response.setStatusCode(200);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }
}
