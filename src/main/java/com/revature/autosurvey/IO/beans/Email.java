package com.revature.autosurvey.IO.beans;

import lombok.Data;

@Data
public class Email {
	private String recipient;
	private String subject;
	private String body;

	public Email() {
		super();
	}
	
	public Email(String recipient, String subject, String body) {
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;

	}
}
	
