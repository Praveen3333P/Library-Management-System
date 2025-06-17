package com.cts.library.service;

import java.util.List;

import com.cts.library.model.Member;

public interface MemberService {
		
	public Member registerMember(Member member);
	public List<Member> getAllMembers();
	public Member getMemberById(long id);
	public void deleteMemberById(long id);
	
}
