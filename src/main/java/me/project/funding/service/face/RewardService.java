package me.project.funding.service.face;

import me.project.funding.dto.RewardDTO;

import java.util.List;

public interface RewardService {
    /**
     * 리워드를 생성한다.
     * @param reward 프로젝트 식별값 및 리워드 생성에 필요한 정보가 담긴 DTO
     * @return 리워드 생성 결과
     */
    int createReward(RewardDTO reward);

    /**
     * 프로젝트 식별값을 참조하는 모든 리워드를 반환한다.
     * @param projectNo 프로젝트 식별값
     * @return 조회된 리워드 리스트
     */
    List<RewardDTO> getAllRewardByProjectNo(int projectNo);
}
