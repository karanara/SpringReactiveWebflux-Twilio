package com.example.webflux.twillioExample.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponseBean {

	private OtpStatus status;
	private String message;
}
