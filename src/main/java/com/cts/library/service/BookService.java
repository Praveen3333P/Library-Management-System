package com.cts.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.cts.library.model.Book;
import com.cts.library.repository.BookRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService {

	
	@Autowired
	private BookRepo bookRepo;
	
	public Book addBook(Book book) {
		return bookRepo.save(book);
	}
	
	public List<Book> getAllBooks(){
		return bookRepo.findAll();
	}
	
	public Book getBookById(Long id) {
		return bookRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not Found" + id));
	}
	
	public List<Book> searchByTitle(String title) {
		return bookRepo.findByBookNameContainingIgnoreCase(title);
	}
	
	public List<Book> searchByGenre(String genre){
		return bookRepo.findByBookGenreIgnoreCase(genre);
	}
	
	public List<Book> searchByAuthor(String author){
		return bookRepo.findByBookAuthorContainingIgnoreCase(author);
	}
	
	public void deleteBook(Long id) {
		Book exist = getBookById(id);
		bookRepo.delete(exist);
	}
	
	
	
}
