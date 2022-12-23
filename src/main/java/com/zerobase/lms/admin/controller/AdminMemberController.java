package com.zerobase.lms.admin.controller;

import com.zerobase.lms.admin.dto.MemberDto;
import com.zerobase.lms.admin.model.MemberParam;
import com.zerobase.lms.admin.model.MemberInput;
import com.zerobase.lms.member.service.MemberService;
import com.zerobase.lms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list.do")
    public String list(Model model, MemberParam parameter) {

        parameter.init();

        List<MemberDto> memberList = memberService.list(parameter);
        model.addAttribute("list", memberList);

        long totalCount = 0;
        String queryString = parameter.getQueryString();

        if (memberList != null && memberList.size() > 0) {
            totalCount = memberList.get(0).getTotalCount();
        }
        model.addAttribute("totalCount", totalCount);

        PageUtil pageUtil = new PageUtil(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
        model.addAttribute("pager", pageUtil.pager());

        return "admin/member/list";
    }

    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParam parameter) {

        MemberDto memberDto = memberService.detail(parameter.getUserId());
        model.addAttribute("memberDto", memberDto);

        return "admin/member/detail";
    }

    @PostMapping("/admin/member/status.do")
    public String status(Model model, MemberInput parameter) {

        boolean result = memberService.updateStatus(
                parameter.getUserId(), parameter.getUserStatus());

        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }

    @PostMapping("/admin/member/password.do")
    public String password(Model model, MemberInput parameter) {

        boolean result = memberService.updatePassword(
                parameter.getUserId(), parameter.getPassword());

        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }
}
