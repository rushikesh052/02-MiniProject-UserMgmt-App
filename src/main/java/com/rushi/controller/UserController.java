package com.rushi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.rushi.dto.LoginDto;
import com.rushi.dto.RegisterDto;
import com.rushi.dto.ResetPwdDto;
import com.rushi.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("registerDto",new RegisterDto());
		return "registerView";
	}
	@GetMapping("/state/{cid}")
	public Map<Integer, String> getStates(@PathVariable("cid")Integer cid){
		
		return null;
	}
	@GetMapping("/city/{sid}")
	public Map<Integer, String> getCities(@PathVariable("sid") Integer sid){
		return null;
	}
	@PostMapping("/register")
	public String register(RegisterDto regDto,Model model) {
		if(regDto==null || regDto.getEmail()==null ||regDto.getPwd()==null) {
			model.addAttribute("error","Invalid registration data");
			return "registerView";
		}
		boolean isRegistered= userService.registerUser(regDto);
		if(isRegistered) {
			return "resetPwd";
		}
		else {
			model.addAttribute("error","User with this email already exists");
				return "registerView";
		}
	}
	
	@GetMapping("/")
	public String loginPage(Model model) {
		return "index";
	}
	@PostMapping("/login")
	public String login(LoginDto loginDto, Model model) {
		return "index";
	}
	
	@PostMapping("/resetPwd")
	public String resetPwd(ResetPwdDto pwdDto) {
		return "resetPwd";
	}
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		return "dashboard";
	}
	@GetMapping("/logout")
	public String logout() {
		return "index";
	}
}
