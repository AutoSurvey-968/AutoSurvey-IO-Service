package com.revature.autosurvey.IO.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.autosurvey.IO.services.IOService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class IOController {
	@Autowired
	private IOService ioService;
	private static Logger log = LoggerFactory.getLogger(IOController.class);
	
	@PostMapping()
	public Mono<Void> sendEmail(@RequestParam(name = "recipient") String[] recipients, @RequestParam(name = "subject", defaultValue="From AutoSurvey") String subject, 
			@RequestParam(name = "message") String message, @RequestParam(name = "attachment", defaultValue="") String[] attachments) {
		log.trace("Received request to send email.");
		return ioService.sendEmail(recipients, subject, message, attachments);
	}
}