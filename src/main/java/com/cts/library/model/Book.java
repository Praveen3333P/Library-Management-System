package com.cts.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bookId;
	@NotBlank(message= "Book Name is required")
	private String bookName;
	@NotBlank(message="Book Author is required")
	private String author;
	@NotBlank(message="Book Genre is required")
	private String genre;
	@NotBlank(message="Book ISBN is required")
	private String ISBN;
	@Min(value=1000, message="Invalid Publication year" )
	private int yearPublished;
	@Min(value=0, message="Available copies cannot be negative")
	private int availableCopies;
}
