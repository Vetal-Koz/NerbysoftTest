package com.example.nerdysoft_java_test.dto.response;

import com.example.nerdysoft_java_test.entity.data.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTitleResponse extends ApiResponse {
    private String title;

    public BookTitleResponse(Book book) {
        setId(book.getId());
        this.title = book.getTitle();
    }
}
