package com.zerobase.lms;

import com.zerobase.lms.components.MailComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponent mailComponent;

    @RequestMapping
    public String index() {

        return "index";
    }
}
