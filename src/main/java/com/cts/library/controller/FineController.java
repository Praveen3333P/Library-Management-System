package com.cts.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.library.model.Fine;
import com.cts.library.service.FineService;

@RestController
public class FineController {
	
	@Autowired
	private FineService fineService;
	
	@PostMapping("/create/fine")
	public Fine createFine(Fine fine) {
		return fineService.saveFineDetails(fine);
	}

}
