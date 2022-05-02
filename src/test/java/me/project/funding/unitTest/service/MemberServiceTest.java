package me.project.funding.unitTest.service;

import me.project.funding.commons.SHA256Util;
import me.project.funding.dto.MemberDTO;
import me.project.funding.mapper.MemberMapper;
import me.project.funding.service.face.MemberService;
import me.project.funding.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService = new MemberServiceImpl();

    @Mock
    private MemberMapper memberMapper;

    @Test
    @DisplayName("회원가입 테스트")
    void join() {
        // given
        // 테스트용 더미 회원
        MemberDTO member = new MemberDTO();
        member.setId("testId");
        member.setPw("testPw");
        member.setNick("testNick");
        member.setName("testName");
        member.setEmail("test@test.com");
        member.setGrade(1);

        // Mock 아이디 중복 체크
        given(memberMapper.idCheck(member.getId()))
                .willReturn(1)  // 중복 상황인 경우
                .willReturn(0)  // 중복 상황이 아닌 경우
                .willReturn(0);

        // Mock 회원가입 결과
        given(memberMapper.save(member))
                .willReturn(1)  // 가입 성공
                .willReturn(0);  // 가입 실패

        // when & then
        // 중복된 아이디로 회원가입 시도
        assertThrows(RuntimeException.class, () -> memberService.join(member));
        // 회원가입 성공
        assertTrue(memberService.join(member));
        // 회원가입 실패
        assertThrows(RuntimeException.class, () -> memberService.join(member));

        // Mock 객체 호출 확인
        verify(memberMapper, times(3)).idCheck(member.getId());
        verify(memberMapper, times(2)).save(member);

        // Memo
        // 메소드가 여러번 호출 될 떄 각기 다른 행동제어
        // -> 같은 member 더미를 사용하기 때문에 암호화 한 digest 를 다시 암호화 (같은 pw에 다른 digest)
        // 해시 암호화 테스트를 따로 만들어줘서 해야하는 문제여서 상관없나?
    }

    @Test
    @DisplayName("로그인")
    void login() {
        // given
        MemberDTO member = new MemberDTO();
        member.setId("testId");
        member.setPw("testPw");

        MemberDTO wrongMemberInfo = new MemberDTO();
        wrongMemberInfo.setId("testId");
        wrongMemberInfo.setPw(SHA256Util.encryptionSHA256("wrongPw"));

        // 아이디가 없는경우, 비밀번호가 틀린경우, 비밀번호 일치한 경우
        given(memberMapper.findById(member))
                .willReturn(isNull())
                .willReturn(wrongMemberInfo)
                .willReturn(member);

        // when
        MemberDTO resultNoId = memberService.login(member);
        MemberDTO resultWrongPw = memberService.login(member);
        MemberDTO resultSuccess = memberService.login(member);

        // then
        assertNull(resultNoId);

        assertNull(resultWrongPw);

        assertEquals(resultSuccess.getId(), member.getId());
        assertEquals(resultSuccess.getPw(), member.getPw());

        verify(memberMapper, times(3)).findById(any());
    }

    @Test
    @DisplayName("회원 상세 정보")
    void getMemberDetail() {
        // given
        MemberDTO saveMember = new MemberDTO();
        saveMember.setId("testId");
        saveMember.setPw("testPw");
        saveMember.setNick("testNick");
        saveMember.setName("testName");
        saveMember.setEmail("test@test.com");
        saveMember.setGrade(1);

        MemberDTO member = new MemberDTO();
        member.setId("testId");

        given(memberMapper.findById(member))
                .willReturn(saveMember);

        // when
        MemberDTO actual = memberService.getDetail(member);

        // then
        assertEquals(actual.getId(), saveMember.getId());

        verify(memberMapper, times(1)).findById(member);
    }

}
