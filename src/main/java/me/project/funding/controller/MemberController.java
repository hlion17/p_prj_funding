package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.MemberDTO;
import me.project.funding.mapper.MemberMapper;
import me.project.funding.service.face.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/member/join")
    public ModelAndView join() {
        return new ModelAndView("member/join");
    }

    @PostMapping("/member/join")
    public ModelAndView joinProcess(MemberDTO member) {
        log.info("[/member/join][POST]");

        ModelAndView mav = new ModelAndView("jsonView");
        Map<String, Object> model = mav.getModel();

        model.put("result", memberService.join(member));
        return mav;
    }

}
