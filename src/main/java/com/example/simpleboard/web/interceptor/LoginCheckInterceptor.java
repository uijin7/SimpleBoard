package com.example.simpleboard.web.interceptor;

import com.example.simpleboard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        var loginMember = memberService.getLoginMember(request.getSession(false));

        if (loginMember != null) {
            return true;
        }

        String uri = request.getRequestURI();

        if (uri.startsWith("/api/")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return false;
        }

        response.sendRedirect("/login");
        return false;
    }
}
