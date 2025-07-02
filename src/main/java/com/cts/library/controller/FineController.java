package com.cts.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.library.service.FineService;

@RestController
@RequestMapping("fines")
public class FineController {

    @Autowired
    private FineService fineService;
    

    @PostMapping("/pay/{fineId}")
    public ResponseEntity<String> payFine(@PathVariable Long fineId) {
        fineService.payFine(fineId);
        return ResponseEntity.ok("Fine paid successfully.");
    }

}
