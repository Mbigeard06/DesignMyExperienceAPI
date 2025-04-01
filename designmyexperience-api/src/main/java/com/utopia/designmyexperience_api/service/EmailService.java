package com.utopia.designmyexperience_api.service;

import com.utopia.designmyexperience_api.model.BusinessOwner;
import com.utopia.designmyexperience_api.model.Client;
import com.utopia.designmyexperience_api.utility.IMailer;
import com.utopia.designmyexperience_api.utility.TemplateLoader;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final IMailer mailer;

    public EmailService(IMailer mailer) {
        this.mailer = mailer;
    }

    public void welcomeClient(Client client) {
        Map<String, String> variables = new HashMap<>();
        variables.put("name", client.getFirstName());

        String body = TemplateLoader.load("welcome-client.txt", variables);
        mailer.send(client.getEmail(), "Bienvenue sur DesignMyExperience 🎉", body);
    }

    public void welcomeBusinessOwner(BusinessOwner owner) {
        Map<String, String> variables = new HashMap<>();
        variables.put("name", owner.getFirstName());
        variables.put("businessName", owner.getBusinessName());

        String body = TemplateLoader.load("welcome-business-owner.txt", variables);
        mailer.send(owner.getEmail(), "Bienvenue parmi les pros de DesignMyExperience 🚀", body);
    }
}