package com.utopia.designmyexperience_api.utility;

public interface IMailer {
    /**
     * Sends a plain text email.
     *
     * @param to      Email address of the receiver
     * @param subject Subject of the email
     * @param body    Body content of the email (plain text)
     */
    void send(String to, String subject, String body);
}