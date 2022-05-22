package me.project.funding.service.impl;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
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
            log.info("결제 요청금액 검증 성공");
            return json;
        } else {
            // 검증에 실패한 경우
            json.put("result", "fail");
            json.put("data", result);
            log.info("결제 요청금액 검증 실패");
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

    @Transactional
    @Override
    public IamportResponse<Payment> cancel(PaymentDTO paramPayment) {
        // 파라미터(paramPayment)
        // -> 결제 식별값(imp_uid), 결제 취소금액, 취소 사유

        // 결제 정보 조회
        PaymentDTO foundPayment = paymentMapper.findByUid(paramPayment);
        log.info("조회된 결제 정보: {}", foundPayment);

        // DB 에 결제 정보를 저장하지 못했을 경우 결제 취소
        if (foundPayment == null) {
            return cancelPayment(paramPayment);
        }

        // TODO: 2022-05-22  

        // 결제 취소 가능 여부 검증
        // -> 결제금액 - 결제 취소 금액 <= 0
        // -> 이미 취소된 결제
        if (foundPayment.getPaymentTotal() - foundPayment.getCancelAmount() <= 0) {
            log.info("DB 결제 금액 데이터: {}", foundPayment.getPaymentTotal());
            log.info("결제 취소 시도 금액: {}", foundPayment.getCancelAmount());
            log.error("결제된 금액보다 큰 금액으로 취소 요청: {}", foundPayment.getPaymentCode());
            throw new RuntimeException("결제취소 금액이 올바르지 않음");
        } else if (foundPayment.getPaymentStatus() == 2) {
            log.error("이미 취소된 거래 취소 시도: imp_uid - {}", foundPayment.getPaymentCode());
            throw new RuntimeException("이미 취소된 거래");
        }

        // 아임 포트 결제 취소
        IamportResponse<Payment> paymentResponse = cancelPayment(paramPayment);

        // 결제취소, 환불 결과 DB 에 저장
        // 결제 취소금액(cancel_amount) 입력, 결제 상태(payment_status) 를 결제 취소(2) 로 변경
        paymentMapper.updateCancelResult(paramPayment);
        // 주문상태(order_status) 를 결제 취소(2)로 변경
        orderMapper.updateCancelResult(foundPayment.getOrderNo());

        // 결과 반환
        return paymentResponse;
    }


    // 아임포트 REST API 로 결제 환불 요청
    private IamportResponse<Payment> cancelPayment(PaymentDTO paramPayment) {
        // 결제 모듈에 전송할 결제 취소 데이터 생성
        // -> 결제금액 전액 취소
        // (참고) setCheckSum 을 이용하여 아임포트 서버에서 취소가능 금액을 검증할 수 있다.
        CancelData cancelData = new CancelData(paramPayment.getPaymentCode(), true);

        IamportResponse<Payment> paymentResponse = null;
        try {
            // 아임포트에 결제 취소 요청
            paymentResponse = api.cancelPaymentByImpUid(cancelData);
        } catch (IamportResponseException e) {
            log.error("아임포트 결제취소 실패");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("아임포트 결제취소 실패");
            e.printStackTrace();
        }

        // 이미 취소된 결제 검증
        // 아임포트 결제 취소 요청 결과가 null 인 경우 이미 취소된 거래
        if (paymentResponse == null) {
            log.error("이미 취소된 거래");
            throw new RuntimeException("이미 취소된 거래");
        }

        // 결제 취소 결과 확인
        log.info("결제 취소결과: {}", paymentResponse);

        if (paymentResponse.getCode() == -1) {
            log.error("아임포트 결제 취소 실패: {}", paymentResponse.getMessage());
            throw new RuntimeException("아임포트 결제 취소 실패");
        }

        return paymentResponse;
    }

}
