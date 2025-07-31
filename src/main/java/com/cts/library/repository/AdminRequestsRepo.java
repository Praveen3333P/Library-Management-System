package com.cts.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.library.model.AdminRequests;

public interface AdminRequestsRepo extends JpaRepository<AdminRequests, Long> {
	
	List<AdminRequests> findAll();

}
