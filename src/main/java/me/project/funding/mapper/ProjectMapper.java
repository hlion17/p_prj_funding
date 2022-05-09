package me.project.funding.mapper;

import me.project.funding.commons.Pagination;
import me.project.funding.dto.CategoryDTO;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {
    /**
     * 프로젝트 상태가 "작성중"인 프로젝트만 조회
     * @param member 회원 식별값(memberNo) 정보가 담긴 DTO
     * @return 조회된 프로젝트 리스트
     */
    List<ProjectDTO> findOnWritingProject(MemberDTO member);

    /**
     * 프로젝트 식별값을 가지고 하나의 프로젝트를 조회
     * @param projectNo 프로젝트 식별값
     * @return 조회된 프로젝트
     */
    ProjectDTO findByNo(int projectNo);

    /**
     * 프로젝트 생성, 프로젝트는 항상 인트로를 작성하여 생성한다.
     * @param project 프로젝트 인트로 내용이 담긴 DTO
     * @return DB insert 수행 결과
     */
    int insert(ProjectDTO project);

    /**
     * 프로젝트 내용 업데이트
     * @param project 업데이트를 할 프로젝트 내용이 담긴 DTO
     * @return DB update 수행 결과
     */
    int update(ProjectDTO project);

    /**
     * 작성중인 프로젝트 삭제
     * @param projectNo 삭제할 프로젝트 식별값
     * @return DB delete 수행 결과
     */
    int delete(int projectNo);

    /**
     * 프로젝트 카테고리 목록 조회
     * @return 프로젝트 카테고리 목록
     */
    // 테이블은 다르지만 프로젝트 테이블에 전속되어 사용되는 테이블이라 ProjectMapper 에서 처리
    List<CategoryDTO> getCategory();

    /**
     * 페이지네이션 조건에 따른 프로젝트 리스트 개수를 조회한다.
     * @param pagination 검색어, 필터, 정렬기준, 페이지 정보 DTO
     * @return 프로젝트 리스트 개수
     */
    int getTotalCnt(Pagination pagination);

    /**
     * 필터, 정렬기준이 적용된 프로젝트 리스트 조회
     * @param pagination 검색어, 필터, 정렬기준, 페이지 정보 DTO
     * @return 페이지 정보가 적용된 프로젝트 리스트
     */
    List<ProjectDTO> findAllByFilterAndOrder(Pagination pagination);
}
