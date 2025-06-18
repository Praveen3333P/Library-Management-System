package com.cts.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.cts.library.model.Book;
import com.cts.library.repository.BookRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookServiceImpl implements BookService{

	
	@Autowired
	private BookRepo bookRepo;
	
	public Book addBook(Book book) {
		return bookRepo.save(book);
	}
	
	public void deleteBook(Long id) {
		Book exist = getBookById(id);
		bookRepo.delete(exist);
	}
	
	public String updateBook(Long id, Book updated) {
		Book exist = getBookById(id);
		exist.setBookId(updated.getBookId());
		exist.setBookName(updated.getBookName());
		exist.setGenre(updated.getGenre());
		exist.setISBN(updated.getISBN());
		exist.setAuthor(updated.getAuthor());
		exist.setAvailableCopies(updated.getAvailableCopies());
		exist.setYearPublished(updated.getYearPublished());
		
		return "Book Updated Succefully";
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
	
	
	
	
	
}
