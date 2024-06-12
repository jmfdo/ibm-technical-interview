package com.ibm.backend.service;

import com.ibm.backend.dto.ClientDTO;
import com.ibm.backend.dto.CustomClientDTO;
import com.ibm.backend.entity.Clients;
import com.ibm.backend.repository.ClientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {

    @Autowired
    private ClientsRepo clientsRepo;

    @Autowired
    private DTOConversionService dtoConversionService;

    public ClientDTO getAllClients () {
        ClientDTO response = new ClientDTO();

        try {
            List<Clients> result = clientsRepo.findAll();
            List<CustomClientDTO> resultsList = dtoConversionService.convertToClientDTOs(result);
            if (!result.isEmpty()) {
                response.setClients(resultsList);
                response.setStatusCode(200);
                response.setMessage("Successful");
            } else {
                response.setStatusCode(404);
                response.setMessage("No clients found");
            }
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: "+e.getMessage());
            return response;
        }
    }

    public ClientDTO addClient (CustomClientDTO addClientRequest) {
        ClientDTO response = new ClientDTO();

        try {
            Clients client = new Clients();
            client.setId(addClientRequest.getId());
            client.setName(addClientRequest.getName());
            Clients clientsResult = clientsRepo.save(client);

            if(clientsResult.getId()>0){
                response.setMessage("Client saved successfully");
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ClientDTO deleteClient(Integer clientId) {
        ClientDTO response = new ClientDTO();

        try {
            Optional<Clients> clientsOptional = clientsRepo.findById(clientId);

            if (clientsOptional.isPresent()) {
                clientsRepo.deleteById(clientId);
                response.setStatusCode(200);
                response.setMessage("Client deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Client not found for deletion");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting client: " + e.getMessage());
        }
        return response;
    }
}
