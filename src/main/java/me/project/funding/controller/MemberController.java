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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/member/login")
    public String login() {
        log.info("[/member/login][GET]");
        return "member/login";
    }

    @PostMapping("/member/login")
    public ModelAndView loginProcess(MemberDTO member, HttpSession session) {
        log.info("[/member/login][POST]");
        ModelAndView mav = new ModelAndView();

        // 로그인 검증
        MemberDTO result = memberService.login(member);

        // 로그인 검증 결과에 따른 View 처리
        if (result == null) {
            mav.setViewName("jsonView");
            mav.addObject("result", -1);
            mav.addObject("msg", "로그인 실패");
            return mav;
        } else if (result.getGrade() == 3) {
            mav.setViewName("jsonView");
            mav.addObject("result", -1);
            mav.addObject("msg", "이미 탈퇴한 회원입니다.");
            return mav;
        } else {
            // 모든 이외(else) 상황을 로그인 성공으로 처리해도 되는 것인가
            // 예상하지 못한 결과에도 로그인 성공 처리하는 것은 아닌지
            // login service 에서 로그인 성공시 status 값을 추가 하고
            // status 값을 if 문으로 확인하는 방식 생각해보기
            mav.setViewName("jsonView");
            session.setAttribute("loginId", result.getId());
            session.setAttribute("loginMemberNo", result.getMemberNo());
            log.info("로그인결과: {}", result);
            return mav;
        }
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        log.info("[/member/logout][GET]");

        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/member/detail")
    public ModelAndView detail(MemberDTO member) {
        log.info("[/member/detail][GET]");
        log.info("요청 파라미터: {}", member);

        ModelAndView mav = new ModelAndView("member/detail");
        Map<String, Object> model = mav.getModel();

        model.put("member", memberService.getDetail(member));
        return mav;
    }

    // myProfile 로 대체
    @GetMapping("/member/update")
    public ModelAndView updatePage(MemberDTO member) {
        log.info("[/member/update][GET]");
        ModelAndView mav = new ModelAndView("member/update");
        Map<String, Object> model = mav.getModel();
        model.put("member",memberService.getDetail(member));
        return mav;
    }

    @PostMapping("/member/update")
    public ModelAndView update(MemberDTO member, HttpSession session) {
        log.info("[/member/update][POST]");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/myPage/profile");

        // 세션 로그인 확인
        if (session.getAttribute("loginMemberNo") == null || session.getAttribute("loginId") == null) {
            log.error("로그인 되지 않음");
            throw new RuntimeException("로그인 상태 확인");
        }

        member.setMemberNo((Integer) session.getAttribute("loginMemberNo"));
        member.setId((String) session.getAttribute("loginId"));

        log.info("업데이트 파마미터: {}", member);
        // 회원정보 업데이트
        MemberDTO editedMember = memberService.editMemberInfo(member);
        // 리다이렉트 할 꺼면 위에 코드에서 MemberDTO 받아올 필요 없음
        //log.info("조회된 정보: {}", editedMember);
        //mav.addObject("member", editedMember);
        return mav;
    }
}
