package me.project.funding.unitTest.controller;

import me.project.funding.controller.MemberController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    MemberController memberController = new MemberController();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(memberController)
                .build();
    }

    @Test
    void 접속테스트() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders
                .get("/test");

        // when
        ResultActions result = this.mockMvc.perform(request);

        // then
        result.andDo(print())
                .andExpectAll(
                        status().isOk()
                        , view().name("test/go")
                );
    }
}
