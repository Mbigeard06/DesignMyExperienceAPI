package com.utopia.designmyexperience_api.controller;

import com.utopia.designmyexperience_api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class TestController {

    @Autowired
    private EmailService emailService;
}