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

    // not tested
    @PostMapping("/payment/cancel")
    @ResponseBody
    public IamportResponse<Payment> cancelPayment(@RequestBody Map<String, Object> param) {
        log.info("[/payment/cancel][POST]");

        // 요청 파라미터 확인
        log.info("주문번호: {}", param.get("merchant_uid"));
        log.info("결제번호: {}", param.get("imp_uid"));
        log.info("결제취소 금액: {}", param.get("cancel_request_amount"));
        log.info("결제취소 사유: {}", param.get("reason"));

        // 파라미터 검증
        if ( param.get("imp_uid") == null
                && param.get("cancel_request_amount") == null) {
            log.error("요청 파라미터 값이 정확하지 않음");
            throw new RuntimeException("잘못된 요청 파라미터");
        }

        PaymentDTO paramPayment = new PaymentDTO();
        paramPayment.setPaymentCode((String) param.get("imp_uid"));
        paramPayment.setCancelReason((String) param.get("reason"));
        // problem: param.get 반환값이 Object 인데,
        //  런타임에서는 왜 String 을 Integer 로 바꿀 수 없다고 했을까
        Object cancelAmount = param.get("cancel_request_amount");
        paramPayment.setCancelAmount((Integer) cancelAmount);

        return paymentService.cancel(paramPayment);
    }


    // TODO: 2022-05-21 payment 커멘드 객체로 요청 파라미터 받아올 수 있게 리팩토링
    //  - 다른 이름으로 커맨드 객체 바인딩 할 수 있는지 알아보기
    //  - -> imp_uid 를 paymentCode 와 같이 , 아니면 JSON key 값을 바꾸면 되긴 함
}
