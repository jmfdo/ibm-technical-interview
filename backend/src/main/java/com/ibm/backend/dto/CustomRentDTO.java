package com.ibm.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.backend.enums.RentState;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomRentDTO {

    private int id;
    private int deviceId;
    private String deviceName;
    private int userId;
    private String rentedBy;
    private int clientId;
    private String rentedTo;
    private LocalDate rentDate;
    private LocalDate expDate;
    private RentState rentState;
}
