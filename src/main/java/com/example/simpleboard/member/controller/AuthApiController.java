package com.example.simpleboard.member.controller;

import com.example.simpleboard.member.model.LoginMemberDto;
import com.example.simpleboard.member.model.MemberLoginRequest;
import com.example.simpleboard.member.model.MemberSignupRequest;
import com.example.simpleboard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public LoginMemberDto signup(@Valid @RequestBody MemberSignupRequest request) {
        return memberService.signup(request);
    }

    @PostMapping("/login")
    public LoginMemberDto login(@Valid @RequestBody MemberLoginRequest request, HttpSession session) {
        return memberService.login(request, session);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        memberService.logout(session);
    }

    @GetMapping("/me")
    public ResponseEntity<LoginMemberDto> me(HttpSession session) {
        var loginMember = memberService.getLoginMember(session);

        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(loginMember);
    }
}