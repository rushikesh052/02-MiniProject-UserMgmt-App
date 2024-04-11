package com.rushi.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rushi.dto.LoginDto;
import com.rushi.dto.RegisterDto;
import com.rushi.dto.ResetPwdDto;
import com.rushi.dto.UserDto;
import com.rushi.entity.CityEntity;
import com.rushi.entity.CountryEntity;
import com.rushi.entity.StateEntity;
import com.rushi.entity.UserEntity;
import com.rushi.repo.CityRepo;
import com.rushi.repo.CountryRepo;
import com.rushi.repo.StateRepo;
import com.rushi.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private StateRepo stateRepo;
	
	@Autowired
	private CityRepo cityRepo;
	

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryEntity> countries=countryRepo.findAll();
		Map<Integer,String>country= countries.stream().collect(Collectors.toMap(CountryEntity::getCountryId,CountryEntity::getCountryName));
		return country;
	}
	
	
	
	
	@Override
	public Map<Integer, String> getStates(Integer cid) {
		List<StateEntity> states=stateRepo.findByCountryId(cid);
		Map<Integer, String> state=states.stream().collect(Collectors.toMap(StateEntity::getStateId, StateEntity::getStateName));
		return state;

	}

	@Override
	public Map<Integer, String> getCities(Integer sid) {
		List<CityEntity> cityAll=cityRepo.findByStateId(sid);
		Map<Integer, String> cities=
				cityAll.stream().collect(Collectors.toMap(CityEntity::getCityId, CityEntity::getCityName));
		
		return cities;
	}

	@Override
	public UserDto getUser(String email) {
			UserDto userDto=userRepo.findByEmail(email);
		return userDto;
	}

	@Override
	public boolean registerUser(RegisterDto regDto) {
		if(regDto==null || regDto.getEmail()==null || regDto.getPwd()==null) {
		return false;
	}
	
	if(userRepo.existsByEmail(regDto.getEmail())) {
		return false;
	}
	
	UserEntity user=new UserEntity();
	user.setEmail(regDto.getEmail());
	user.setPwdUpdated(regDto.getPwd());
	try {
		userRepo.save(user);
		return true;
	}catch(Exception e) {
		e.printStackTrace();
		return false;
	}
	}

	@Override
	public UserDto getUser(LoginDto loginDto) {
		if(loginDto==null || loginDto.getEmail()==null || loginDto.getPwd()==null) {
			return null;
		}
		UserEntity user=new UserEntity();
		
		if(user !=null && passwordMatches(user.getPwdUpdated(),loginDto.getPwd())){
			UserDto userDto=new  UserDto();
			userDto.setUserId(user.getUserId());
			userDto.setEmail(user.getEmail());
			return userDto;
		}else {
			return null;
		}
	}
	public boolean passwordMatches(String password, String plainPassword) {
		
		return password.equals(plainPassword);
	}
		
	@Override
	public boolean resetPwd(ResetPwdDto pwdDto) {
		
		return false;
	}

	@Override
	public String getQuote() {
		
		return null;
	}
	
	

}
