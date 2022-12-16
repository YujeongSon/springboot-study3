package com.zerobase.lms.member.controller;

import com.zerobase.lms.member.model.MemberDto;
import com.zerobase.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @GetMapping("/member/register")
    public String register() {

        return "/member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(Model model, HttpServletRequest request,
                                 HttpServletResponse response, MemberDto memberDto) {

        boolean result = memberService.register(memberDto);
        model.addAttribute("result", result);

        return "/member/register-complete";
    }

    @GetMapping("member/email-auth")
    public String emailAuth(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("id");
        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "/member/email-auth";
    }

}
