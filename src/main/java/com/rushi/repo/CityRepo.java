package com.rushi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rushi.entity.CityEntity;

public interface CityRepo extends JpaRepository<CityEntity, Integer> {

	@Query(value="select * from city_master where state_id=?1",nativeQuery=true)
	List<CityEntity> findByStateId(Integer stateId);
}
