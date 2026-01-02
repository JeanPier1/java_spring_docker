package com.structure.book.business.impl;

import com.structure.book.business.service.BookService;
import com.structure.book.dao.entity.Book;
import com.structure.book.dao.repository.BookRepository;
import com.structure.book.expose.dto.BookDto;
import com.structure.book.mapper.BookMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public List<BookDto.Response> getAll() throws Exception {
        return bookMapper.toResponseList(bookRepository.findAll());
    }

    @Override
    public BookDto.Response getBookById(UUID id) throws Exception {
        return  bookMapper.toResponse(bookRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public BookDto.Response save(BookDto.Request request) throws Exception {
        return  bookMapper.toResponse(bookRepository.save(bookMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public BookDto.Response update(UUID id, BookDto.Request request) throws Exception {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()) {
            throw new Exception("Book not found");
        }

        bookMapper.updateEntityFromRequest(request, optionalBook.get());
        bookRepository.save(optionalBook.get());

        return bookMapper.toResponse(optionalBook.get());
    }

    @Override
    @Transactional
    public void delete(UUID id) throws Exception {
        if (!bookRepository.existsById(id)) {
            throw new Exception("Book not found");
        }
        bookRepository.deleteById(id);
    }
}
