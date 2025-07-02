package com.cts.library.repository;

import com.cts.library.model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepo extends JpaRepository<Fine, Long> {
    
}
