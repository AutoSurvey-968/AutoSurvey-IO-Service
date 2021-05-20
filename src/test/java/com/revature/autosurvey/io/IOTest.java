package com.revature.autosurvey.io;

import com.intuit.karate.junit5.Karate;

public class IOTest {
	
	@Karate.Test
	Karate testSendEmail() {
		return Karate.run("sendemail").relativeTo(getClass());
	}
}
