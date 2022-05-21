package me.project.funding.mapper;

import me.project.funding.dto.PaymentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    /**
     * 결제 정보 DB에 저장
     * @param payment 결제 정보
     * @return DB insert 결과
     */
    int insert(PaymentDTO payment);
}
