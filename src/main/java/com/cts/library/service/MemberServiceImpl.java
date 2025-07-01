package com.cts.library.service;

import com.cts.library.exception.ResourceNotFoundException;
import com.cts.library.exception.UnauthorizedAccessException;
import com.cts.library.model.BorrowingTransaction;
import com.cts.library.model.LoginDetails;
import com.cts.library.model.Member;
import com.cts.library.model.MemberToken;
import com.cts.library.model.MembershipStatus;
import com.cts.library.model.Role;
import com.cts.library.repository.MemberRepo;
import com.cts.library.repository.MemberTokenRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberTokenRepo memberTokenRepo;

    private final MemberRepo memberRepo;

    public MemberServiceImpl(MemberRepo memberRepo, MemberTokenRepo memberTokenRepo) {
        this.memberRepo = memberRepo;
        this.memberTokenRepo = memberTokenRepo;
    }

    public String registerMember(Member member) {
        if (memberRepo.existsByUsername(member.getUsername())) {
            throw new RuntimeException("Username already taken.");
        }
        member.setRole(Role.MEMBER);
        BCryptPasswordEncoder encodedPassword = new BCryptPasswordEncoder();
        String hashedPassword = encodedPassword.encode(member.getPassword());
        member.setPassword(hashedPassword);
        memberRepo.save(member);
        return "Member registered successfully.";
    }

    public String createAdmin(Member newAdmin) {
        newAdmin.setRole(Role.ADMIN);
        BCryptPasswordEncoder encodedPassword = new BCryptPasswordEncoder();
        String hashedPassword = encodedPassword.encode(newAdmin.getPassword());
        newAdmin.setPassword(hashedPassword);
        memberRepo.save(newAdmin);
        return "Admin created successfully.";
    }

    public String updateMember(Long id, Member updated) {
        Member existing = getMemberById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        memberRepo.save(existing);
        return "Member profile updated.";
    }

    public String deleteMemberById(Long id) {
        Member member = getMemberById(id);
        memberRepo.delete(member);
        return "Member deleted successfully.";
    }

    public List<Member> getAllMembers() {
        return memberRepo.findAll();
    }

    public Member getMemberById(Long id) {
    	
    	
        return memberRepo.findById(id)
        		
            .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + id + " not found."));
//        
//        List<BorrowingTransaction> b= new ArrayList<>();
//    	
    }

    public String activateMembership(Long id, int months) {
        Member member = getMemberById(id);
        member.setMembershipStatus(MembershipStatus.PRIME);
        LocalDate newExpiry = (member.getMembershipExpiryDate() == null)
                ? LocalDate.now().plusMonths(months)
                : member.getMembershipExpiryDate().plusMonths(months);
        member.setMembershipExpiryDate(newExpiry);
        memberRepo.save(member);
        return "Membership activated until " + newExpiry + ".";
    }

    public void updateMembershipStatus(Member member) {
        if (member.getMembershipExpiryDate() != null &&
            LocalDate.now().isAfter(member.getMembershipExpiryDate())) {
            member.setMembershipStatus(MembershipStatus.EXPIRED);
            memberRepo.save(member);
        }
    }

    // Role-based Checks

    public void validateAdmin(Long requesterId) {
        Member requester = getMemberById(requesterId);
        if (requester.getRole() != Role.ADMIN) {
            throw new UnauthorizedAccessException("Only ADMINs can perform this action.");
        }
    }

    public void validateSameUser(Long requesterId, Long targetMemberId) {
        if (!requesterId.equals(targetMemberId)) {
            throw new UnauthorizedAccessException("You can only modify your own account.");
        }
    }


    public Member loginMember(LoginDetails loginDetails) {
        Member member = memberRepo.findByUsername(loginDetails.getUserName());
        if (member == null) {
            throw new UnauthorizedAccessException("Member Not Found");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(loginDetails.getUserPassword(), member.getPassword())) {
            throw new UnauthorizedAccessException("Invalid Credentials");
        }
        memberTokenRepo.findByMember(member).ifPresent(memberTokenRepo::delete);

        MemberToken memberToken = new MemberToken();
        memberToken.setMemberToken(UUID.randomUUID().toString());
        memberToken.setGeneratedAt(LocalDateTime.now());
        memberToken.setMember(member);
        memberTokenRepo.save(memberToken);

        return member;
    }

}
