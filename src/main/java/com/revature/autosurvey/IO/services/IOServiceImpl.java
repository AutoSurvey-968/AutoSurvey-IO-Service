package com.revature.autosurvey.IO.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Component
public class IOServiceImpl implements IOService {
	@Autowired
	private JavaMailSender emailSender;
	private static Logger log = LoggerFactory.getLogger(IOServiceImpl.class);

	@Override
	public Mono<Void> sendEmail(String[] recipients, String subject, String body, String[] attachments) {
		String[] newRecipients = newRecipients(recipients);
		
		if (ArrayUtils.isEmpty(attachments)) {
			log.info("Sending and email without attachments...");
			try {

				SimpleMailMessage emailMessage = new SimpleMailMessage();
				emailMessage.setFrom("autosurvey968@gmail.com");
				emailMessage.setTo(newRecipients);
				emailMessage.setSubject(subject);
				emailMessage.setText(body);
				emailSender.send(emailMessage);
				log.trace("Email Sent successfully.");
			} catch (Exception e) {
				for (StackTraceElement s : e.getStackTrace()) {
					log.debug(s.toString());
				}
			}
		} else {
			MimeMessage message = emailSender.createMimeMessage();
			try {
				log.info("Sending and email with attachments...");
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setFrom("autosurvey968@gmail.com");
				helper.setTo(newRecipients);
				helper.setSubject(subject);
				helper.setText(body);

				for (String str : attachments) {
					FileSystemResource file = new FileSystemResource(new File(str));
					helper.addAttachment(file.getFilename(), file);
				}
				emailSender.send(message);
				log.trace("Email sent successfully.");
			} catch (MessagingException e) {
				for (StackTraceElement s : e.getStackTrace()) {
					log.debug(s.toString());
				}
			}
		}
		return Mono.empty();
	}
	
	public static String[] newRecipients(String[] recipients) {
		List<String> recipientsTemp = new ArrayList<>();
		
		for (String str : recipients) {
			if (isValidEmailAddress(str)) {
				recipientsTemp.add(str);
			}
		}

		String[] newRecipients = new String[recipientsTemp.size()];

		for (int i = 0; i < newRecipients.length; i++) {
			newRecipients[i] = recipientsTemp.get(i);
		}
		
		return newRecipients;
	}

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

}
