package com.example.simpleboard.post.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostViewRequest {

    @NotNull(message = "게시글 번호를 입력해주세요.")
    private Long postId;

    @NotNull(message = "게시글 비밀번호를 입력해주세요.")
    @Size(min = 4, max = 20, message = "게시글 비밀번호는 4자리 이상 20자리 이하로 입력해주세요.")
    private String password;
}
