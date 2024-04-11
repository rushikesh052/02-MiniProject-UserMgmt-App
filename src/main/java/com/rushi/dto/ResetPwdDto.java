package com.rushi.dto;

import lombok.Data;

@Data
public class ResetPwdDto {

	private String email; 
	
	private String OldPwd;  
	
	private String newPwd; 
	
	private String confirmPwd;
	
}
