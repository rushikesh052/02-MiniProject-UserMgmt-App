package com.rushi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="STATE_MASTER")
public class StateEntity {
	
	@Id
	private Integer stateId;
	private String stateName;
	
	@ManyToOne
	@JoinColumn(name="country_Id")
	private CountryEntity country;

}
