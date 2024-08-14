package com.example.nerdysoft_java_test.unit.service;

import com.example.nerdysoft_java_test.entity.data.Book;
import com.example.nerdysoft_java_test.entity.data.Member;
import com.example.nerdysoft_java_test.exception.CanNotBeDeletedException;
import com.example.nerdysoft_java_test.exception.EntityNotFoundException;
import com.example.nerdysoft_java_test.exception.NotValidDataException;
import com.example.nerdysoft_java_test.repository.BookRepository;
import com.example.nerdysoft_java_test.service.impl.BookServiceImpl;
import com.example.nerdysoft_java_test.util.ExceptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BookServiceTest {
    @InjectMocks
    BookServiceImpl bookService;
    @Mock
    BookRepository bookRepository;

    final Long id = 1L;
    final String correctTitle = "Mindset";
    final String correctAuthor = "Vitalii Lucal";

    @Test
    public void shouldBeCreateBookWhenIdIsNotNull() {
        Book book = new Book();
        book.setId(id);

        Exception thrown = Assertions.assertThrows(NotValidDataException.class, () -> bookService.create(book));

        assertThat(thrown).isInstanceOf(NotValidDataException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.BOOK_ALREADY_EXIST);
    }

    @Test
    public void shouldBeCreateBookWhereTitleIsNull() {
        Book book = new Book();

        Exception thrown = Assertions.assertThrows(NotValidDataException.class, () -> bookService.create(book));

        assertThat(thrown).isInstanceOf(NotValidDataException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.TITLE_IS_NOT_PRESENT);
    }

    @Test
    public void shouldBeCreateBookWhereTitleIsNotValid() {
        Book book = new Book();
        book.setTitle("ssss");

        Exception thrown = Assertions.assertThrows(NotValidDataException.class, () -> bookService.create(book));

        assertThat(thrown).isInstanceOf(NotValidDataException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.TITLE_IS_NOT_VALID);
    }

    @Test
    public void shouldBeCreateBookWhereAuthorIsNull() {
        Book book = new Book();
        book.setTitle(correctTitle);

        Exception thrown = Assertions.assertThrows(NotValidDataException.class, () -> bookService.create(book));

        assertThat(thrown).isInstanceOf(NotValidDataException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.AUTHOR_IS_NOT_PRESENT);
    }

    @Test
    public void shouldBeCreateBookWhereAuthorIsNotValid() {
        Book book = new Book();
        book.setTitle(correctTitle);
        book.setAuthor("dsada");

        Exception thrown = Assertions.assertThrows(NotValidDataException.class, () -> bookService.create(book));

        assertThat(thrown).isInstanceOf(NotValidDataException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.AUTHOR_IS_NOT_VALID);
    }

    @Test
    public void shouldBeCreateBookWhereBookIsCorrect() {
        Book book = new Book();
        book.setTitle(correctTitle);
        book.setAuthor(correctAuthor);

        bookService.create(book);

        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }

    @Test
    public void shouldBeDeleteBookWhereBookIsNull() {
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.delete(id));

        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.ENTITY_NOT_FOUND);
    }

    @Test
    public void shouldBeDeleteBookWhereMembersIsNotEmpty() {
        Book book = new Book();
        Member member = new Member();
        book.getMember().add(member);
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Exception thrown = Assertions.assertThrows(CanNotBeDeletedException.class, () -> bookService.delete(id));

        assertThat(thrown).isInstanceOf(CanNotBeDeletedException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.CAN_NOT_BE_DELETED);
    }

    @Test
    public void shouldBeFindByBookWhereIdIsNotExist() {
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.findById(id));

        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.ENTITY_NOT_FOUND);
    }

    @Test
    public void shouldBeFindBookWhereIdIsExist() {
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(new Book()));

        Book book = bookService.findById(id);

        assertThat(book).isNotNull();
        assertThat(book).isInstanceOf(Book.class);
    }
}
