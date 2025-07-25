package com.cts.library.controller;

import com.cts.library.model.LoginDetails;
import com.cts.library.model.Member;
import com.cts.library.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@Validated
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/admin/admin-register")
    public ResponseEntity<String> createAdmin(@RequestBody Member admin) {
        return ResponseEntity.ok(memberService.createAdmin(admin));
    }
    @GetMapping("/admin/allmembers")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/admin/get-member/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }
    
    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> loginMember(@RequestBody LoginDetails loginDetails) {

    	HashMap<String, String> response = memberService.loginMember(loginDetails);
        return ResponseEntity.ok(response); 
    }



    @PostMapping("/member/register")
    public ResponseEntity<String> registerMember(@RequestBody Member member) {
    	System.out.println("yeah");
        return ResponseEntity.ok(memberService.registerMember(member));
    }

    @GetMapping("/member/{id}/profile")
    public ResponseEntity<Member> getMemberProfile(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        memberService.updateMembershipStatus(member);
        return ResponseEntity.ok(member);
    }

    @PutMapping("/member/{id}/update")
    public ResponseEntity<String> updateMember(@PathVariable Long id,@RequestBody Member member) {

        return ResponseEntity.ok(memberService.updateMember(id, member));
    }
    @PutMapping("/member/{id}/update-password")
    public ResponseEntity<String> updateMemberPassword(@PathVariable Long id,
    		@RequestBody String plainText) {
    	return ResponseEntity.ok(memberService.updatePassword(id, plainText));
    }

    @DeleteMapping("/member/{id}/delete")

    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.deleteMemberById(id));
    }

    @PutMapping("/member/{id}/activate")
    public ResponseEntity<String> activateMembership(@PathVariable Long id,
                                                     @RequestParam int months) {
        return ResponseEntity.ok(memberService.activateMembership(id, months));
    }
}
