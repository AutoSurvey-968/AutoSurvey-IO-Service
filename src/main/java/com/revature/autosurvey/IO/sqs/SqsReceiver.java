package com.revature.autosurvey.IO.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import com.amazonaws.util.json.Jackson;
import com.revature.autosurvey.IO.beans.Email;
import com.revature.autosurvey.IO.services.IOService;

@Component
public class SqsReceiver {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IOService ios;
	
	public SqsReceiver() {
		super();
	}
	
	@SqsListener(value="https://sqs.us-east-1.amazonaws.com/855430746673/EmailQueue", deletionPolicy=SqsMessageDeletionPolicy.ALWAYS)
	public void queueListener(String payload) {
		Email payloadEmail = Jackson.fromJsonString(payload, Email.class);
		String[] recipient = {payloadEmail.getRecipient()};
		ios.sendEmail(recipient, payloadEmail.getSubject(), payloadEmail.getBody(), new String[0]).subscribe();
	}
}
