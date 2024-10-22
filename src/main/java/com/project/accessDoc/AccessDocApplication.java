package com.project.accessDoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AccessDocApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessDocApplication.class, args);
		
		// Show the SWAGGER documentation url in the console
        System.out.println("---------- SWAGGER Documentation ------------");
        System.out.println("http://[ip_server]:8080/swagger-ui/index.html");
        System.out.println("---------------------------------------------");
        
	}

}
