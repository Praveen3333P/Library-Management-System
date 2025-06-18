package com.cts.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/allmembers")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Member> getAllMember(){
		return memberService.getAllMembers();
	}
	
	@PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')") // we are making sure that only admins can create other admins
    public Member createAdmin(@RequestBody Member member) {
        return memberService.createAdmin(member);
    }
	
	@DeleteMapping("/member/{id}")
	@PreAuthorize("hasRole('ADMIN')") // we are giving admin access to delete 
	public void deleteMember(@PathVariable Long id) {
		memberService.deleteMemberById(id);
	}
	
	@PutMapping("/member/{id}")
	@PreAuthorize("hasRole('MEMBER')")
	public Member updateMember(@PathVariable Long id,@RequestBody Member member) {
		return memberService.updateMember(id,member);
	}
	
	@GetMapping("/member/{id}")
	public Member getMemberProfile(@PathVariable Long id) {
	    Member member = memberService.getMemberById(id);
	    
	    // Automatically update membership status before returning profile
	    memberService.updateMembershipStatus(member);

	    return member;
	}

	
	@PutMapping("/member/{id}/activate")
	@PreAuthorize("hasRole('MEMBER')")
	public Member activateMembership(@PathVariable Long id, @RequestParam int months) {
	    return memberService.activateMembership(id, months);
	}

	
}
