package me.project.funding.unitTest.mapper;

import me.project.funding.dto.RewardDTO;
import me.project.funding.mapper.RewardMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
@Transactional
public class RewardMapperTest {
    @Autowired
    RewardMapper rewardMapper;

    @Test
    @DisplayName("리워드 DB insert")
    void insert() {
        // Given
        RewardDTO reward = new RewardDTO();
        reward.setProjectNo(1);
        reward.setRewardPrice(1000);
        reward.setRewardAmount(99);
        reward.setRewardName("테스트 리워드");
        reward.setRewardIntro("테스트 리워드 소개");

        // When
        int result = rewardMapper.insert(reward);
        // select 개발 후 확인

        // Then
        assertEquals(1, result);
        assertTrue(reward.getRewardNo() > 0);
    }

    @Test
    @DisplayName("리워드 목록 조회")
    void findAllByProjectNo() {
        // Given
        int projectNo = 1;

        // When
        List<RewardDTO> result = rewardMapper.findAllByProjectNo(projectNo);

        // Then
        System.out.println(result);
        System.out.println(result.size());
        assertNotNull(result);

        // 테이블의 모든 데이터 삭제 후 2개 이상의 데이터 삽입
        // List.size() 가 삽입한 데이터 개수와 일치하는지 확인
        // @Transactional 반드시 확인
    }

    @Test
    @DisplayName("리워드 삭제")
    void delete() {
        // Given
        RewardDTO reward = new RewardDTO();
        reward.setProjectNo(1);
        reward.setRewardPrice(1000);
        reward.setRewardAmount(99);
        reward.setRewardName("테스트 리워드");
        reward.setRewardIntro("테스트 리워드 소개");

        rewardMapper.insert(reward);

        // When
        int result = rewardMapper.delete(reward.getRewardNo());

        // Then
        assertEquals(1, result);
    }


}
