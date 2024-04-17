package com.rushi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rushi.dto.LoginDto;
import com.rushi.dto.RegisterDto;
import com.rushi.dto.ResetPwdDto;
import com.rushi.dto.UserDto;
import com.rushi.service.UserService;
import com.rushi.utils.AppConstants;
import com.rushi.utils.AppProperties;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppProperties prop;
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("registerDto",new RegisterDto());
		 Map<Integer, String> countriesMap = userService.getCountries();
		 model.addAttribute("countries",countriesMap);
		 return AppConstants.REGISTER_PAGE;
	}
	
	@GetMapping("/state/{cid}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable("cid")Integer cid){
		return userService.getStates(cid);
	}
	
	@GetMapping("/city/{sid}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable("sid") Integer sid){
		return userService.getCities(sid);
	}
	
	
	@PostMapping("/register")
	public String register(RegisterDto regDto,Model model) {
			
			model.addAttribute("countries",userService.getCountries());
			
			Map<String, String> messages=prop.getMessages();
		
			UserDto user=userService.getUser(regDto.getEmail());
			
			if(user!=null){
				model.addAttribute(AppConstants.ERROR_MSG,messages.get(AppConstants.DUPLICATE_EMAIL));
				return AppConstants.REGISTER_PAGE;
			}
			boolean registerUser=userService.registerUser(regDto);
			
			if(registerUser) {
				model.addAttribute(AppConstants.SUCCESS_MSG, messages.get(AppConstants.REG_SUCCESS));
			}
			else {
				model.addAttribute(AppConstants.ERROR_MSG,messages.get(AppConstants.REG_FAIL));
			}
			return AppConstants.REGISTER_PAGE;
	}
	
	@GetMapping("/")
	public String loginPage(Model model) {
		
		model.addAttribute("loginDto",new LoginDto());
		return "index";
	}
	@PostMapping("/login")
	public String login(LoginDto loginDto, Model model) {
		
		Map<String, String> messages=prop.getMessages();
		
		UserDto user=userService.getUser(loginDto);
		if(user == null) {
			model.addAttribute(AppConstants.ERROR_MSG,messages.get(AppConstants.INVALD_CRED));
			return "index";
		}
		if("YES".equals(user.getPwdUpdated())) {
			return "redirect:dashboard";
		}else {
			ResetPwdDto resetPwdDto=new ResetPwdDto();
			resetPwdDto.setEmail(user.getEmail());
			model.addAttribute("resetPwdDto",resetPwdDto);
			return AppConstants.RESETPWD_PAGE;
		}
	}
	
	@PostMapping("/resetPwd")
	public String resetPwd(ResetPwdDto pwdDto, Model model) {
		
		Map<String, String> messages=prop.getMessages();
		
		if(!(pwdDto.getNewPwd().equals(pwdDto.getConfirmPwd()))) {
			model.addAttribute(AppConstants.ERROR_MSG,messages.get(AppConstants.PWDMATCH_ERR));
			return AppConstants.RESETPWD_PAGE;
		}
			UserDto user=userService.getUser(pwdDto.getEmail());
		if(user.getPwd().equals(pwdDto.getOldPwd())) {
			boolean resetPwd = userService.resetPwd(pwdDto);
			if(resetPwd) {
				return "redirect:dashboard";
			}else {
				model.addAttribute(AppConstants.ERROR_MSG,messages.get(AppConstants.PWDUPDATE_ERR));
				return AppConstants.RESETPWD_PAGE;
			}
		}else {
			model.addAttribute(AppConstants.ERROR_MSG,messages.get(AppConstants.INCORRECTOLDPWD_ERR));
			return AppConstants.RESETPWD_PAGE;
		}
	}
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		String quote=userService.getQuote();
		model.addAttribute("quote",quote);
		return "dashboardView";
	}
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/";
	}
}
