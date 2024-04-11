package com.rushi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rushi.dto.UserDto;
import com.rushi.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer>{

	public UserDto findByEmail(String email);
	
	public boolean existsByEmail(String email);
}
