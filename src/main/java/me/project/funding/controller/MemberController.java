package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.MemberDTO;
import me.project.funding.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberMapper memberMapper;

    @GetMapping("/test")
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView("test/go");
        log.info("[/test][GET]");

        MemberDTO tester = memberMapper.test("tester");
        log.info("test: {}", tester);

        return mav;
    }

    @GetMapping("/member/join")
    public ModelAndView join() {
        return new ModelAndView("member/join");
    }

}
