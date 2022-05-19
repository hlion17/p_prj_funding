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
    public Map<String, Object> payment(@RequestBody Map<String, String> requestBody) {
        log.info("[/payment][POST]");
        log.info("요청파라미터:  {}", requestBody);
        Map<String, Object> json = new HashMap<>();

        IamportResponse<Payment> result = null;
        try {
            // 아임포트 REST API key, private key 인증 후 결제 정보 받아오기
            result = api.paymentByImpUid(requestBody.get("imp_uid"));
        } catch (IamportResponseException e) {
            e.printStackTrace();
            // 조회 결과에 따른 응답 처리
            switch(e.getHttpStatusCode()) {
                // ResponseEntity 를 이용한 처리가 어울리려나..
                case 401 :
                    log.error("401: 인증되지 않음");
                    // 401 status 처리 코드
                    break;
                case 404 :
                    log.error("404: 거래내역이 존재하지 않음");
                    // 404 status 처리 코드
                    break;
                case 500 :
                    log.error("500: 서버 응답 오류");
                    // 500 status 처리 코드
                    break;
            }
        } catch (IOException e) {
            //서버 연결 실패
            e.printStackTrace();
        }

        System.out.println("아임포트 토큰 요청 결과: " + result);

        // 요청 금액 변조 검증
        // 아임포트 요청 결과 금액과 DB 리워드 금액, 추가 후원금액 비교
        Payment response = result.getResponse();
        RewardDTO reward = rewardService.getRewardByNo(Integer.parseInt(requestBody.get("reward_no")));

        BigDecimal amount = response.getAmount();  // 결제 정보 금액
        int rewardPrice = reward.getRewardPrice(); // DB 리워드 가격 정보
        int extraPay = Integer.parseInt(requestBody.get("extraPay"));  // 추가 후원 금액

        // 결제 요청 금액과 DB 리워드 금액 + 추가 후원금액과 비교
        if (amount.intValue() == (rewardPrice + extraPay)) {
            // 검증을 통과한 경우
            json.put("result", "success");
            json.put("data", result);
            return json;
        } else {
            // 검증에 실패한 경우
            json.put("result", "fail");
            json.put("data", result);
            // bad Request, 이건 status response 로 응답 하는게 아닌거 같은데 잘못 된거 같음 다른 방법 강구
            json.put("status", 400);
            return json;
        }

        // TODO: 2022-05-19 MVC 구조에 맞게 리팩토링
        // -> 결제금액을 검증하는 로직은 service 단에서 처리해야 mvc2 구조에 어울리지 않을까?
        // controller 는 비즈니스 로직 처리에 필요한 파라미터를 비즈니스 로직 처리 객체에 전달하고
        // 처리 결과를 받아서 view 에 뿌려주는 역할을 해야 할 것 같다.
        // -> 토큰 발급 후 아임포트에 요청해서 결제 정보 받아옴
        // -> 결제 정보와 리워드 번호를 검증 서비스 단으로 전달 (PaymentService), (추가) extraPay 정보도 전달
        // -> 금액 검증 후 결과 반환
        // -> 검증 결과 json 형태로 반환
    }
}
