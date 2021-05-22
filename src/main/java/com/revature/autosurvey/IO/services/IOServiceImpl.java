package com.revature.autosurvey.IO.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.ArrayUtils;

@Component
public class IOServiceImpl {
	@Autowired
	private JavaMailSender emailSender;
	private static Logger log = LogManager.getLogger(IOServiceImpl.class);

	public void sendEmail(String[] recipients, String subject, String body, String[] attachments) {
		if (ArrayUtils.isEmpty(attachments)) {
			SimpleMailMessage emailMessage = new SimpleMailMessage();
			emailMessage.setFrom("noreply@revature.com");
			emailMessage.setTo(recipients);
			emailMessage.setSubject(subject);
			emailMessage.setText(body);
			emailSender.send(emailMessage);
		} else {
			MimeMessage message = emailSender.createMimeMessage();
			try {
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
				
			} catch (MessagingException e) {
				for (StackTraceElement s : e.getStackTrace()) {
					log.warn(s);
				}
			}
		}
	}
}
