package me.project.funding.service.face;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import me.project.funding.dto.DeliveryDTO;
import me.project.funding.dto.OrderDTO;
import me.project.funding.dto.PaymentDTO;

import java.util.Map;

public interface PaymentService {
    /**
     * 아임포트 결제 요청 결과 금액을 DB에 저장된 리워드 금액과 비교하여 검증
     * @param requestBody 아임포트 결제 요청 결과
     */
    Map<String, Object> verify(Map<String, String> requestBody);

    /**
     * 결제 정보 저장
     * @param payment 결제 정보
     * @return 저장 결과
     */
    String savePayment(OrderDTO order, DeliveryDTO delivery, PaymentDTO payment, int rewardNo);

    /**
     * 아임포트 결제 취소 요청
     * @param payment 결제 데이터 식별값, 결제취소 금액, 사유
     * @return 결제 취소 결과
     */
    IamportResponse<Payment> cancel(PaymentDTO paramPayment);
}
