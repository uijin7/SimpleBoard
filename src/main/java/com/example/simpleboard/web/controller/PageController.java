package com.example.simpleboard.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/admin/members")
    public String adminMembersPage() {
        return "admin-members";
    }

    @GetMapping("/posts/write")
    public String postWritePage() {
        return "post-write";
    }

    @GetMapping("/posts/edit/{id}")
    public String postEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("postId", id);
        return "post-edit";
    }
}
