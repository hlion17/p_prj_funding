package me.project.funding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PaymentDTO {
    private int paymentNo;
    private int projectNo;
    private int orderNo;
    private String paymentCode;
    private int paymentTotal;
    private String paymentMethod;

    private int cancelAmount;  // 결제 취소금액
    private String cancelReason;  // 결제 취소 사유
    private int paymentStatus; // 결제 상태 (default: 1, 1 - 결제완료, 2 - 결제취소)
}
