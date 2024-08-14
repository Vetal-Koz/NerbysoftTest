package com.example.nerdysoft_java_test.facade;

import com.example.nerdysoft_java_test.dto.request.MemberRequest;
import com.example.nerdysoft_java_test.dto.response.MemberResponse;

public interface MemberFacade extends CrudFacade<MemberRequest, MemberResponse> {
    void borrowBookForUser(Long bookId, Long memberId);

    void returnBookBack(Long bookId, Long memberId);
}
