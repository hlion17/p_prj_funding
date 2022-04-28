package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class MemberController {

    @GetMapping("/test")
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView("test/go");
        log.info("[/test][GET]");
        return mav;
    }
}
