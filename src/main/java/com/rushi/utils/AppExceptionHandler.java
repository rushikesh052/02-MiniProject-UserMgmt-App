package com.rushi.utils;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
	
	@ExceptionHandler(value=Exception.class)
	public String handleException(Exception e) {
		return "error";
	}

}
