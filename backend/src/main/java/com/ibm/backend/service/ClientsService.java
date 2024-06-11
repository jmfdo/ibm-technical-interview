package com.ibm.backend.service;

import com.ibm.backend.dto.ClientReqRes;
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

    public ClientReqRes getAllClients () {
        ClientReqRes response = new ClientReqRes();

        try {
            List<Clients> result = clientsRepo.findAll();
            if (!result.isEmpty()) {
                response.setClientsList(result);
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

    public ClientReqRes addClient (ClientReqRes addClientRequest) {
        ClientReqRes response = new ClientReqRes();

        try {
            Clients client = new Clients();
            client.setId(addClientRequest.getId());
            client.setName(addClientRequest.getName());
            Clients clientsResult = clientsRepo.save(client);

            if(clientsResult.getId()>0){
                response.setClients(clientsResult);
                response.setMessage("Client saved successfully");
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ClientReqRes deleteClient(Integer clientId) {
        ClientReqRes response = new ClientReqRes();

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
