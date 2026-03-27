package com.example.simpleboard.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AdminPasswordResetRequest {

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    @Size(min = 4, max = 100, message = "비밀번호는 4자 이상 100자 이하로 입력해주세요.")
    private String newPassword;
}
