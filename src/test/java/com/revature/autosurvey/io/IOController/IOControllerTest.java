package com.revature.autosurvey.io.IOController;

import com.intuit.karate.junit5.Karate;

public class IOControllerTest {

	@Karate.Test
	Karate testSendEmail() {
		return Karate.run("sendemailcontroller").relativeTo(getClass());
	}
}