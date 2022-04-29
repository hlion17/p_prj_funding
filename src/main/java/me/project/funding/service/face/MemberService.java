package me.project.funding.service.face;

import me.project.funding.dto.MemberDTO;

public interface MemberService {
    /**
     * 입력한 회원정보를 가지고 회원가입
     * @param member 회원정보가 담긴 DTO 객체
     * @return 회원가입 성공 여부
     */
    Boolean join(MemberDTO member);
}
