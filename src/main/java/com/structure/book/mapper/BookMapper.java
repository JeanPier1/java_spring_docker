package com.structure.book.mapper;


import com.structure.book.dao.entity.Book;
import com.structure.book.expose.dto.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface BookMapper {

    List<BookDto.Response> toResponseList(List<Book> books);
    BookDto.Response toResponse(Book book);
    Book toEntity(BookDto.Request request);
    void updateEntityFromRequest(BookDto.Request request, @MappingTarget Book book);
}
