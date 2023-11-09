package com.charlie.toggleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.charlie.repositories")
public class ToggleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToggleServiceApplication.class, args);
	}

}
