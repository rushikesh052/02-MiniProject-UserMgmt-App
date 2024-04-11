package com.rushi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rushi.entity.CityEntity;
import com.rushi.entity.StateEntity;

public interface StateRepo extends JpaRepository<StateEntity, Integer> {

	@Query(value="select * from state_master where country_id=?1",nativeQuery=true)
	List<StateEntity> findByCountryId(Integer countryId);
	
}
