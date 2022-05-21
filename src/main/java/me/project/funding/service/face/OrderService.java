package me.project.funding.service.face;

import me.project.funding.dto.DeliveryDTO;
import me.project.funding.dto.OrderDTO;

public interface OrderService {
    /**
     * 주문 정보 및 배송 정보 삽입
     * @param order 주문정보
     * @param delivery 배송정보
     * @param rewardNo 주문 대상 리워드 식별값
     * @return 삽입 결과
     */
    String saveOrderAndDelivery(OrderDTO order, DeliveryDTO delivery, int rewardNo);
}
