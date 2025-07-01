package com.cts.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LibraryManagementProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementProjectApplication.class, args);
	}

}
