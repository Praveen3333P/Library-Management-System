package com.cts.library.controller;

import com.cts.library.model.Fine;
import com.cts.library.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fine")
public class FineController {

    private FineService fineService;


	public FineController(FineService fineService) {
		super();
		this.fineService = fineService;
	}

	@GetMapping("/member/{memberId}")
    public List<Fine> getFinesByMember(@PathVariable Long memberId) {
        return fineService.getFinesByMemberId(memberId);
    }

    @PutMapping("/{fineId}/pay")
    public Fine payFine(@PathVariable Long fineId) {
        return fineService.payFine(fineId);
    }
}
