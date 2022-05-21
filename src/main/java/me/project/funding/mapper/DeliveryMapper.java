package me.project.funding.mapper;

import me.project.funding.dto.DeliveryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryMapper {
    /**
     * 배송정보 저장
     * @param delivery 배송정보
     * @return DB insert 결과
     */
    int insert(DeliveryDTO delivery);
}
