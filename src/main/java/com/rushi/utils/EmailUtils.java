package com.rushi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailUtils {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(String to, String subject, String body) {
		
		return true;
	}
	

}
