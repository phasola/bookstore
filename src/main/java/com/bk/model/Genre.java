package com.bk.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Andrei Petraru
 * Mar 15, 2013
 */

@Entity
@Table(name = "GENRE")
public class Genre extends AbstractEntity {
	private static final long serialVersionUID = 2428536031281203658L;

	@Column(nullable = false, unique = true)
	private String bookGenre;

	@ManyToMany(mappedBy = "genres")
	private Set<Book> books = new HashSet<>();

	public String getGenre() {
		return bookGenre;
	}

	public void setGenre(String genre) {
		this.bookGenre = genre;
	}

	public void addBook(Book book) {
		this.books.add(book);
	}

	public Set<Book> getBooks() {
		return Collections.unmodifiableSet(books);
	}
}
