package com.example.nerdysoft_java_test.service;

import com.example.nerdysoft_java_test.entity.data.Member;

public interface MemberService extends CrudService<Member> {
    void borrowBookForUser(Long bookId, Long memberId);

    void returnBookBack(Long bookId, Long memberId);
}
