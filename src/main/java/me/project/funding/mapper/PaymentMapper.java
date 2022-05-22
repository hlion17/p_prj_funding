package me.project.funding.mapper;

import me.project.funding.dto.PaymentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface PaymentMapper {
    /**
     * 결제 정보 DB에 저장
     * @param payment 결제 정보
     * @return DB insert 결과
     */
    int insert(PaymentDTO payment);

    /**
     * 결제 코드를 사용하여 결제 데이터 조회
     * @param payment 결제 코드(식별값)
     * @return 결제 정보가 담긴 DTO 객체
     */
    PaymentDTO findByUid(PaymentDTO payment);

    /**
     * 결제 취소결과 update
     * @param mapperParam 결제 취소금액,
     * @return
     */
    int updateCancelResult(PaymentDTO payment);
}
