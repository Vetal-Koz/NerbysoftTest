package com.example.nerdysoft_java_test.service;

import com.example.nerdysoft_java_test.entity.data.Book;

import java.util.List;

public interface BookService extends CrudService<Book> {
    List<Book> findAllBorrowedDistinctByTitle();

    List<Book> findAllByMemberName(String memberName);
}
