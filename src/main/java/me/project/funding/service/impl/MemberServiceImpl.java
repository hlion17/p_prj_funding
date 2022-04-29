package me.project.funding.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.commons.SHA256Util;
import me.project.funding.dto.MemberDTO;
import me.project.funding.mapper.MemberMapper;
import me.project.funding.service.face.MemberService;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberMapper memberMapper;

    @Override
    public Boolean join(MemberDTO member) {
        log.info("등록 요청 회원정보: {}", member);

        // ID 중복체크
        boolean dupleIdCheck = isDuplicatedId(member.getId());
        if (dupleIdCheck) {
            log.error("중복된 아이디로 회원가입 요청: {}", member.getId());
            // 예외를 뭘로 던져할까
            throw new RuntimeException("중복된 아이디입니다.");
        }

        // 비밀번호 단방향 헤시알고리즘 적용하여 암호화
        member.setPw(SHA256Util.encryptionSHA256(member.getPw()));
        // 회원가입
        int result = memberMapper.save(member);

        // 회원가입 결과 확인
        if (result < 1) {
            log.error("회원가입 실패: {}", member);
            throw new RuntimeException(
                    "memberMapper.save() ERROR, 회원가입 메서드 확인\n" + "Param: " + member);
        } else {
            log.info("회원가입 성공");
            return true;
        }
    }

    @Override
    public MemberDTO login(MemberDTO member) {
        member.setPw(SHA256Util.encryptionSHA256(member.getPw()));
        log.info("로그인 인증 요청 정보 - ID: {}, PW: {}", member.getId(), member.getPw());

        MemberDTO result = memberMapper.findById(member);
        log.info("조회결과: {}", result);

        if (result == null) {
            log.info("존재하지 않는 아이디");
            return null;
        } else if (!member.getPw().equals(result.getPw())) {
            log.info("비밀번호 인증 실패");
            return null;
        } else {
            log.info("로그인 인증 성공");
            return member;
        }

    }

    // ID 중복체크
    public boolean isDuplicatedId(String id) {
        return memberMapper.idCheck(id) == 1;
    }
}
