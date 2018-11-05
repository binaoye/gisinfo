package com.grid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.grid.service","com.grid.dao","com.grid.controllers"})
public class GisinfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GisinfoApplication.class, args);
	}
}
