package com.example.nerdysoft_java_test.dto.request;

import com.example.nerdysoft_java_test.util.ValidatorsUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookRequest extends ApiRequest {
    @Schema(name = "title", example = "Iron Flame")
    @NotBlank(message = "title can't be blank")
    @Pattern(regexp = ValidatorsUtil.TITLE_REGEX, message = "title is not valid")
    private String title;
    @Schema(name = "author", example = "Paulo Coelho")
    @NotBlank(message = "author can't be blank")
    @Pattern(regexp = ValidatorsUtil.AUTHOR_REGEX, message = "title is not valid")
    private String author;
}
