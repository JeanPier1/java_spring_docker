package com.structure.book.expose.controller;

import com.structure.book.business.service.BookService;
import com.structure.book.expose.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;


    @GetMapping
    public ResponseEntity<List<BookDto.Response>> getAll() throws Exception {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto.Response> getBookById(@PathVariable UUID id) throws Exception {
        return new ResponseEntity<>( bookService.getBookById(id), HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<BookDto.Response> save(@RequestBody BookDto.Request request) throws Exception {
        return new ResponseEntity<>( bookService.save(request), HttpStatus.OK) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto.Response> update(@PathVariable UUID id, @RequestBody BookDto.Request request) throws Exception {
        return new ResponseEntity<>( bookService.update(id, request), HttpStatus.OK) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
