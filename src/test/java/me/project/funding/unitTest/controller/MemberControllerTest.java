package me.project.funding.unitTest.controller;

import me.project.funding.controller.MemberController;
import me.project.funding.service.face.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.xml.transform.Result;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    MemberController memberController = new MemberController();

    @Mock
    MemberService memberService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(memberController)
                .build();
    }

    @Test
    @DisplayName("회원가입 페이지")
    void joinPage() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders
                .get("/member/join");

        // when
        ResultActions result = this.mockMvc.perform(request);

        // then
        result.andExpectAll(
                status().isOk()
                , view().name("member/join")
        );
    }

    @Test
    @DisplayName("회원가입 실패")
    void join() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders
                .post("/member/join")
                .param("id", "testId")
                .param("pw", "testPw")
                .param("nick", "testNick")
                .param("grade", "1");

        given(memberService.join(any())).willReturn(anyBoolean());

        // when
        ResultActions result = this.mockMvc.perform(request);

        // then
        result
                //.andDo(print())
                .andExpectAll(
                        status().isOk()
                        , model().attributeExists("result")
                );
    }

    @Test
    @DisplayName("로그인 페이지")
    void loginPage() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders.get("/member/login");

        // when
        ResultActions result = this.mockMvc.perform(request);

        // then
        result
                .andExpectAll(
                        status().isOk()
                        , view().name("member/login")
                );
    }

    @Test
    @DisplayName("로그인")
    void loginProcess() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders
                .post("/member/login")
                .param("id", "testId")
                .param("pw", "testPw");

        given(memberService.login(any())).willReturn(any());

        // when
        ResultActions result = this.mockMvc.perform(request);

        // then
        result
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                        , model().attributeExists("result", "msg")
                );

    }

}
