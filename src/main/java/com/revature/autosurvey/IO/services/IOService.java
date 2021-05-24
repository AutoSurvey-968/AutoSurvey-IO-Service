package com.revature.autosurvey.IO.services;

import reactor.core.publisher.Mono;

public interface IOService {

	Mono<Void> sendEmail(String[] recipients, String subject, String body, String[] attachments);
}