package com.example.webflux.twillioExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.webflux.twillioExample.config.TwilioConfig;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class TwillioExampleApplication {

	@Autowired
	private TwilioConfig twilioConfig;
	
	@PostConstruct
	public void initTwilio() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}
	public static void main(String[] args) {
		SpringApplication.run(TwillioExampleApplication.class, args);
	}

}
