package com.rushi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Country_Master")
public class CountryEntity {
	
	@Id
	private Integer countryId;
	
	private String countryName;
	
	

}
