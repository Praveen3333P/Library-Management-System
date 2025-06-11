package com.cts.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.library.model.Fine;


@Repository
public interface FineRepo extends JpaRepository<Fine, Long>{
	
}
