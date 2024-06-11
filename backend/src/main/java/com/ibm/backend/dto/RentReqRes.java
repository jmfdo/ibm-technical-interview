package com.ibm.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.backend.entity.Rents;
import com.ibm.backend.enums.RentState;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RentReqRes {

    private int statusCode;
    private String message;
    private String error;
    private int id;
    private int deviceId;
    private int userId;
    private int clientId;
    private Date rentDate;
    private Date expDate;
    private RentState rentState;
    private Rents devices;
    private List<Rents> rentsList;
}
