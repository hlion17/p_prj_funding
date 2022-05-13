package me.project.funding.mapper;

import me.project.funding.dto.RewardDTO;
import me.project.funding.dto.RewardOptionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RewardMapper {
    /**
     * DB에 리워드 정보를 등록
     * @param reward 프로젝트 식별값 및 등록할 리워드 정보가 담긴 DTO
     * @return DB insert 결과
     */
    int insert(RewardDTO reward);

    /**
     * DB 에서 특정 프로젝트에 속하는 리워드 목록을 조회한다.
     * @param projectNo 프로젝트 식별값
     * @return 조회된 리워드 목록
     */
    List<RewardDTO> findAllByProjectNo(int projectNo);

    /**
     * 특정 리워드 DB 에서 삭제
     * @param rewardNo 삭제할 리워드 식별값
     * @return 삭제 결과
     */
    int delete(int rewardNo);

    /**
     * DB 리워드 옵션 생성
     * @param option 옵션의 이름이 담긴 DTO
     * @return insert 결과
     */
    int insertOption(RewardOptionDTO option);

    /**
     * 프로젝트 리워드 옵션을 모두 가져온다.
     * @param projectNo 프로젝트 식별값
     * @return 옵션 리스트
     */
    List<RewardOptionDTO> findAllOptions(int projectNo);

    /**
     * 옵션 식별값을 이용하여 옵션 정보 삭제
     * @param optionNo 옵션의 식별값
     * @return DB delete 결과
     */
    int deleteOption(int optionNo);
}
