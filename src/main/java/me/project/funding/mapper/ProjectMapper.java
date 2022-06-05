package me.project.funding.mapper;

import me.project.funding.commons.Pagination;
import me.project.funding.dto.CategoryDTO;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectBoardDTO;
import me.project.funding.dto.ProjectDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 프로젝트 후원자 수 조회
     * @param projectNo 프로젝트 식별값
     * @return 조회된 후원자 수
     */
    int getContributorsCntByProjectNo(int projectNo);

    /**
     * 좋와요 여부 조회
     * @param projectNo 프로젝트 식별값
     * @param memberNo 회원 식별값
     * @return 조회 결과 (0 - 좋아요를 누른적 없음, 1 - 좋아요를 누른적 있음)
     */
    int findLike(@Param("projectNo") int projectNo, @Param("memberNo") int memberNo);

    /**
     * 프로젝트에 좋아요를 누른다.(?)
     * @param projectNo 프로젝트 식별값
     * @param memberNo 회원 식별값
     */
    void insertLike(@Param("projectNo") int projectNo, @Param("memberNo") int memberNo);

    /**
     * 프로젝트 좋아요 취소
     * @param projectNo 프로젝트 식별값
     * @param memberNo 회원 식별값
     */
    void deleteLike(@Param("projectNo") int projectNo, @Param("memberNo") int memberNo);

    /**
     * 후원 프로젝트 목록 조회
     * @param paramMap 회원 식별값이 담긴 DTO, 검색어
     * @return 후원 프로젝트 목록
     */
    List<Map<String, Object>> findAllSupportProject(Map<String, Object> paramMap);

    /**
     * 회원이 좋아요 누른 프로젝트 조회
     * @param memberNo 회원 식별값
     * @return 좋아요 누른 프로젝트 목록
     */
    List<ProjectDTO> findLikeProjects(Integer memberNo);

    /**
     * 회원이 작성한 모든 프로젝트 조회
     * @param memberNo 회원 식별값
     * @return 조회된 프로젝트 리스트
     */
    List<ProjectDTO> findMemberProjects(@Param("memberNo") int memberNo, @Param("projectStep") int projectStep);

    /**
     * 프로젝트의 게시글 목록 조회
     * @param projectNo 프로젝트 식별값
     * @return 조회된 프로젝트 게시글 목록
     */
    List<ProjectBoardDTO> findCommunityPosts(int projectNo);

    /**
     * 프로젝트 게시글 등록
     * @param board 게시글 정보
     * @return 등록 결과
     */
    int insertProjectBoard(ProjectBoardDTO board);

     /** 좋아요를 많이 받는 순으로 프로젝트 조회
     * @return 조회된 프로젝트 리스트
     */
    List<ProjectDTO> findRankedProjects();
}
