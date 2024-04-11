package com.rushi.dto;

import lombok.Data;

@Data
public class UserDto {
	
	private Integer userId; 
	
	private String name; 
	
	private String email;
	
	private String phno; 
	
	private String pwd; 
	
	private String pwdUpdated; 
	
	private String newPwd; 
	
	private String confirmPwd; 
	
	private Integer countryId;
	
	private Integer stateId; 
	
	private Integer cityId; 
	
	

}
