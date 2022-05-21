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
}
