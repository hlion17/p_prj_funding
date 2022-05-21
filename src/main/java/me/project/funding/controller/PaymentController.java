package me.project.funding.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.*;
import me.project.funding.service.face.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
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

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

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
    public Map<String, Object> verifyPayment(@RequestBody Map<String, String> requestBody) {
        log.info("[/payment/verification][POST]");
        log.info("요청파라미터:  {}", requestBody);
        // 아임포트 결제 요청과 DB 리워드 금액 비교 검증
        return paymentService.verify(requestBody);
    }

    // not tested
    @PostMapping("/payment/complete")
    @ResponseBody
    public Map<String, Object> paymentComplete(@RequestBody Map<String, Object> param, HttpSession session) throws JsonProcessingException {
        log.info("[/payment/complete][POST]");
        log.info("요청 파라미터: {}", param);
        // JSON 응답 객체 생성
        Map<String, Object> jsonResponse = new HashMap<>();
        // JSON 요청 객체 파싱을 위한 JACKSON 객체 생성
        ObjectMapper mapper = new ObjectMapper();

        // 요청 파라미터 확인
        log.info("order: {}", param.get("order"));
        log.info("payment: {}", param.get("payment"));
        log.info("delivery: {}", param.get("delivery"));
        log.info("rewardNo: {}", param.get("rewardNo"));

        // JSON 요청 파싱 후 DTO 변환
        OrderDTO order = mapper.readValue(mapper.writeValueAsString(param.get("order")), OrderDTO.class);
        PaymentDTO payment = mapper.readValue(mapper.writeValueAsString(param.get("payment")), PaymentDTO.class);
        DeliveryDTO delivery = mapper.readValue(mapper.writeValueAsString(param.get("delivery")), DeliveryDTO.class);
        int rewardNo = (Integer) param.get("rewardNo");

        // 로그인 회원 정보
        if (session.getAttribute("loginMemberNo") == null) {
            log.error("회원 식별값이 존재하지 않음");
            throw new RuntimeException("잘못된 주문 정보");
        }
        // 주문 정보에 회원 식별값을 세션에서 가져온다.
        order.setMemberNo((Integer) session.getAttribute("loginMemberNo"));

        // 주문, 결제 정보 저장
        String paymentSaveResult = paymentService.savePayment(order, delivery, payment, rewardNo);

        if ("success".equals(paymentSaveResult)) {
            jsonResponse.put("result", "success");
            return jsonResponse;
        } else {
            jsonResponse.put("result", "fail");
            return jsonResponse;
        }
    }
}
