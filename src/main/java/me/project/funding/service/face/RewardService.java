package me.project.funding.service.face;

import me.project.funding.dto.RewardDTO;
import me.project.funding.dto.RewardOptionDTO;

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

    /**
     * 옵션 생성
     * @param option 생성할 옵션 이름이 담긴 DTO
     * @return 생성 결과 (success, fail)
     */
    String createOption(RewardOptionDTO option);

    /**
     * 프로젝트에 있는 리워드를 위한 옵션들을 불러온다.
     * @param projectNo 프로젝트 식별값
     * @return 옵션 리스트 반환
     */
    List<RewardOptionDTO> getRewardOptions(int projectNo);

    /**
     * 옵션을 삭제한다.
     * @param optionNo 삭제할 식별값
     */
    void deleteOption(int optionNo);

    /**
     * 리워드 목록 조회
     * @param projectNo 프로젝트 식별값
     * @return 리워드 목록
     */
    List<RewardDTO> getRewards(int projectNo);

    /**
     * 리워드 삭제
     * @param rewardNo 삭제할 리워드 식별값
     */
    void deleteReward(int rewardNo);

    /**
     * 리워드 식별값으로 조회
     * @param rewardNo 리워드 식별값
     * @return 조회된 리워드 정보가 담긴 DTO
     */
    RewardDTO getRewardByNo(int rewardNo);

    //RewardOptionDTO findOptionByNo(RewardOptionDTO option);
}
