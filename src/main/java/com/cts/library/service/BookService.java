package com.cts.library.service;

import java.util.List;

import com.cts.library.model.Book;


public interface BookService {
	

	public String addBook(Book book);
	public String deleteBook(Long id);
	public String updateBook(Long id, Book book);
	public Book getBookById(Long id);
	public List<Book> getAllBooks();
	public List<Book> searchByTitle(String title);
	public List<Book> searchByGenre(String genre);
	public List<Book> searchByAuthor(String author);

}
