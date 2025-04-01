package com.utopia.designmyexperience_api.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class googleMailer implements IMailer {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(System.getProperty("MAIL_USERNAME")); // Adresse de l'expéditeur
        message.setTo(to);                                    // Destinataire
        message.setSubject(subject);                          // Sujet
        message.setText(body);                                // Contenu texte
        mailSender.send(message);
    }
}
