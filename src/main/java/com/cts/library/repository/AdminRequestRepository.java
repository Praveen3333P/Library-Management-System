package com.cts.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.library.model.Admin_Requests;

public interface AdminRequestRepository extends JpaRepository<Admin_Requests, Long> {
	
	   @Query("SELECT ar FROM Admin_Requests ar")
	    List<Admin_Requests> findAllAdminRequests();

}
