package com.cts.library.service;

import java.util.List;

import com.cts.library.model.Member;

public interface MemberService {
		
	public Member createAdmin(Member adminMember);
	public Member registerMember(Member member);
	public Member updateMember(Long id, Member member);
	public void deleteMemberById(Long id);
	public List<Member> getAllMembers();
	public Member getMemberById(Long id);
	public Member activateMembership(Long memberId, int monthsToExtend);
	public void updateMembershipStatus(Member member);
	
}
	