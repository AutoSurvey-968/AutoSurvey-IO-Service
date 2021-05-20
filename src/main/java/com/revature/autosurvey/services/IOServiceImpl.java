package com.revature.autosurvey.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

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
	public void sendEmail(String[] recipients, String subject, String body, String[] attachments) {
		if (ArrayUtils.isEmpty(attachments)) {
			log.info("Sending and email without attachments...");
			SimpleMailMessage emailMessage = new SimpleMailMessage();
			emailMessage.setFrom("noreply@revature.com");
			emailMessage.setTo(recipients);
			emailMessage.setSubject(subject);
			emailMessage.setText(body);
			emailSender.send(emailMessage);
			log.trace("Email Sent successfully.");
		} else {
			MimeMessage message = emailSender.createMimeMessage();
			try {
				log.info("Sending and email with attachments...");
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setFrom("noreply@revature.com");
				helper.setTo(recipients);
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
	}
}
