package com.structure.book.expose.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class BookDto {

    @Getter
    @Setter
    public static class Response{
        public UUID id;
        public String name;
        public String code;
        public String ipsCode;
        public Boolean state;
    }

    @Getter
    @Setter
    public static class Request {
        public String name;
        public String code;
        public String ipsCode;
        public Boolean state;
    }

}
