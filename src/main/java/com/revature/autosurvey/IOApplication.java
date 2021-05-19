package com.revature.autosurvey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class IOApplication {

	public static void main(String[] args) {
		SpringApplication.run(IOApplication.class, args);
	}
}