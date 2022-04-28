package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MemberController {

    @GetMapping("/test")
    public String test() {
        log.info("[/test][GET]");
        return "test/go";
    }
}
