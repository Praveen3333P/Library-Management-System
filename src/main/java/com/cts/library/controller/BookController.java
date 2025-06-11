package com.cts.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.library.model.Book;
import com.cts.library.service.BookService;


@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping("/create/book")
	public Book createBook(@RequestBody Book book) {
		return bookService.addBook(book);
	}
	
	@GetMapping("/books")
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
	
	@GetMapping("/books/{id}")
	public Book getBook(@PathVariable Long id) {
		return bookService.getBookById(id);
	}
	
	@GetMapping("/books/title")
	public List<Book> searchTitle(@RequestParam String title) {
		return bookService.searchByTitle(title);
	}
	@GetMapping("/books/genre")
	public List<Book> searchGenre(@RequestParam String genre) {
		return bookService.searchByGenre(genre);
	}
	@GetMapping("/books/author")
	public List<Book> getBookName(@RequestParam String author) {
		return bookService.searchByAuthor(author);
	}
}
