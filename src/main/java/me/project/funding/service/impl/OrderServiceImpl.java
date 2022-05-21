package me.project.funding.service.impl;

import me.project.funding.dto.DeliveryDTO;
import me.project.funding.dto.OrderDTO;
import me.project.funding.mapper.DeliveryMapper;
import me.project.funding.mapper.OrderMapper;
import me.project.funding.service.face.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    DeliveryMapper deliveryMapper;

    @Transactional
    @Override
    public String saveOrderAndDelivery(OrderDTO order, DeliveryDTO delivery, int rewardNo) {
        int orderResult = orderMapper.insert(order);
        delivery.setOrderNo(order.getOrderNo());
        int deliveryResult = deliveryMapper.insert(delivery);
        int rewardAndOrderResult = orderMapper.insertOrderAndReward(order.getOrderNo(), rewardNo);

        // DB insert 처리 결과 검증
        if (orderResult == 1
                && deliveryResult == 1
                && rewardAndOrderResult == 1) {
            return "success";
        } else {
            return "fail";
        }
    }
}
