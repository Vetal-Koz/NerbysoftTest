package com.example.nerdysoft_java_test.dto.response;

import com.example.nerdysoft_java_test.entity.data.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTitleAndAmountResponse extends ApiResponse {
    private String title;
    private Integer amountOfBorrowing;

    public BookTitleAndAmountResponse(Book book) {
        setId(book.getId());
        this.title = book.getTitle();
        this.amountOfBorrowing = book.getMember().size();
    }
}
