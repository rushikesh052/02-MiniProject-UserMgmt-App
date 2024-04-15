package com.rushi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rushi.dto.LoginDto;
import com.rushi.dto.QuoteDto;
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
import com.rushi.utils.EmailUtils;

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
	
	@Autowired
	private EmailUtils emailUtils;
	

	@Override
	public Map<Integer, String> getCountries() {
		Map<Integer, String> country=new HashMap<>();
		
//		CountryEntity countrie=new CountryEntity();
//		countrie.getCountryId();
//		
//		StateEntity entity=new StateEntity();
//		Example<StateEntity> of=Example.of(entity);
//		
//		List<StateEntity> stateList =stateRepo.findAll();
//		stateList.forEach(s->{
//			country.put(s.getStateId(), s.getStateName());
//		});
//		return stateList;
//	}
		
		List<CountryEntity> countries=countryRepo.findAll();
		countries.forEach(c->{
			country.put(c.getCountryId(), c.getCountryName());
		});
		return country;
	}
	@Override
	public Map<Integer, String> getStates(Integer cid) {
		Map<Integer, String> state=new HashMap<>();
		List<StateEntity> states=stateRepo.findByCountryId(cid);
//		Map<Integer, String> state=states.stream().collect(Collectors.toMap(StateEntity::getStateId, StateEntity::getStateName));
		states.forEach(s->{
			state.put(s.getStateId(),s.getStateName());
		});
		return state;

	}

	@Override
	public Map<Integer, String> getCities(Integer sid) {
		Map<Integer, String> cities=new HashMap<>();
		List<CityEntity> city=cityRepo.findByStateId(sid);
//		Map<Integer, String> cities=
//				cityAll.stream().collect(Collectors.toMap(CityEntity::getCityId, CityEntity::getCityName));
		city.forEach(c->{
			cities.put(c.getCityId(),c.getCityName());
		});
		return cities;
	}

	@Override
	public UserDto getUser(String email) {
			UserEntity userEntity=userRepo.findByEmail(email);
			
			if (userEntity == null) {
		       
		        return null;
		    }
		   /*
			UserDto dto=new UserDto();
			BeanUtils.copyProperties(userEntity, dto);
			*/
			//using third party dependency
			
			ModelMapper mapper=new ModelMapper();
			UserDto userDto=mapper.map(userEntity,UserDto.class);
			
		return userDto;
	}

	@Override
	public boolean registerUser(RegisterDto regDto) {
		

		ModelMapper mapper=new ModelMapper();
		
		UserEntity entity=mapper.map(regDto,UserEntity.class);
		
		CountryEntity country= countryRepo.findById(regDto.getCountryId()).orElseThrow();
		
		StateEntity state=stateRepo.findById(regDto.getStateId()).orElseThrow();
		
		CityEntity city=cityRepo.findById(regDto.getCityId()).orElseThrow();
		
		 entity.setCountry(country);
		 entity.setState(state);
		 entity.setCity(city);
		 entity.setPwd(generateRandom());
		 entity.setPwdUpdated("no");
		 
		 UserEntity savedEntity =userRepo.save(entity);
		 String subject="user Registration";
		 
		 String body="Your temporary PWD is :: "+ entity.getPwd();
		 
		 emailUtils.sendEmail(regDto.getEmail(),subject , body);
		 
		 return savedEntity.getUserId()!=null;
		 
		
		
	}
	@Override
	public UserDto getUser(LoginDto loginDto) {
	UserEntity userEntity=	userRepo.findByEmailAndPwd(loginDto.getEmail(),loginDto.getPwd());
	if(userEntity==null) {
		return null;
	}
	
	ModelMapper mapper=new ModelMapper();
	return mapper.map(userEntity, UserDto.class);
	}
		
	@Override
	public boolean resetPwd(ResetPwdDto pwdDto) {
		UserEntity userEntity=userRepo.findByEmail(pwdDto.getEmail());
		if(userEntity!=null) {
		userEntity.setPwd(pwdDto.getNewPwd());
		userEntity.setPwdUpdated("YES");
		userRepo.save(userEntity);
		return true;
		}
		return false;
	}

	@Override
	public String getQuote() {
		
		QuoteDto[] quotations=null;
		String url="https://type.fit/api/quotes";
		RestTemplate rt=new RestTemplate();
		ResponseEntity<String> forEntity=rt.getForEntity(url,String.class);
		String responseBody=forEntity.getBody();
		ObjectMapper mapper=new ObjectMapper();
		try {
			quotations =mapper.readValue(responseBody, QuoteDto[].class);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		Random r=new Random();
		int index=r.nextInt(quotations.length-1);
		return quotations[index].getText();
	}
	
	private static String generateRandom() {
		
		String aToZ ="ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		
		Random rand=new Random();
		StringBuilder res=new StringBuilder();
		for(int i=0; i<5; i++)
		{
			int randIndex=rand.nextInt(aToZ.length());
			res.append(aToZ.charAt(randIndex));
		}
		return res.toString();
		
	}
	
	

}
