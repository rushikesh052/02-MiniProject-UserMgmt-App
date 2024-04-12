package com.rushi.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="USER_DTLS")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	private String name; 
	
	private String email;
	
	private String pwd;
	
	private String pwdUpdated;
	
	private Long phno;
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private CountryEntity country; 
	
	@ManyToOne
	@JoinColumn(name="state_id")
	private StateEntity state; 
	
	@ManyToOne
	@JoinColumn(name="city_id")
	private CityEntity city; 
	
	
	@CreationTimestamp
	private LocalDateTime createDate;
	
	@UpdateTimestamp
	private LocalDateTime updateDate;
	

}
