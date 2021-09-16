package com.revature.autosurvey.io.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.autosurvey.IO.services.IOService;
import com.revature.autosurvey.IO.services.IOServiceImpl;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class IOServiceTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public IOService getIoService(JavaMailSender javaMailSender) {
			IOServiceImpl service = new IOServiceImpl();
			service.setEmailSender(javaMailSender);
			return service;
		}

		@Bean
		public JavaMailSender getEmailSender() {
			return Mockito.mock(JavaMailSender.class);
		}

	}
	
	@Autowired
	private IOService ioService;
	
	@MockBean
	private JavaMailSender emailSender;
	
	@BeforeEach
	public void beforeEach() {
		
	}
	
	@Test
	void testSendEmailNoAttachmentsValid() {
		ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
		String[] recipients = {"mock@mock.com"};
		String subject = "Mock Subject";
		String body = "Mock Body";
		String[] attachments = {};
		
		Mono<Void> monoVoid = ioService.sendEmail(recipients, subject, body, attachments);
		StepVerifier.create(monoVoid).verifyComplete();
		
		verify(emailSender).send(captor.capture());
		SimpleMailMessage capture = captor.getValue();
		assertEquals(subject, capture.getSubject(), "Assert that the subject is that same.");
		assertEquals(body, capture.getText(), "Assert that the body is that same.");
		assertEquals(recipients[0], capture.getTo()[0], "Assert that the recipients is the same");
	}
	
	@Test
	void testSendEmailWithAttachmentsValid() {
		MimeMessage mockMessage = Mockito.mock(MimeMessage.class);
		when(emailSender.createMimeMessage()).thenReturn(mockMessage);
		ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
		String[] recipients = {"mock@mock.com"};
		String subject = "Mock Subject";
		String body = "Mock Body";
		String[] attachments = {"path/to/file.js"};
		
		Mono<Void> monoVoid = ioService.sendEmail(recipients, subject, body, attachments);
		StepVerifier.create(monoVoid).verifyComplete();
		
		
	}
	
	@Test
	void testSendEmailNoAttachmentsInvalid() {
		ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
		String[] recipients = {"mock@mock.com"};
		String subject = "Mock Subject";
		String body = "Mock Body";
		String[] attachments = {};
		
		Mockito.doThrow(MailSendException.class).when(emailSender).send(Mockito.any(SimpleMailMessage.class));
		Mono<Void> monoVoid = ioService.sendEmail(recipients, subject, body, attachments);
		StepVerifier.create(monoVoid).expectError();
		
		verify(emailSender).send(captor.capture());
		SimpleMailMessage capture = captor.getValue();
		assertEquals(subject, capture.getSubject(), "Assert that the subject is that same.");
		assertEquals(body, capture.getText(), "Assert that the body is that same.");
		assertEquals(recipients[0], capture.getTo()[0], "Assert that the recipients is the same");
	}
	
	@Test
	void testSendEmailWithAttachmentsInvalid() {
		MimeMessage mockMessage = Mockito.mock(MimeMessage.class);
		when(emailSender.createMimeMessage()).thenReturn(mockMessage);
		ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
		String[] recipients = {"mock@mock.com"};
		String subject = "Mock Subject";
		String body = "Mock Body";
		String[] attachments = {"path/to/file.js"};
		
		Mockito.doThrow(MailSendException.class).when(emailSender).send(Mockito.any(SimpleMailMessage.class));
		Mono<Void> monoVoid = ioService.sendEmail(recipients, subject, body, attachments);
		StepVerifier.create(monoVoid).expectError();
		
		
	}
}
