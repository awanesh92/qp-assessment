package com.awanesh.APIGrocery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.awanesh.APIGrocery.controller")
public class ApiGroceryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGroceryApplication.class, args);
	}

}
