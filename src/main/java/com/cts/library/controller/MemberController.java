package com.cts.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.library.model.Member;
import com.cts.library.service.MemberService;

@RestController
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/register/member")
	public Member registeringMember(@RequestBody Member member) {
		return memberService.registerMember(member);
	}
	
	@GetMapping("/register/allmembers")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Member> getAllMember(){
		return memberService.getAllMembers();
	}
	
	@DeleteMapping("/member/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteMember(@PathVariable long id) {
		memberService.deleteMemberById(id);
	}
	
	
}
