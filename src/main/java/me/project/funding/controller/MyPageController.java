package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.commons.Pagination;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectDTO;
import me.project.funding.service.face.MemberService;
import me.project.funding.service.face.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

@Slf4j
@Controller
public class MyPageController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/myPage/support")
    public ModelAndView getSupportPage(HttpSession session, String keyword) {
        log.info("[/myPage/support][GET]");
        Map<String, Object> paramMap = new HashMap<>();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("member/myPage/support");

        // 로그인 여부 검증
        if (session.getAttribute("loginMemberNo") == null) {
            log.info("로그인 하지 않은 상태로 마이페이지 접근");
            throw new RuntimeException("유요하지 않은 접근");
        }

        // 전달할 파라미터
        paramMap.put("memberNo", session.getAttribute("loginMemberNo"));
        paramMap.put("keyword", keyword);

        // 후원 목록 조회
        List<Map<String, Object>> list = projectService.getSupportProjects(paramMap);

        mav.addObject("list", list);
        mav.addObject("keyword", keyword);
        return mav;
    }

    @GetMapping("/myPage/likeProject")
    public ModelAndView getLikeProject(HttpSession session) {
        log.info("[/myPage/likeProject][GET]");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("member/myPage/likeProject");

        // 로그인 여부 검증
        if (session.getAttribute("loginMemberNo") == null) {
            log.info("로그인 하지 않은 상태로 마이페이지 접근");
            throw new RuntimeException("유요하지 않은 접근");
        }

        // 좋아요 누른 프로젝트 목록 조회
        List<ProjectDTO> list = projectService.getLikeProject((Integer) session.getAttribute("loginMemberNo"));

        mav.addObject("list", list);
        return mav;
    }

    @GetMapping("/myPage/myProject")
    public ModelAndView getMyProject(HttpSession session, Integer projectStep) {
        log.info("[/myPage/myProject][GET]");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("member/myPage/myProject");

        // 로그인 여부 검증
        if (session.getAttribute("loginMemberNo") == null) {
            log.info("로그인 하지 않은 상태로 마이페이지 접근");
            throw new RuntimeException("유요하지 않은 접근");
        }

        // problem: 개선해야 할 코드, 임시방편
        if (projectStep == null) {
            projectStep = -1;
        } else {
            mav.setViewName("member/myPage/myProject-component");
        }

        // 회원의 모든 프로젝트 조회
        List<ProjectDTO> list = projectService.getMemberProjects((Integer) session.getAttribute("loginMemberNo"), projectStep);
        // 프로젝트 진행단계 별로 구분
        Map<Integer, List<ProjectDTO>> streamList =
                list.stream()
                        .collect(
                        groupingBy(ProjectDTO::getProjectStep, toList())
                );
        mav.addObject("map", streamList);
        return mav;
    }

    @GetMapping("/myPage/profile")
    public ModelAndView getProfile(HttpSession session) {
        log.info("[/myPage/profile][GET]");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("member/myPage/profile");

        // 세션 로그인 확인
        if (session.getAttribute("loginMemberNo") == null || session.getAttribute("loginId") == null) {
            log.error("로그인 되지 않음");
            throw new RuntimeException("로그인 상태 확인");
        }

        MemberDTO member = new MemberDTO();
        member.setMemberNo((Integer) session.getAttribute("loginMemberNo"));
        member.setId((String) session.getAttribute("loginId"));

        MemberDTO foundMember = memberService.getDetail(member);
        log.info("조회된 회원: {}", foundMember);

        mav.addObject("member", foundMember);
        return mav;
    }

}
