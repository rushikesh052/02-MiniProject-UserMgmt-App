package com.rushi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rushi.entity.CountryEntity;

public interface CountryRepo extends JpaRepository<CountryEntity, Integer> {
	
}
