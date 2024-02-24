package com.example.webflux.twillioExample.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.example.webflux.twillioExample.Bean.PasswordResetRequestBean;
import com.example.webflux.twillioExample.Bean.PasswordResetResponseBean;
import com.example.webflux.twillioExample.Bean.OtpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webflux.twillioExample.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import reactor.core.publisher.Mono;

@Service
public class TwilioOTPService {

	@Autowired
	private TwilioConfig twilioConfig;
	
	Map<String,String> otpMap = new HashMap<>();
	
	public Mono<PasswordResetResponseBean> sendOTPForPasswordReset(PasswordResetRequestBean request){
		PasswordResetResponseBean response = null;
		try {
			PhoneNumber to = new PhoneNumber(request.getPhoneNumber());
			PhoneNumber from = new PhoneNumber(twilioConfig.getTrailNumber());
			String otp = generateOTP();
            String otpMessage = "Dear Customer , Your OTP is ##" + otp + "##. Use this Passcode to complete your transaction. Thank You.";
            Message message = Message.creator(to, from,otpMessage).create();
            otpMap.put(request.getUserName(), otp);
            response=new PasswordResetResponseBean(OtpStatus.DELIVERED,otpMessage);
		}catch(Exception ex) {
            response=new PasswordResetResponseBean(OtpStatus.FAILED,ex.getMessage());
		}
		
		return Mono.just(response);
	}
	
	public Mono<String> validateOTP(String userInputOtp,String userName){
		 if(userInputOtp.equals(otpMap.get(userName))){
			 otpMap.remove(userName,userInputOtp);
			 return Mono.just("Valid OTP please proceed with your Process !");
		 }
		 else {
	            return Mono.error(new IllegalArgumentException("Invalid otp please retry !"));
		 }
	}
	public String generateOTP() {
		return new DecimalFormat("000000").format(new Random().nextInt(99999));
	}
	
}
