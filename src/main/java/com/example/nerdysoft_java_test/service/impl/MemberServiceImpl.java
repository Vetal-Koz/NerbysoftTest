package com.example.nerdysoft_java_test.service.impl;

import com.example.nerdysoft_java_test.entity.data.Book;
import com.example.nerdysoft_java_test.entity.data.Member;
import com.example.nerdysoft_java_test.exception.CanNotBeDeletedException;
import com.example.nerdysoft_java_test.exception.EntityNotFoundException;
import com.example.nerdysoft_java_test.exception.CanNotBeBorrowedException;
import com.example.nerdysoft_java_test.exception.NotValidDataException;
import com.example.nerdysoft_java_test.repository.BookRepository;
import com.example.nerdysoft_java_test.repository.MemberRepository;
import com.example.nerdysoft_java_test.service.MemberService;
import com.example.nerdysoft_java_test.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    //    @Value("${max.number.of.borrowed.books}")
    private final int maxNumberOfBorrowedBooks = 10;

    @Override
    public void create(Member entity) {
        checkCorrectMember(entity);
        memberRepository.save(entity);
    }

    @Override
    public void update(Member entity) {
        checkNameIsNull(entity.getName());
        memberRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionUtil.ENTITY_NOT_FOUND));
        checkMemberCanBeDeleted(member);
        memberRepository.deleteById(id);
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionUtil.ENTITY_NOT_FOUND));
    }

    @Override
    public Collection<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public void borrowBookForUser(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionUtil.ENTITY_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionUtil.ENTITY_NOT_FOUND));
        Set<Book> books = member.getBooks();
        int amountOfBook = book.getAmount();
        if (checkNumberOfBorrowedBooksCorrespondToLimit(books) && checkBookAmountMoreThanOne(amountOfBook)) {
            book.setAmount(book.getAmount() - 1);
            books.add(book);
            memberRepository.save(member);
        } else {
            throw new CanNotBeBorrowedException(ExceptionUtil.CAN_NOT_BE_BORROWED);
        }
    }

    @Override
    public void returnBookBack(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionUtil.ENTITY_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionUtil.ENTITY_NOT_FOUND));
        Set<Book> books = member.getBooks();
        books.remove(book);
        memberRepository.save(member);
        book.setAmount(book.getAmount() + 1);
        bookRepository.save(book);
    }

    private void checkCorrectMember(final Member member) {
        checkIdIsNotNull(member.getId());
        checkNameIsNull(member.getName());
    }

    private void checkIdIsNotNull(final Long id) {
        if (id != null) {
            throw new NotValidDataException(ExceptionUtil.MEMBER_ALREADY_EXIST);
        }
    }

    private void checkNameIsNull(final String name) {
        if (name == null) {
            throw new NotValidDataException(ExceptionUtil.NAME_IS_NOT_PRESENT);
        }
    }

    private boolean checkNumberOfBorrowedBooksCorrespondToLimit(final Set<Book> books) {
        return books.size() < maxNumberOfBorrowedBooks;
    }

    private boolean checkBookAmountMoreThanOne(int amount) {
        return amount >= 1;
    }

    private void checkMemberCanBeDeleted(final Member member) {
        if (!member.getBooks().isEmpty()) {
            throw new CanNotBeDeletedException(ExceptionUtil.CAN_NOT_BE_DELETED);
        }
    }
}
