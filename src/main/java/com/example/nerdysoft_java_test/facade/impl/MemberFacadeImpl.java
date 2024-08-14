package com.example.nerdysoft_java_test.facade.impl;

import com.example.nerdysoft_java_test.dto.request.MemberRequest;
import com.example.nerdysoft_java_test.dto.response.MemberResponse;
import com.example.nerdysoft_java_test.entity.data.Member;
import com.example.nerdysoft_java_test.facade.MemberFacade;
import com.example.nerdysoft_java_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberFacadeImpl implements MemberFacade {
    private final MemberService memberService;

    @Override
    public void create(MemberRequest entity) {
        Member member = new Member();
        member.setName(entity.getName());
        memberService.create(member);
    }

    @Override
    public void update(Long id, MemberRequest entity) {
        Member member = memberService.findById(id);
        member.setName(entity.getName());
        memberService.update(member);
    }

    @Override
    public void delete(Long id) {
        memberService.delete(id);
    }

    @Override
    public MemberResponse findById(Long id) {
        return new MemberResponse(memberService.findById(id));
    }

    @Override
    public List<MemberResponse> findAll() {
        return memberService.findAll().stream().map(MemberResponse::new).toList();
    }

    @Override
    public void borrowBookForUser(Long bookId, Long memberId) {
        memberService.borrowBookForUser(bookId, memberId);
    }

    @Override
    public void returnBookBack(Long bookId, Long memberId) {
        memberService.returnBookBack(bookId, memberId);
    }
}
