package com.example.nerdysoft_java_test.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest extends ApiRequest {
    @NotBlank(message = "name can't be blank")
    private String name;
}
