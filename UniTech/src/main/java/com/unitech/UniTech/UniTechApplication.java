package com.unitech.UniTech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = "com.unitech.UniTech")

public class UniTechApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniTechApplication.class, args);
	}

	
	
}
