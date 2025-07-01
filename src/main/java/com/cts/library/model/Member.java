package com.cts.library.model;

import java.time.LocalDate;

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
	
	private String username;
	private String password;
	private LocalDate membershipExpiryDate;
	private int borrowingLimit=2;
	
	@Enumerated(EnumType.STRING)
	private MembershipStatus membershipStatus = MembershipStatus.BASIC;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public boolean isMembershipActive() {
        return membershipExpiryDate != null && LocalDate.now().isBefore(membershipExpiryDate);
    }
	
	public Member() {
		// by default we are setting the role to member
		this.role = Role.MEMBER;
	}

	public Member(long memberId, String name, String email, String phone, String address, MembershipStatus membershipStatus,
			int borrowingLimit,String username, String password, Role role) {
		super();
		this.memberId = memberId;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.membershipStatus = membershipStatus;
		this.username = username;
		this.password = password;
		this.role = role;
		this.borrowingLimit=borrowingLimit;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public MembershipStatus getMembershipStatus() {
	    if (membershipExpiryDate == null || LocalDate.now().isAfter(membershipExpiryDate)) {
	        return MembershipStatus.EXPIRED;
	    }
	    return membershipStatus;
	}
	
	public void setMembershipStatus(MembershipStatus status) {
	    if (this.membershipStatus != MembershipStatus.PRIME || status == MembershipStatus.EXPIRED) {
	        this.membershipStatus = status;
	    }
	}

	public LocalDate getMembershipExpiryDate() {
		return membershipExpiryDate;
	}

	public void setMembershipExpiryDate(LocalDate membershipExpiryDate) {
		this.membershipExpiryDate = membershipExpiryDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		
		// we are making sure member is not changing into admin
		if (this.role != Role.ADMIN) {
            this.role = role;
        }
	}

	public int getBorrowingLimit() {
		return borrowingLimit;
	}

	public void setBorrowingLimit(int borrowingLimit) {
		this.borrowingLimit = borrowingLimit;
	}
	
	
	
	
	
	

}
