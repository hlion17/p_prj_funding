package me.project.funding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class OrderDTO {
    private int OrderNo;
    private int MemberNo;
    private Date orderDate;
    private int orderStatus;
    private int additionalFunding;
    private int totalPrice;
}
