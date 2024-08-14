package com.example.nerdysoft_java_test.facade.impl;

import com.example.nerdysoft_java_test.dto.request.BookRequest;
import com.example.nerdysoft_java_test.dto.response.BookResponse;
import com.example.nerdysoft_java_test.dto.response.BookTitleAndAmountResponse;
import com.example.nerdysoft_java_test.dto.response.BookTitleResponse;
import com.example.nerdysoft_java_test.entity.data.Book;
import com.example.nerdysoft_java_test.facade.BookFacade;
import com.example.nerdysoft_java_test.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookFacadeImpl implements BookFacade {
    private final BookService bookService;

    @Override
    public void create(BookRequest entity) {
        Book book = new Book();
        book.setTitle(entity.getTitle());
        book.setAuthor(entity.getAuthor());
        bookService.create(book);
    }

    @Override
    public void update(Long id, BookRequest entity) {
        Book book = bookService.findById(id);
        book.setTitle(entity.getTitle());
        book.setAuthor(entity.getAuthor());
        bookService.update(book);
    }

    @Override
    public void delete(Long id) {
        bookService.delete(id);
    }

    @Override
    public BookResponse findById(Long id) {
        return new BookResponse(bookService.findById(id));
    }

    @Override
    public List<BookResponse> findAll() {
        return bookService.findAll().stream().map(BookResponse::new).toList();
    }

    @Override
    public List<BookTitleResponse> findAllDistinctBorrowedTitles() {
        return bookService.findAllBorrowedDistinctByTitle().stream().map(BookTitleResponse::new).toList();
    }

    @Override
    public List<BookTitleAndAmountResponse> findAllDistinctTitlesWithAmountOfBorrowing() {
        return bookService.findAllBorrowedDistinctByTitle().stream().map(BookTitleAndAmountResponse::new).toList();
    }

    @Override
    public List<BookResponse> findAllByMemberName(String memberName) {
        return bookService.findAllByMemberName(memberName).stream().map(BookResponse::new).toList();
    }
}
