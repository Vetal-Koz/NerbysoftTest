package com.example.nerdysoft_java_test.facade;

import com.example.nerdysoft_java_test.dto.request.BookRequest;
import com.example.nerdysoft_java_test.dto.response.BookResponse;
import com.example.nerdysoft_java_test.dto.response.BookTitleAndAmountResponse;
import com.example.nerdysoft_java_test.dto.response.BookTitleResponse;
import com.example.nerdysoft_java_test.entity.data.Book;

import java.util.List;

public interface BookFacade extends CrudFacade<BookRequest, BookResponse> {
    List<BookTitleResponse> findAllDistinctBorrowedTitles();

    List<BookTitleAndAmountResponse> findAllDistinctTitlesWithAmountOfBorrowing();

    List<BookResponse> findAllByMemberName(String memberName);

}
