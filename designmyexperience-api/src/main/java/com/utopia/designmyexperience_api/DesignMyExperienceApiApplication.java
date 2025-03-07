package com.utopia.designmyexperience_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.utopia.designmyexperience_api")
public class DesignMyExperienceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesignMyExperienceApiApplication.class, args);
	}

}
