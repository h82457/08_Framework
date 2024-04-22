package com.project.pawlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;


@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class PawLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PawLifeApplication.class, args);
		
		
	}
	


}
