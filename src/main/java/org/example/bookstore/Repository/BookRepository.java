package org.example.bookstore.Repository;

import org.example.bookstore.BookDao.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
