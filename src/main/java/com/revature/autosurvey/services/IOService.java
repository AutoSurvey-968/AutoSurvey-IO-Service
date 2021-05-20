package com.revature.autosurvey.services;

public interface IOService {

	void sendEmail(String[] recipients, String subject, String body, String[] attachments);
}