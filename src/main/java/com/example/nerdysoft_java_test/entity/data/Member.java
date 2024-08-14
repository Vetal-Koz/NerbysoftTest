package com.example.nerdysoft_java_test.entity.data;

import com.example.nerdysoft_java_test.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "members")
public class Member extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(name = "membership_date")
    private Date membershipDate;
    @ManyToMany
    @JoinTable(
            name = "memb_books",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books;

    public Member() {
        this.membershipDate = new Date();
        this.books = new HashSet<>();
    }
}
