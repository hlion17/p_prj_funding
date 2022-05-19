package me.project.funding.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectDTO;
import me.project.funding.dto.RewardDTO;
import me.project.funding.service.face.MemberService;
import me.project.funding.service.face.ProjectService;
import me.project.funding.service.face.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class PaymentController {

    @Autowired
    ProjectService projectService;

    @Autowired
    MemberService memberService;

    @Autowired
    RewardService rewardService;

    private IamportClient api;

    public PaymentController() {
        this.api = new IamportClient("6982445871226183", "be5fac0e5b663f545cdd7a9eb391f9b224e0513243c4148ba51c2f9775cb941161f48afc09f988ad");
    }

    // not tested
    @GetMapping("/payment/{projectNo}")
    public ModelAndView paymentPage(@PathVariable("projectNo") int projectNo, int rewardNo, HttpSession session) {
        log.info("[/payment/{}][GET]", projectNo);
        ModelAndView mav = new ModelAndView("payment/payment");
        MemberDTO member = new MemberDTO();

        // 요청 파라미터 검증
        if (projectNo < 1) {
            log.error("프로젝트 식별값이 올바르지 않음: {}", projectNo);
            throw new RuntimeException("올바르지 않은 요청값");
        } else if (rewardNo < 1) {
            log.error("리워드 식별값이 올바르지 않음: {}", rewardNo);
            throw new RuntimeException("올바르지 않은 요청값");
        } else if (session.getAttribute("loginId") == null) {
            log.error("로그인 상태가 아님");
            throw new RuntimeException("올바르지 않은 요청값");
        }

        // 세션으로 부터 회원 식별값 가져오기
        member.setMemberNo((Integer) session.getAttribute("loginMemberNo"));
        member.setId((String) session.getAttribute("loginId"));

        // 프로젝트 정보
        ProjectDTO project = projectService.getProject(projectNo);
        log.info("프로젝트 조회정보: {}", project);

        // 리워드 정보
        RewardDTO reward = rewardService.getRewardByNo(rewardNo);

        // 회원정보
        MemberDTO detail = memberService.getDetail(member);
        log.info("회원 조회 정보: {}", detail);

        // View 데이터 전달
        mav.addObject("project", project);
        mav.addObject("member", detail);
        mav.addObject("reward", reward);
        return mav;
    }

    // not tested
    @PostMapping("/payment/verification")
    @ResponseBody
    public IamportResponse<Payment> payment(@RequestBody Map<String, String> requestBody) throws IamportResponseException, IOException {
        log.info("[/payment][POST]");
        Map<String, Object> json = new HashMap<>();

        log.info("요청파라미터:  {}", requestBody);

        return api.paymentByImpUid(requestBody.get("imp_uid"));
    }
}
