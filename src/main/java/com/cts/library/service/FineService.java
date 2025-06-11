package com.cts.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.library.model.Fine;
import com.cts.library.repository.FineRepo;


@Service
public class FineService {

	@Autowired
	private FineRepo fineRepo;
	
	public Fine saveFineDetails(@RequestBody Fine fine) {
		return fineRepo.save(fine);
	}
}
