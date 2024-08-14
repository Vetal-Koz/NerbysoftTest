package com.example.nerdysoft_java_test.dto.response;

import com.example.nerdysoft_java_test.entity.data.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponse extends ApiResponse {
    private String title;
    private String author;
    private Integer amount;

    public BookResponse(Book book) {
        setId(book.getId());
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.amount = book.getAmount();
    }
}
