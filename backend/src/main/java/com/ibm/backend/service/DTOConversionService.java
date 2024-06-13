package com.ibm.backend.service;

import com.ibm.backend.dto.*;
import com.ibm.backend.entity.Clients;
import com.ibm.backend.entity.Devices;
import com.ibm.backend.entity.Rents;
import com.ibm.backend.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DTOConversionService {

    private CustomRentDTO convertToRentDTO(Rents rent) {
        CustomRentDTO dto = new CustomRentDTO();
        dto.setId(rent.getId());
        dto.setRentDate(rent.getRentDate());
        dto.setExpDate(rent.getExpDate());
        dto.setRentState(rent.getRentState());
        dto.setUserId(rent.getUsers().getId());
        dto.setRentedBy(rent.getUsers().getName());
        dto.setClientId(rent.getClients().getId());
        dto.setRentedTo(rent.getClients().getName());
        dto.setDeviceId(rent.getDevices().getId());
        dto.setDeviceName(rent.getDevices().getName());
        return dto;
    }

    public List<CustomRentDTO> convertToRentDTOs(List<Rents> rents) {
        return rents.stream().map(this::convertToRentDTO).collect(Collectors.toList());
    }

    private CustomDeviceDTO convertToDeviceDTO(Devices device) {
        CustomDeviceDTO dto = new CustomDeviceDTO();
        dto.setId(device.getId());
        dto.setName(device.getName());
        dto.setDeviceType(device.getDeviceType());
        dto.setAmount(device.getAmount());
        dto.setTimesRequested(device.getTimesRequested());
        return dto;
    }

    public List<CustomDeviceDTO> convertToDeviceDTOs(List<Devices> devices) {
        return devices.stream().map(this::convertToDeviceDTO).collect(Collectors.toList());
    }

    private CustomClientDTO convertToClientDTO(Clients client) {
        CustomClientDTO dto = new CustomClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setTimesRented(client.getTimesRented());
        return dto;
    }

    public List<CustomClientDTO> convertToClientDTOs(List<Clients> clients) {
        return clients.stream().map(this::convertToClientDTO).collect(Collectors.toList());
    }

    private CustomUserDTO convertToUserDTO(Users user) {
        CustomUserDTO dto = new CustomUserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    public List<CustomUserDTO> convertToUserDTOs(List<Users> users) {
        return users.stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }
}
