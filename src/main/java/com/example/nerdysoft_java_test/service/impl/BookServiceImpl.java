package com.example.nerdysoft_java_test.service.impl;

import com.example.nerdysoft_java_test.entity.data.Book;
import com.example.nerdysoft_java_test.exception.CanNotBeDeletedException;
import com.example.nerdysoft_java_test.exception.EntityNotFoundException;
import com.example.nerdysoft_java_test.exception.NotValidDataException;
import com.example.nerdysoft_java_test.repository.BookRepository;
import com.example.nerdysoft_java_test.service.BookService;
import com.example.nerdysoft_java_test.util.ExceptionUtil;
import com.example.nerdysoft_java_test.util.ValidatorsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public void create(Book entity) {
        if (!bookRepository.existsByTitleAndAuthor(entity.getTitle(), entity.getAuthor())) {
            checkIdIsNotNull(entity.getId());
            checkCorrectBook(entity);
            bookRepository.save(entity);
        } else {
            Book book = bookRepository.findBookByTitleAndAuthor(entity.getTitle(), entity.getAuthor());
            book.setAmount(book.getAmount() + 1);
            bookRepository.save(book);
        }
    }

    @Override
    public void update(Book entity) {
        checkCorrectBook(entity);
        bookRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionUtil.ENTITY_NOT_FOUND));
        checkBookCanBeDeleted(book);
        bookRepository.deleteById(id);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionUtil.ENTITY_NOT_FOUND));
    }

    @Override
    public Collection<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllBorrowedDistinctByTitle() {
        return bookRepository.findAllBorrowedDistinctByTitle();
    }

    @Override
    public List<Book> findAllByMemberName(String memberName) {
        return bookRepository.findAllByMemberName(memberName);
    }

    private void checkBookCanBeDeleted(final Book book) {
        if (!book.getMember().isEmpty()) {
            throw new CanNotBeDeletedException(ExceptionUtil.CAN_NOT_BE_DELETED);
        }
    }

    private void checkCorrectBook(Book book) {
        checkTitleIsNull(book.getTitle());
        checkTitleIsNotValid(book.getTitle());
        checkAuthorIsNull(book.getAuthor());
        checkAuthorIsNotValid(book.getAuthor());
    }

    private void checkIdIsNotNull(final Long id) {
        if (id != null) {
            throw new NotValidDataException(ExceptionUtil.BOOK_ALREADY_EXIST);
        }
    }

    private void checkTitleIsNull(final String title) {
        if (title == null) {
            throw new NotValidDataException(ExceptionUtil.TITLE_IS_NOT_PRESENT);
        }
    }

    private void checkTitleIsNotValid(final String title) {
        if (!title.matches(ValidatorsUtil.TITLE_REGEX)) {
            throw new NotValidDataException(ExceptionUtil.TITLE_IS_NOT_VALID);
        }
    }

    private void checkAuthorIsNull(final String author) {
        if (author == null) {
            throw new NotValidDataException(ExceptionUtil.AUTHOR_IS_NOT_PRESENT);
        }
    }

    private void checkAuthorIsNotValid(final String author) {
        if (!author.matches(ValidatorsUtil.AUTHOR_REGEX)) {
            throw new NotValidDataException(ExceptionUtil.AUTHOR_IS_NOT_VALID);
        }
    }
}
