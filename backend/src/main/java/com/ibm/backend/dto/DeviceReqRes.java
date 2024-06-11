package com.ibm.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.backend.entity.Clients;
import com.ibm.backend.entity.Devices;
import com.ibm.backend.enums.DeviceType;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceReqRes {

    private int statusCode;
    private String message;
    private String error;
    private int id;
    private String name;
    private DeviceType deviceType;
    private int amount;
    private Devices devices;
    private List<Devices> devicesList;
}
