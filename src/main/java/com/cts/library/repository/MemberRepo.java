package com.cts.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.library.model.Member;


@Repository
public interface MemberRepo extends JpaRepository<Member, Long>{
	
	Member findByUsername(String username);
	boolean existsByUsername(String username);
		
}
