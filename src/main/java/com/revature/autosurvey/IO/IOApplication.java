package com.revature.autosurvey.IO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
public class IOApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(IOApplication.class, args);
	}
}