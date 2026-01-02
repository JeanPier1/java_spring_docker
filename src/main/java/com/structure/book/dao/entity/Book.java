package com.structure.book.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "book_id")
    private UUID id;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private String ipsCode;

    @Column
    private  boolean state;


}
