package com.rushi.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import com.rushi.service.UserService;
@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private UserService service;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		System.out.println(service.getCountries());

		
		System.out.println(service.getStates(1));
		
//		System.out.println(service.getCities(2));
	
	}

}
