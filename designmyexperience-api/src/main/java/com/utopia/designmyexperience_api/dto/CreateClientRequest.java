package com.utopia.designmyexperience_api.dto;

import com.utopia.designmyexperience_api.model.Client;

public class CreateClientRequest {

    private Client client;
    private String password;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}