package me.project.funding.service.impl;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.DeliveryDTO;
import me.project.funding.dto.OrderDTO;
import me.project.funding.dto.PaymentDTO;
import me.project.funding.dto.RewardDTO;
import me.project.funding.mapper.DeliveryMapper;
import me.project.funding.mapper.OrderMapper;
import me.project.funding.mapper.PaymentMapper;
import me.project.funding.service.face.PaymentService;
import me.project.funding.service.face.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentMapper paymentMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    DeliveryMapper deliveryMapper;

    @Autowired
    RewardService rewardService;

    // 아임포트 인증 토큰을 받아오기 위한 객체
    // 객체 자체에 동기화 처리가 되있는지 확인해야 하는 것은 아닌가?
    private IamportClient api;

    public PaymentServiceImpl() {
        this.api = new IamportClient("6982445871226183", "be5fac0e5b663f545cdd7a9eb391f9b224e0513243c4148ba51c2f9775cb941161f48afc09f988ad");
    }

    @Override
    public Map<String, Object> verify(Map<String, String> requestBody) {
        // 반환할 결과를 담을 객체
        Map<String, Object> json = new HashMap<>();
        // 아임포트 인증 객체
        IamportResponse<Payment> result = null;
        try {
            // 아임포트 REST API key, private key 인증 후 결제 정보 받아오기
            result = api.paymentByImpUid(requestBody.get("imp_uid"));
        } catch (IamportResponseException e) {
            e.printStackTrace();
            // 조회 결과에 따른 응답 처리
            switch(e.getHttpStatusCode()) {
                // ResponseEntity 를 이용한 처리가 어울리려나..
                // 그렇다면 어떻게 처리해야 할까?
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

        log.info("아임포트 토큰 요청 결과: {}", result);

        // 요청 금액 변조 검증
        // 아임포트 요청 결과 금액과 DB 리워드 금액, 추가 후원금액 비교
        Payment response = result.getResponse();
        RewardDTO reward = rewardService.getRewardByNo(Integer.parseInt(requestBody.get("reward_no")));

        BigDecimal amount = response.getAmount();  // 결제 정보 금액
        int rewardPrice = reward.getRewardPrice(); // DB 리워드 가격 정보
        int extraPay;
        if ("".equals(requestBody.get("extraPay")) || requestBody.get("extraPay") == null) {
            extraPay = 0;
        } else {
            extraPay = Integer.parseInt(requestBody.get("extraPay"));  // 추가 후원 금액
        }

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
            return json;
        }

        // TODO: 2022-05-19 MVC 구조에 맞게 리팩토링
        //  - done by me 2022-05-21
        // -> 결제금액을 검증하는 로직은 service 단에서 처리해야 mvc2 구조에 어울리지 않을까?
        // controller 는 비즈니스 로직 처리에 필요한 파라미터를 비즈니스 로직 처리 객체에 전달하고
        // 처리 결과를 받아서 view 에 뿌려주는 역할을 해야 할 것 같다.
        // -> 토큰 발급 후 아임포트에 요청해서 결제 정보 받아옴
        // -> 결제 정보와 리워드 번호를 검증 서비스 단으로 전달 (PaymentService), (추가) extraPay 정보도 전달
        // -> 금액 검증 후 결과 반환
        // -> 검증 결과 json 형태로 반환
    }

    @Transactional
    @Override
    public String savePayment(OrderDTO order, DeliveryDTO delivery, PaymentDTO payment, int rewardNo) {
        // 주문, 배송, 결제 insert 를 하나의 트랜잭션으로 관리 하기 위함

        // 주문 정보 DB insert
        int orderResult = orderMapper.insert(order);
        // 주문 식별값 정보 (order insert 결과로 생성됨)
        // (메모) 오라클 시퀀스 값으로 했을나
        // 아임포트에서 merchant_uid 를 주문 식별값으로 하는 것이 좋을 것 같음
        delivery.setOrderNo(order.getOrderNo());
        // 배송 정보 DB 삽입
        int deliveryResult = deliveryMapper.insert(delivery);
        // 리워드_주문 매핑 결과 DB insert
        int rewardAndOrderResult = orderMapper.insertOrderAndReward(order.getOrderNo(), rewardNo);

        // 결제 정보 DB insert
        // (메모) 위와 같이 주문 식별값 merchant_uid 고려해보기
        payment.setOrderNo(order.getOrderNo());
        int paymentResult = paymentMapper.insert(payment);

        // DB insert 처리 결과 검증
        if (orderResult == 1
                && deliveryResult == 1
                && rewardAndOrderResult == 1
                && paymentResult == 1) {
            log.info("주문, 결제 결과 DB 저장 성공");
            return "success";
        } else {
            log.error("주문, 결제 결과 DB 저장 실패");
            return "fail";
        }
    }
}
