package com.example.webflux.twillioExample.Bean;

import lombok.Data;

@Data
public class PasswordResetRequestBean {

	private String phoneNumber;
	private String userName;
	private String oneTimePassword;
}
