package com.example.nerdysoft_java_test.controller;

import com.example.nerdysoft_java_test.dto.request.MemberRequest;
import com.example.nerdysoft_java_test.dto.response.BookResponse;
import com.example.nerdysoft_java_test.dto.response.MemberResponse;
import com.example.nerdysoft_java_test.dto.response.ResponseContainer;
import com.example.nerdysoft_java_test.facade.BookFacade;
import com.example.nerdysoft_java_test.facade.MemberFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "Member API", description = "the member api that contains crud, and related operations")
public class MemberController {
    private final MemberFacade memberFacade;
    private final BookFacade bookFacade;

    @Operation(summary = "get all members as list",
            description = "return all members")
    @GetMapping
    private ResponseEntity<ResponseContainer<List<MemberResponse>>> getAllMembers() {
        return ResponseEntity.ok(new ResponseContainer<>(memberFacade.findAll()));
    }

    @Operation(summary = "get member by id",
            description = "return a member")
    @GetMapping("/{id}")
    private ResponseEntity<ResponseContainer<MemberResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseContainer<>(memberFacade.findById(id)));
    }

    @Operation(summary = "create new instant of member",
            description = "create new member")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid MemberRequest member) {
        memberFacade.create(member);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "update instant of member",
            description = "update a member")
    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid MemberRequest member) {
        memberFacade.update(id, member);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "delete instant of member",
            description = "delete a member")
    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        memberFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "borrow a book by it's id",
            description = "borrow a book")
    @PostMapping("/{memberId}/borrow/{bookId}")
    private ResponseEntity<?> borrowBookForMember(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "bookId") Long bookId) {
        memberFacade.borrowBookForUser(bookId, memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "return a book by it's id",
            description = "return a book back")
    @PostMapping("/{memberId}/return/{bookId}")
    private ResponseEntity<?> returnBookBack(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "bookId") Long bookId) {
        memberFacade.returnBookBack(bookId, memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "get all books borrowed by a specific member by name",
            description = "return list of borrowed books")
    @GetMapping("/borrowed")
    private ResponseEntity<ResponseContainer<List<BookResponse>>> getAllBorrowedBookByName(
            @RequestBody MemberRequest nameRequest) {
        return ResponseEntity.ok(new ResponseContainer<>(bookFacade.findAllByMemberName(nameRequest.getName())));
    }
}
