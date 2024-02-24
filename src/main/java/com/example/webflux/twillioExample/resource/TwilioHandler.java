package com.example.webflux.twillioExample.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.example.webflux.twillioExample.Bean.PasswordResetRequestBean;
import com.example.webflux.twillioExample.Bean.PasswordResetResponseBean;
import com.example.webflux.twillioExample.Bean.OtpStatus;
import com.example.webflux.twillioExample.service.TwilioOTPService;

import reactor.core.publisher.Mono;

@Component
public class TwilioHandler {

	@Autowired
	private TwilioOTPService tservice;
	
	public Mono<ServerResponse> sendOTP(ServerRequest request){
		return request.bodyToMono(PasswordResetRequestBean.class)
				.flatMap(c->tservice.sendOTPForPasswordReset(c))
				.flatMap(c->ServerResponse.status(HttpStatus.OK)
				.body(BodyInserters.fromValue(c)));			
		
	}
	
	public Mono<ServerResponse> validateOTP(ServerRequest request){
		return request.bodyToMono(PasswordResetRequestBean.class)
				.flatMap(c->tservice.validateOTP(c.getOneTimePassword(),c.getUserName()))
				.flatMap(c->ServerResponse.status(HttpStatus.OK).bodyValue(c));
	}
}
