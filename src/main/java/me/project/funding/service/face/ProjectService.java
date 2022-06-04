package me.project.funding.service.face;

import me.project.funding.commons.Pagination;
import me.project.funding.dto.CategoryDTO;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    /**
     * 현재 회원이 작성중인 프로젝트 리스트를 가져온다.
     * @param member 현재 로그인한 회원정보가 담긴 DTO
     * @return 로그인한 회원이 가진 프로젝트중 상태가 작성중인 프로젝트 리스트
     */
    List<ProjectDTO> getOnWritingProject(MemberDTO member);

    /**
     * 한 개의 프로젝트를 가져온다.
     * @param projectNo 가져올 프로젝트의 식별값
     * @return 조회된 프로젝트
     */
    ProjectDTO getProject(int projectNo);

    /**
     * 특정 회원이 후원한 프로젝트 목록을 가져온다.
     * @param paramMap 회원 식별값이 담긴 DTO 객체, 검색어
     * @return 후원 프로젝트 목록
     */
    List<Map<String, Object>> getSupportProjects(Map<String, Object> paramMap);

    /**
     * 프로젝트 인트로 내용을 적어 프로젝트를 생성한다.
     * @param project 프로젝트 인트로 내용이 들어 있는 프로젝트 DTO 객체
     */
    void createProject(ProjectDTO project);

    /**
     * 작성중인 프로젝트 내용을 업데이트 한다.
     * @param project 업데이트 할 프로젝트 내용이 들어있는 DTO 객체
     */
    void updateOnWritingProject(ProjectDTO project);

    /**
     * 작성중인 프로젝트를 삭제한다.
     * @param projectNo 삭제할 작성중인 프로젝트 번호
     */
    void deleteOnWritingProject(int projectNo);

    /**
     * 프로젝트 카테고리 목록을 가져온다.
     * @return 조회된 카테고리 목록
     */
    List<CategoryDTO> getCategoryList();

    /**
     * 프로젝트 대표 이미지 파일 업로드
     * @param file 업로드할 파일 정보
     * @param path 업로드할 경로
     * @return 업로드한 파일 이름(저장된 이름)
     */
    String uploadFile(MultipartFile file, String path);

    /**
     * 프로젝트 리스트를 가져온다.
     * @param pagination 검색어, 필터링, 정렬기준, 페이지 정보가 담긴 DTO
     * @return DTO 조건이 반영된 프로젝트 리스트
     */
    List<ProjectDTO> getPageList(Pagination pagination);

    /**
     * 좋아요 기능
     * - 이미 좋아요 되있는 경우 취소, 없는 경우 좋아요
     * @param projectNo 프로젝트 식별값
     * @param memberNo 회원 식별값
     * @param jsonResponse 좋아요 처리 결과 응답 객체
     * @return jsonResponse 에 처리 결과를 담아 그대로 반환
     */
    Map<String, Object> checkLike(int projectNo, int memberNo, Map<String, Object> jsonResponse);

    /**
     * 프로젝트 좋아요 여부 조회
     * @param projectNo
     * @param memberNo
     * @return
     */
    int getLikeResult(int projectNo, int memberNo);

    /**
     * 좋아요 누른 프로젝트 조회
     * @param memberNo 회원식별값
     * @return
     */
    List<ProjectDTO> getLikeProject(Integer memberNo);

    /**
     * 회원이 작성한 모든 프로젝트 조회
     * @param memberNo 회원 식별값
     * @return 조회된 프로젝트 리스트
     */
    List<ProjectDTO> getMemberProjects(int memberNo, int projectStep);
}
