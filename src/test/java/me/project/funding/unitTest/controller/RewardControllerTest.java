package me.project.funding.unitTest.controller;

import me.project.funding.controller.RewardController;
import me.project.funding.dto.RewardDTO;
import me.project.funding.service.face.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@ExtendWith(MockitoExtension.class)
public class RewardControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    RewardController rewardController = new RewardController();

    @Mock
    RewardService rewardService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(rewardController)
                .build();
    }

    @Test
    @DisplayName("리워드 생성")
    void insertReward() throws Exception {
        // Given
        String projectNo = "1";
        String rewardPrice = "1000";
        String rewardIntro = "테스트 리워드 설명";

        RewardDTO reward = new RewardDTO();
        reward.setProjectNo(Integer.parseInt(projectNo));
        reward.setRewardPrice(Integer.parseInt(rewardPrice));
        reward.setRewardIntro(rewardIntro);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/reward/create")
                .param("projectNo", projectNo)
                .param("rewardPrice", rewardPrice)
                .param("rewardIntro", rewardIntro);

        given(rewardService.createReward(any()))
                .willReturn(1);
        // 결과가 실패(-1) 때도 추가로 테스트 하고 싶을 때 어떻게 하지?

        // When
        ResultActions result = this.mockMvc.perform(request);

        // Then
        result.andExpectAll(
                status().isOk()
                , model().attributeExists("result", "msg")
        );
        verify(rewardService, times(1)).createReward(any());
    }


}
