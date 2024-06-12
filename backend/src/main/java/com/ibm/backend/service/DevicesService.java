package com.ibm.backend.service;

import com.ibm.backend.dto.CustomDeviceDTO;
import com.ibm.backend.dto.DeviceDTO;
import com.ibm.backend.entity.Devices;
import com.ibm.backend.repository.DevicesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DevicesService {

    @Autowired
    private DevicesRepo devicesRepo;

    @Autowired
    private DTOConversionService dtoConversionService;

    public DeviceDTO getAllDevices () {
        DeviceDTO response = new DeviceDTO();

        try {
            List<Devices> result = devicesRepo.findAll();
            List<CustomDeviceDTO> resultList = dtoConversionService.convertToDeviceDTOs(result);
            if (!result.isEmpty()) {
                response.setDevices(resultList);
                response.setStatusCode(200);
                response.setMessage("Successful");
            } else {
                response.setStatusCode(404);
                response.setMessage("No devices found");
            }
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: "+e.getMessage());
            return response;
        }
    }

    public DeviceDTO addDevice (CustomDeviceDTO addDeviceRequest) {
        DeviceDTO response = new DeviceDTO();

        try {
            Devices device = new Devices();
            device.setName(addDeviceRequest.getName());
            device.setDeviceType(addDeviceRequest.getDeviceType());
            device.setAmount(addDeviceRequest.getAmount());
            Devices devicesResult = devicesRepo.save(device);


            if(devicesResult.getId()>0){
                response.setMessage("Device saved successfully");
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public DeviceDTO deleteDevice (Integer deviceId) {
        DeviceDTO response = new DeviceDTO();

        try {
            Optional<Devices> deviceOptional = devicesRepo.findById(deviceId);
            if (deviceOptional.isPresent()){
                devicesRepo.deleteById(deviceId);
                response.setStatusCode(200);
                response.setMessage("Device deleted succesfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Device not found for deletion");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting device: "+e.getMessage());
        }
        return response;
    }
}
