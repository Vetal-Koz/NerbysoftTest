package com.example.nerdysoft_java_test.dto.response;

import com.example.nerdysoft_java_test.entity.data.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemberResponse extends ApiResponse {
    private String name;
    private Date membershipDate;

    public MemberResponse(Member member) {
        setId(member.getId());
        this.name = member.getName();
        this.membershipDate = member.getMembershipDate();
    }
}
