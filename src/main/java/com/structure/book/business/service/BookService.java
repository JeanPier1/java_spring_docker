package com.structure.book.business.service;


import com.structure.book.expose.dto.BookDto;

import java.util.List;
import java.util.UUID;

public interface BookService {

    List<BookDto.Response> getAll() throws Exception;

    BookDto.Response getBookById(UUID id) throws Exception;

    BookDto.Response save(BookDto.Request request) throws Exception;

    BookDto.Response update(UUID id, BookDto.Request request) throws Exception;

    void delete(UUID id) throws Exception;

}
