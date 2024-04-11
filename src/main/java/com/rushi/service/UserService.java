package com.rushi.service;

import java.util.Map;

import com.rushi.dto.LoginDto;
import com.rushi.dto.RegisterDto;
import com.rushi.dto.ResetPwdDto;
import com.rushi.dto.UserDto;

public interface UserService {
	
	public Map<Integer, String> getCountries();
	
	public Map<Integer, String> getStates(Integer cid);
	
	public Map<Integer, String> getCities(Integer sid);
	
	public UserDto getUser(String email);
	
	public boolean registerUser(RegisterDto regDto);
	
	public UserDto getUser(LoginDto loginDto);
	
	public boolean resetPwd(ResetPwdDto pwdDto);
	
	public String getQuote();
	
}
