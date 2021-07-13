package com.sidneiali.bookstoremanager.repository;

import com.sidneiali.bookstoremanager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
