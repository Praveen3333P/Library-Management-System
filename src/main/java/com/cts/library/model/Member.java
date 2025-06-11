package com.cts.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long memberId;
	private String name;
	private String email;
	private String phone;
	private String address;
	private String membershipStatus;
	
	private String username;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;

}
