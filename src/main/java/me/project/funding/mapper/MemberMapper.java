package me.project.funding.mapper;

import me.project.funding.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    MemberDTO test(@Param("id") String id);

    /**
     * DB에 회원정보 삽입하여 회원가입
     * @param member 가입할 회원의 정보가 담긴 DTO
     * @return DB insert 결과 ( 1 - 성공, 1이외 실패)
     */
    int save(MemberDTO member);

    /**
     * 중복아이디를 체크
     * @param id 중복체크 요청 아이디
     * @return 1 - 중복된 아이디 존재
     */
    int idCheck(String id);
}
