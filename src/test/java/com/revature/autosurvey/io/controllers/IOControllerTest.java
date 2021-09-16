package com.revature.autosurvey.io.controllers;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.autosurvey.IO.controllers.Reportcontroller;
import com.revature.autosurvey.IO.services.IOService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class IOControllerTest {

	@TestConfiguration
	static class Configuration {
		@Bean
		public Reportcontroller getIoController(IOService ioService) {
			Reportcontroller ioController = new Reportcontroller();
			ioController.setIoService(ioService);
			return ioController;
		}

		@Bean
		public IOService getIoService() {
			return Mockito.mock(IOService.class);
		}

	}
	
	@Autowired
	private Reportcontroller ioController;
	
	@MockBean
	private IOService ioService;
	
	@Test
	void testSendEmail() {
		String[] recipients = {"mock@mock.com"};
		String subject = "Mock Subject";
		String body = "Mock Body";
		String[] attachments = {};
		
		when(ioService.sendEmail(recipients, subject, body, attachments)).thenReturn(Mono.empty());
		
		Mono<Void> monoVoid = ioController.sendEmail(recipients, subject, body, attachments);
		StepVerifier.create(monoVoid).verifyComplete();
	}
}
