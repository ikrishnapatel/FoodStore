package org.example.bookstore.Repository;

import org.example.bookstore.BookDao.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
