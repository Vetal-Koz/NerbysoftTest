package com.example.nerdysoft_java_test.unit.service;

import com.example.nerdysoft_java_test.entity.data.Book;
import com.example.nerdysoft_java_test.entity.data.Member;
import com.example.nerdysoft_java_test.exception.CanNotBeBorrowedException;
import com.example.nerdysoft_java_test.exception.CanNotBeDeletedException;
import com.example.nerdysoft_java_test.exception.EntityNotFoundException;
import com.example.nerdysoft_java_test.exception.NotValidDataException;
import com.example.nerdysoft_java_test.repository.BookRepository;
import com.example.nerdysoft_java_test.repository.MemberRepository;
import com.example.nerdysoft_java_test.service.impl.MemberServiceImpl;
import com.example.nerdysoft_java_test.util.ExceptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MemberServiceTest {
    @InjectMocks
    MemberServiceImpl memberService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    BookRepository bookRepository;

    @Value("${max.number.of.borrowed.books}")
    private int maxNumberOfBorrowedBooks;

    final Long id = 1L;
    final String correctName = "Vitalii";

    @Test
    public void shouldBeCreateMemberWhenIdIsNotNull() {
        Member member = new Member();
        member.setId(id);

        Exception thrown = Assertions.assertThrows(NotValidDataException.class, () -> memberService.create(member));

        assertThat(thrown).isInstanceOf(NotValidDataException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.MEMBER_ALREADY_EXIST);
    }

    @Test
    public void shouldBeCreateMemberWhereNameIsNull() {
        Member member = new Member();

        Exception thrown = Assertions.assertThrows(NotValidDataException.class, () -> memberService.create(member));

        assertThat(thrown).isInstanceOf(NotValidDataException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.NAME_IS_NOT_PRESENT);
    }


    @Test
    public void shouldBeCreateMemberWhereMemberIsCorrect() {
        Member member = new Member();
        member.setName(correctName);

        memberService.create(member);

        Mockito.verify(memberRepository, Mockito.times(1)).save(member);
    }

    @Test
    public void shouldBeDeleteMemberWhereMemberIsNull() {
        Mockito.when(memberRepository.findById(id)).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> memberService.delete(id));

        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.ENTITY_NOT_FOUND);
    }

    @Test
    public void shouldBeDeleteMemberWhereBooksIsNotEmpty() {
        Book book = new Book();
        Member member = new Member();
        member.getBooks().add(book);
        Mockito.when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        Exception thrown = Assertions.assertThrows(CanNotBeDeletedException.class, () -> memberService.delete(id));

        assertThat(thrown).isInstanceOf(CanNotBeDeletedException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.CAN_NOT_BE_DELETED);
    }

    @Test
    public void shouldBeFindByMemberWhereIdIsNotExist() {
        Mockito.when(memberRepository.findById(id)).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> memberService.findById(id));

        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.ENTITY_NOT_FOUND);
    }

    @Test
    public void shouldBeFindMemberWhereIdIsExist() {
        Mockito.when(memberRepository.findById(id)).thenReturn(Optional.of(new Member()));

        Member member = memberService.findById(id);

        assertThat(member).isNotNull();
        assertThat(member).isInstanceOf(Member.class);
    }

    @Test
    public void shouldBeBorrowBookForUserWhereNumberOfBorrowedExceedLimit() {
        Member member = new Member();
        Set<Book> books = generateBooksForMember();
        Book book = new Book();
        book.setAmount(2);
        member.setBooks(books);
        Mockito.when(memberRepository.findById(id)).thenReturn(Optional.of(member));
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Exception thrown = Assertions.assertThrows(CanNotBeBorrowedException.class,
                () -> memberService.borrowBookForUser(id, id));

        assertThat(thrown).isInstanceOf(CanNotBeBorrowedException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.CAN_NOT_BE_BORROWED);
    }

    @Test
    public void shouldBeBorrowBookForUserWhereAmountIsZero() {
        Member member = new Member();
        Book book = new Book();
        book.setAmount(0);
        Mockito.when(memberRepository.findById(id)).thenReturn(Optional.of(member));
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Exception thrown = Assertions.assertThrows(CanNotBeBorrowedException.class,
                () -> memberService.borrowBookForUser(id, id));

        assertThat(thrown).isInstanceOf(CanNotBeBorrowedException.class);
        assertThat(thrown.getMessage()).isEqualTo(ExceptionUtil.CAN_NOT_BE_BORROWED);
    }

    @Test
    public void shouldBeBorrowBookForUserWhereAllIsCorrect() {
        Member member = new Member();
        Book book = new Book();
        book.setAmount(1);
        Mockito.when(memberRepository.findById(id)).thenReturn(Optional.of(member));
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        memberService.borrowBookForUser(id, id);

        Mockito.verify(memberRepository, Mockito.times(1)).save(member);
    }

    private Set<Book> generateBooksForMember() {
        Set<Book> books = new HashSet<>();
        for (int i = 0; i < maxNumberOfBorrowedBooks + 1; i++) {
            books.add(new Book());
        }
        return books;
    }
}
