package me.project.funding.unitTest.service;

import me.project.funding.dto.RewardDTO;
import me.project.funding.mapper.RewardMapper;
import me.project.funding.service.face.RewardService;
import me.project.funding.service.impl.RewardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class RewardServiceTest {
    @InjectMocks
    RewardService rewardService = new RewardServiceImpl();

    @Mock
    RewardMapper rewardMapper;

    @Test
    @DisplayName("리워드 생성")
    void createReward() {
        // Given
        RewardDTO reward = new RewardDTO();
        reward.setProjectNo(1);
        reward.setRewardIntro("테스트 리워드 인트로");
        reward.setRewardPrice(1000);

        given(rewardMapper.insert(any()))
                .willReturn(1);

        // When
        int result = rewardService.createReward(reward);

        // createReward 결과를 처리하는 로직은 컨트롤러가 아니라 서비스쪽에 있어야 할 것 같다. 테스트할 것이 없음

        // Then
        assertEquals(1, result);
    }

    @Test
    @DisplayName("리워드 조회")
    void getReward() {
        // 테스트 할 로직이 없어서 의미없는 테스트
        // 앞으로 단순 반환인 경우 단위테스트는 하지 말자

        // Given
        int projectNo = 1;
        List<RewardDTO> returnRewards = new ArrayList<>();
        given(rewardMapper.findAllByProjectNo(anyInt())).willReturn(returnRewards);

        // When
        List<RewardDTO> result = rewardService.getAllRewardByProjectNo(projectNo);

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("리워드 삭제")
    void delete() {
        // Given
        int rewardNo = 1;

        // When

        // Then

    }
}
