package com.example.nerdysoft_java_test.controller;

import com.example.nerdysoft_java_test.dto.request.BookRequest;
import com.example.nerdysoft_java_test.dto.response.BookResponse;
import com.example.nerdysoft_java_test.dto.response.BookTitleAndAmountResponse;
import com.example.nerdysoft_java_test.dto.response.BookTitleResponse;
import com.example.nerdysoft_java_test.dto.response.ResponseContainer;
import com.example.nerdysoft_java_test.facade.BookFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books API", description = "the books api that contains all methods related to books")
public class BookController {
    private final BookFacade bookFacade;

    @Operation(summary = "get all books as list",
            description = "return all books")
    @GetMapping
    private ResponseEntity<ResponseContainer<List<BookResponse>>> getAllBook() {
        return ResponseEntity.ok(new ResponseContainer<>(bookFacade.findAll()));
    }

    @Operation(summary = "get book by id",
            description = "return a book")
    @GetMapping("/{id}")
    private ResponseEntity<ResponseContainer<BookResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseContainer<>(bookFacade.findById(id)));
    }

    @Operation(summary = "create new instant of book",
            description = "create new book")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid BookRequest book) {
        bookFacade.create(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "update instant of book",
            description = "update a book")
    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid BookRequest book) {
        bookFacade.update(id, book);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "delete instant of Book",
            description = "delete a Book")
    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        bookFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "get all borrowed distinct book names",
            description = "return list of all borrowed distinct book names")
    @GetMapping("/titles")
    private ResponseEntity<ResponseContainer<List<BookTitleResponse>>> getDistinctBorrowedTitles() {
        return ResponseEntity.ok(new ResponseContainer<>(bookFacade.findAllDistinctBorrowedTitles()));
    }

    @Operation(summary = "get all borrowed distinct book names and amount how much copy of\n" +
            "this book was borrowed",
            description = "return list of all borrowed distinct book names and amount")
    @GetMapping("/titlesAndAmount")
    private ResponseEntity<ResponseContainer<List<BookTitleAndAmountResponse>>> getTitlesAndAmountOfBorrowing() {
        return ResponseEntity.ok(new ResponseContainer<>(bookFacade.findAllDistinctTitlesWithAmountOfBorrowing()));
    }
}
