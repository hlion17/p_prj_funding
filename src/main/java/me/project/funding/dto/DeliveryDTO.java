package me.project.funding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class DeliveryDTO {
    private int deliveryNo;
    private int orderNo;
    private String postalCode;
    private String address;
    private String addressExtra;
    private String addressDetail;
    private String recipientName;
    private String recipientPhone;
    private String precautions;
}
