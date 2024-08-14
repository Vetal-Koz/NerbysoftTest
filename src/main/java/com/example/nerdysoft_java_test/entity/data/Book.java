package com.example.nerdysoft_java_test.entity.data;

import com.example.nerdysoft_java_test.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private Integer amount;
    @ManyToMany(mappedBy = "books")
    private Set<Member> member;

    public Book() {
        this.amount = 1;
        this.member = new HashSet<>();
    }
}
