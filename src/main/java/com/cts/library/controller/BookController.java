package com.cts.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.library.model.Book;
import com.cts.library.service.BookService;


@RestController
@Validated
public class BookController {
	
	
	private BookService bookService;

	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}

	@PostMapping("/books/add")
	public Book addBook(@RequestBody Book book) {
		return bookService.addBook(book);
	}
	
	@DeleteMapping("/books/{id}")
	public void deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
	}
	
	@PutMapping("/books/{id}")
	public String updateBook(@PathVariable Long id, Book book) {
		String result = bookService.updateBook(id,book);
		return result;
	}
	
	@GetMapping("/books")
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
	
	@GetMapping("/books/{id}") 
	public Book getBookById(@PathVariable Long id) {
		return bookService.getBookById(id);
	}
	
	@GetMapping("/search/title")
	public List<Book> searchByTitle(@RequestParam String title) {
		return bookService.searchByTitle(title);
	}
	@GetMapping("/search/genre")
	public List<Book> searchByGenre(@RequestParam String genre) {
		return bookService.searchByGenre(genre);
	}
	@GetMapping("/search/author")
	public List<Book> searchByAuthor(@RequestParam String author) {
		return bookService.searchByAuthor(author);
	}
	
}
