package me.project.funding.service.face;

import me.project.funding.dto.MemberDTO;

public interface MemberService {
    /**
     * 입력한 회원정보를 가지고 회원가입
     * @param member 회원정보가 담긴 DTO 객체
     * @return 회원가입 성공 여부
     */
    Boolean join(MemberDTO member);

    /**
     * 회원 인증 (로그인)
     * @param member 회원 인증에 필요한 정보가 담긴 DTO 객체
     * @return 인증된 회원 정보 반환
     */
    MemberDTO login(MemberDTO member);

    /**
     * 회원 상세정보
     * @param member 조회할 회원의 번호
     * @return 조회된 회원의 정보
     */
    MemberDTO getDetail(MemberDTO member);

    /**
     * 회원정보 업데이트
     * @param member 업데이트 대상 회원의 정보
     * @return 업데이트 된 회원의 정보
     */
    MemberDTO editMemberInfo(MemberDTO member);
}
