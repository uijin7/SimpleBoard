package com.example.simpleboard.member.service;

import com.example.simpleboard.member.entity.MemberEntity;
import com.example.simpleboard.member.repository.MemberRepository;
import com.example.simpleboard.member.model.LoginMemberDto;
import com.example.simpleboard.member.model.MemberLoginRequest;
import com.example.simpleboard.member.model.MemberSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

    public static final String LOGIN_MEMBER = "LOGIN_MEMBER";

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginMemberDto signup(MemberSignupRequest request) {

        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다.");
        }

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");
        }

        try {
            var entity = MemberEntity.builder()
                    .loginId(request.getLoginId())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .email(request.getEmail())
                    .role("USER")
                    .status("REGISTERED")
                    .createdAt(LocalDateTime.now())
                    .build();

            var saved = memberRepository.save(entity);

            return toDto(saved);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 회원 정보입니다.", exception);
        }
    }

    public LoginMemberDto login(MemberLoginRequest request, HttpSession session) {

        var member = memberRepository.findFirstByLoginIdAndStatus(request.getLoginId(), "REGISTERED")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        var loginMember = toDto(member);
        session.setAttribute(LOGIN_MEMBER, loginMember);

        return loginMember;
    }

    public void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    public LoginMemberDto getLoginMember(HttpSession session) {
        if (session == null) {
            return null;
        }
        return (LoginMemberDto) session.getAttribute(LOGIN_MEMBER);
    }

    private LoginMemberDto toDto(MemberEntity entity) {
        return LoginMemberDto.builder()
                .id(entity.getId())
                .loginId(entity.getLoginId())
                .name(entity.getName())
                .email(entity.getEmail())
                .role(entity.getRole())
                .build();
    }
}
