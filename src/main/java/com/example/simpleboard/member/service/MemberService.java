package com.example.simpleboard.member.service;

import com.example.simpleboard.global.time.TimeProvider;
import com.example.simpleboard.member.model.AdminMemberDto;
import com.example.simpleboard.member.model.AdminPasswordResetRequest;
import com.example.simpleboard.member.entity.MemberEntity;
import com.example.simpleboard.member.model.LoginMemberDto;
import com.example.simpleboard.member.model.MemberLoginRequest;
import com.example.simpleboard.member.model.MemberSignupRequest;
import com.example.simpleboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

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
                    .createdAt(TimeProvider.nowInKorea())
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

    public java.util.List<AdminMemberDto> getMembersForAdmin(HttpSession session) {
        requireAdmin(session);

        return memberRepository.findAllByStatusOrderByIdDesc("REGISTERED")
                .stream()
                .map(this::toAdminDto)
                .toList();
    }

    public void resetPasswordByAdmin(Long memberId, AdminPasswordResetRequest request, HttpSession session) {
        requireAdmin(session);

        var member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));

        member.setPassword(passwordEncoder.encode(request.getNewPassword()));
        memberRepository.save(member);
    }

    public LoginMemberDto getLoginMember(HttpSession session) {
        if (session == null) {
            return null;
        }
        return (LoginMemberDto) session.getAttribute(LOGIN_MEMBER);
    }

    public boolean isAdmin(HttpSession session) {
        var loginMember = getLoginMember(session);
        return loginMember != null && "ADMIN".equalsIgnoreCase(loginMember.getRole());
    }

    private LoginMemberDto requireAdmin(HttpSession session) {
        var loginMember = getLoginMember(session);

        if (loginMember == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        if (!"ADMIN".equalsIgnoreCase(loginMember.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자만 접근할 수 있습니다.");
        }

        return loginMember;
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

    private AdminMemberDto toAdminDto(MemberEntity entity) {
        return AdminMemberDto.builder()
                .id(entity.getId())
                .loginId(entity.getLoginId())
                .name(entity.getName())
                .email(entity.getEmail())
                .role(entity.getRole())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
