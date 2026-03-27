package com.example.simpleboard.member.controller;

import com.example.simpleboard.member.model.AdminMemberDto;
import com.example.simpleboard.member.model.AdminPasswordResetRequest;
import com.example.simpleboard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class AdminMemberApiController {

    private final MemberService memberService;

    @GetMapping
    public List<AdminMemberDto> members(HttpSession session) {
        return memberService.getMembersForAdmin(session);
    }

    @PostMapping("/{memberId}/password")
    public void resetPassword(
            @PathVariable Long memberId,
            @Valid @RequestBody AdminPasswordResetRequest request,
            HttpSession session
    ) {
        memberService.resetPasswordByAdmin(memberId, request, session);
    }
}
