package com.ibm.backend.service;

import com.ibm.backend.dto.CustomClientDTO;
import com.ibm.backend.dto.CustomDeviceDTO;
import com.ibm.backend.dto.CustomRentDTO;
import com.ibm.backend.dto.RentDTO;
import com.ibm.backend.entity.Clients;
import com.ibm.backend.entity.Devices;
import com.ibm.backend.entity.Rents;
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
        return dto;
    }

    public List<CustomDeviceDTO> convertToDeviceDTOs(List<Devices> devices) {
        return devices.stream().map(this::convertToDeviceDTO).collect(Collectors.toList());
    }

    private CustomClientDTO convertToClientDTO(Clients client) {
        CustomClientDTO dto = new CustomClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        return dto;
    }

    public List<CustomClientDTO> convertToClientDTOs(List<Clients> clients) {
        return clients.stream().map(this::convertToClientDTO).collect(Collectors.toList());
    }
}
