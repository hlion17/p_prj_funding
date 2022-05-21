package me.project.funding.mapper;

import me.project.funding.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    /**
     * 주문 정보 저장
     * @param order 주문 정보
     * @return DB insert 결과
     */
    int insert(OrderDTO order);

    /**
     * 주문_리워드 매핑 테이블 저장
     * @param orderNo 주문 식별값
     * @param rewardNo 리워드 식별값
     * @return DB insert 결과
     */
    int insertOrderAndReward(@Param("orderNo") int orderNo, @Param("rewardNo") int rewardNo);
}
