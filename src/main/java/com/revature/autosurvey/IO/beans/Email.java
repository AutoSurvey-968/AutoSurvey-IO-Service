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
	
}
