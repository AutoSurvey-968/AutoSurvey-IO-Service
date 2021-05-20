package com.revature.autosurvey.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.autosurvey.services.IOService;
import com.revature.autosurvey.services.IOServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class IOController {
	@Autowired
	private IOService ioService;
	private static Logger log = LoggerFactory.getLogger(IOController.class);
	
	@PostMapping("io")
	public void sendEmail(@RequestParam(name = "recipient") String[] recipients, @RequestParam(name = "subject") String subject, 
			@RequestParam(name = "message") String message, @RequestParam(name = "attachment", defaultValue="") String[] attachments) {
		log.trace("Received request to send email.");
		ioService.sendEmail(recipients, subject, message, attachments);
	}
}