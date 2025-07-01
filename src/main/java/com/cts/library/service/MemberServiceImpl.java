package com.cts.library.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.library.model.Member;
import com.cts.library.model.MembershipStatus;
import com.cts.library.model.Role;
import com.cts.library.repository.MemberRepo;

import jakarta.persistence.EntityNotFoundException;


@Service
public class MemberServiceImpl implements MemberService{
	
	private MemberRepo memberRepo;
	

//	private PasswordEncoder passwordEncoder;
	
	public MemberServiceImpl(MemberRepo memberRepo) {
		super();
		this.memberRepo = memberRepo;
	}

	public Member createAdmin(Member adminMember) {
        adminMember.setRole(Role.ADMIN);
        adminMember.setPassword(adminMember.getPassword());
//        adminMember.setPassword(passwordEncoder.encode(adminMember.getPassword()));
        return memberRepo.save(adminMember);
    }

	public Member registerMember(Member member) {
		if (memberRepo.existsByUsername(member.getUsername())) {
			throw new RuntimeException("Username already taken");
		}
		member.setRole(Role.MEMBER);
        member.setPassword(member.getPassword());

//		member.setPassword(passwordEncoder.encode(member.getPassword()));
		return memberRepo.save(member);
	}
	
	public Member updateMember(Long id, Member updated) {
        Member existing = getMemberById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        return memberRepo.save(existing);
    }
	
	public void deleteMemberById(Long id) {
		Member exist = getMemberById(id);
		memberRepo.delete(exist);
	}
	
	public List<Member> getAllMembers(){
		return memberRepo.findAll();
	}
	
	public Member getMemberById(Long id) {
		return memberRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not Found" + id));
	}
	
	public Member activateMembership(Long memberId, int monthsToExtend) {
		Member exist = getMemberById(memberId);
		
		exist.setMembershipStatus(MembershipStatus.PRIME);
		LocalDate newExpiryDate = (exist.getMembershipExpiryDate() == null) ?
				LocalDate.now().plusMonths(monthsToExtend) :
		            exist.getMembershipExpiryDate().plusMonths(monthsToExtend);
		
		exist.setMembershipExpiryDate(newExpiryDate);
		
		return memberRepo.save(exist);	
	}
	
	public void updateMembershipStatus(Member member) {
	    if (member.getMembershipExpiryDate() != null && LocalDate.now().isAfter(member.getMembershipExpiryDate())) {
	        member.setMembershipStatus(MembershipStatus.EXPIRED);
	    }
	}

	
}
