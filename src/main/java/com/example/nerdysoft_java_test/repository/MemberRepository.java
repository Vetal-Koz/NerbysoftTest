package com.example.nerdysoft_java_test.repository;

import com.example.nerdysoft_java_test.entity.data.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
