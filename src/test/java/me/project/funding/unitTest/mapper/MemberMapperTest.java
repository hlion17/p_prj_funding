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
    void save() {
        // given
        MemberDTO member = new MemberDTO();
        member.setId("testId");
        member.setPw(SHA256Util.encryptionSHA256("testPw"));
        member.setNick("testNick");
        member.setName("testName");
        member.setEmail("test@test.com");
        member.setGrade(1);

        // when
        int result = memberMapper.save(member);

        // then
        assertEquals(1, result);
        assertEquals(member.getMemberNo(), memberMapper.findById(member).getMemberNo());
        assertEquals(member.getId(), memberMapper.findById(member).getId());
        assertEquals(member.getPw(), memberMapper.findById(member).getPw());
        assertEquals(member.getNick(), memberMapper.findById(member).getNick());
        assertEquals(member.getName(), memberMapper.findById(member).getName());
        assertEquals(member.getEmail(), memberMapper.findById(member).getEmail());
        assertEquals(member.getGrade(), memberMapper.findById(member).getGrade());

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
        member.setPw(SHA256Util.encryptionSHA256("testPw"));
        member.setNick("testNick");
        member.setName("testName");
        member.setEmail("test@test.com");
        member.setGrade(1);

        memberMapper.save(member);

        // when
        MemberDTO foundMember = memberMapper.findById(member);

        // then
        assertEquals(foundMember.getId(), member.getId());
        assertEquals(foundMember.getPw(), SHA256Util.encryptionSHA256("testPw"));
        assertEquals(foundMember.getNick(), member.getNick());
        assertEquals(foundMember.getName(), member.getName());
        assertEquals(foundMember.getEmail(), member.getEmail());
        assertEquals(foundMember.getGrade(), member.getGrade());
    }

    @Test
    @DisplayName("회원정보 업데이트")
    void update() {
        // given
        MemberDTO member = new MemberDTO();
        member.setId("testId");
        member.setPw(SHA256Util.encryptionSHA256("testPw"));
        member.setNick("testNick");
        member.setName("testName");
        member.setEmail("test@test.com");
        member.setGrade(1);

        memberMapper.save(member);

        member.setNick("editedNick");
        member.setPhone("010-1111-1111");
        member.setEmail("edited@test.com");

        // when
        int result = memberMapper.update(member);

        // then
        assertEquals(1, result);
        assertEquals(member.getNick(), memberMapper.findById(member).getNick());
        assertEquals(member.getPhone(), memberMapper.findById(member).getPhone());
        assertEquals(member.getEmail(), memberMapper.findById(member).getEmail());
    }

}
