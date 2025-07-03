package com.cts.library.controller;

import com.cts.library.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;
    

    /**
     * 💳 Pay a specific fine by ID.
     */
    @PostMapping("/pay/{fineId}")
    public ResponseEntity<String> payFine(@PathVariable Long fineId) {
        fineService.payFine(fineId);
        return ResponseEntity.ok("Fine paid successfully.");
    }

    /**
     * 📄 Get all fines for a specific member.
     */
//    @GetMapping("/member/{memberId}")
//    public ResponseEntity<List<Fine>> getFinesByMember(@PathVariable Long memberId) {
//        List<Fine> fines = fineService.getFinesByMemberId(memberId);
//        return ResponseEntity.ok(fines);
//    }
}
