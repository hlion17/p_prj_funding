package me.project.funding.unitTest.mapper;

import me.project.funding.commons.SHA256Util;
import me.project.funding.dto.MemberDTO;
import me.project.funding.mapper.MemberMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    @DisplayName("회원가입 확인")
    void save(@Mock MemberDTO memberMock) {
        // given
        MemberDTO member = new MemberDTO();
        member.setId("testId");
        member.setPw("testPw");
        member.setNick("testNick");
        member.setName("testName");
        member.setEmail("test@test.com");
        member.setGrade(1);

        // when
        int result = memberMapper.save(member);

        // then
        // 이렇게 테스트 하는거 아닌거 같음
        // 나중에 findById 메서드 구현하면 확인
        assertEquals(1, result);
    }

    @Test
    @DisplayName("회원아이디 중복 확인")
    void idCheck() {
        // given
        MemberDTO member = new MemberDTO();
        member.setId("testId");
        member.setPw("testPw");
        member.setNick("testNick");
        member.setName("testName");
        member.setEmail("test@test.com");
        member.setGrade(1);
        memberMapper.save(member);

        // when
        int resultExist = memberMapper.idCheck("testId");
        int resultNonExist = memberMapper.idCheck("nonExistId");

        // then
        assertEquals(1, resultExist);
        assertEquals(0, resultNonExist);
    }

    @Test
    @DisplayName("아이디로 회원 찾기 테스트")
    void findById() {
        // given
        MemberDTO member = new MemberDTO();
        member.setId("testId");
        member.setPw("testPw");
        member.setNick("testNick");
        member.setName("testName");
        member.setEmail("test@test.com");
        member.setGrade(1);

        memberMapper.save(member);

        // when
        MemberDTO foundMember = memberMapper.findById(member);

        // then
        assertEquals(foundMember.getId(), member.getId());
        assertEquals(foundMember.getPw(), SHA256Util.encryptionSHA256(member.getPw()));
        assertEquals(foundMember.getNick(), member.getNick());
        assertEquals(foundMember.getName(), member.getName());
        assertEquals(foundMember.getEmail(), member.getEmail());
        assertEquals(foundMember.getGrade(), member.getGrade());

    }
}
