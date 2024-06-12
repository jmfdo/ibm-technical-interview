package com.ibm.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.backend.enums.DeviceType;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomDeviceDTO {

    private int id;
    private String name;
    private DeviceType deviceType;
    private int amount;
    private int timesRequested;

}
