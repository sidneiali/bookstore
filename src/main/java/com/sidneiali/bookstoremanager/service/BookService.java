package com.sidneiali.bookstoremanager.service;

import com.sidneiali.bookstoremanager.dto.BookDTO;
import com.sidneiali.bookstoremanager.dto.MessageResponseDTO;
import com.sidneiali.bookstoremanager.entity.Book;
import com.sidneiali.bookstoremanager.mapper.BookMapper;
import com.sidneiali.bookstoremanager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private BookRepository bookRepository;

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public MessageResponseDTO create(BookDTO bookDTO){
        Book bookToSave = bookMapper.toModel(bookDTO);

        Book savedBook = bookRepository.save(bookToSave);
        return MessageResponseDTO
                .builder()
                .message("Book created with ID " + savedBook.getId())
                .build();
    }
}
