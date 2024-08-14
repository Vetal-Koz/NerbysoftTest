package com.example.nerdysoft_java_test.repository;

import com.example.nerdysoft_java_test.entity.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitleAndAuthor(String title, String author);

    Book findBookByTitleAndAuthor(String title, String author);

    @Query(value = "select distinct on (books.title) * from books " +
            "where books.id in (select distinct(book_id) from memb_books)",
            nativeQuery = true)
    List<Book> findAllBorrowedDistinctByTitle();

    List<Book> findAllByMemberName(String memberName);
}
