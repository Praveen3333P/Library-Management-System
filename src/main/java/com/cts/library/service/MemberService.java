package com.cts.library.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.library.model.Member;
import com.cts.library.model.Role;
import com.cts.library.repository.MemberRepo;

import jakarta.persistence.EntityNotFoundException;


@Service
public class MemberService {
	
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Member registerMember(Member member) {
		if (memberRepo.existsByUsername(member.getUsername())) {
			throw new RuntimeException("Username already taken");
		}
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		return memberRepo.save(member);
	}
	
	public List<Member> getAllMembers(Member requestingUser){
		if(requestingUser.getRole() != Role.ADMIN) {
			throw new AccessDeniedException("Only Admins can view the members");
		}
		return memberRepo.findAll();
	}
	
	public Member getMemberById(long id) {
		return memberRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not Found" + id));
	}
	
	public void deleteMemberById(long id, Member requestingUser) {
		if(requestingUser.getRole() != Role.ADMIN) {
			throw new AccessDeniedException("Only Admins can delete the members");
		}
		Member member = getMemberById(id);
		memberRepo.delete(member);
	}
}
