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
    public String list(Model model, MemberParam memberParam) {

        memberParam.init();

        List<MemberDto> memberList = memberService.list(memberParam);
        model.addAttribute("list", memberList);

        long totalCount = 0;
        String queryString = memberParam.getQueryString();

        if (memberList != null && memberList.size() > 0) {
            totalCount = memberList.get(0).getTotalCount();
        }
        model.addAttribute("totalCount", totalCount);

        PageUtil pageUtil = new PageUtil(totalCount, memberParam.getPageSize(), memberParam.getPageIndex(), queryString);
        model.addAttribute("pager", pageUtil.pager());

        return "admin/member/list";
    }

    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParam memberParam) {

        MemberDto memberDto = memberService.detail(memberParam.getUserId());
        model.addAttribute("memberDto", memberDto);

        return "admin/member/detail";
    }

    @PostMapping("/admin/member/status.do")
    public String status(Model model, MemberInput memberInput) {

        boolean result = memberService.updateStatus(
                memberInput.getUserId(), memberInput.getUserStatus());

        return "redirect:/admin/member/detail.do?userId=" + memberInput.getUserId();
    }

    @PostMapping("/admin/member/password.do")
    public String password(Model model, MemberInput statusInput) {

        boolean result = memberService.updatePassword(
                statusInput.getUserId(), statusInput.getPassword());

        return "redirect:/admin/member/detail.do?userId=" + statusInput.getUserId();
    }
}
