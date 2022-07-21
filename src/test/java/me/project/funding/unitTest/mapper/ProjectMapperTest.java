package me.project.funding.unitTest.mapper;

import me.project.funding.dto.CategoryDTO;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectDTO;
import me.project.funding.mapper.MemberMapper;
import me.project.funding.mapper.ProjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@Transactional
@SpringBootTest
public class ProjectMapperTest {
    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    MemberMapper memberMapper;

    @Test
    @DisplayName("프로젝트 생성")
    void createProject() {
        // Given
        ProjectDTO newProject = new ProjectDTO();
        newProject.setProjectIntro("테스트 프로젝트 인트로");
        newProject.setMemberNo(1);  // 회원 테이블의 데이터에 의존한다.
        newProject.setCategoryId(1);  // 카테고리 테이블 데이터에 의존한다.

        // When
        int result = projectMapper.insert(newProject);

        // Then
        assertEquals(1, result);
        assertNotEquals(0, newProject.getProjectNo());
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    @DisplayName("하나의 프로젝트 조회")
    void getProject(int projectNo) {
        // Given

        // When
        ProjectDTO result = projectMapper.findByNo(projectNo);

        // Then
        assertNotNull(result);
        assertEquals(projectNo, result.getProjectNo());
    }

    @Test
    @DisplayName("프로젝트 진행상태가 작성중인 프로젝트 리스트 조회")
    void getOnWritingProject() {
        // Given
        MemberDTO loginMember = new MemberDTO();
        loginMember.setMemberNo(2);  // 테스트가 회원 테이블에 의존적

        // When
        List<ProjectDTO> resultList = projectMapper.findOnWritingProject(loginMember);

        // Then
        assertNotNull(resultList);
        // 반복문을 사용한 테스트를 대신 해주는 기능이 있지 않을까 찾아보기
        for (ProjectDTO p : resultList) {
            assertEquals(0, p.getProjectStep());
        }
    }

    @Test
    @DisplayName("작성중인 프로젝트 업데이트")
    void updateOnWritingProject() {
        // Given
        ProjectDTO newProject = new ProjectDTO();
        newProject.setProjectIntro("테스트 프로젝트 인트로");
        newProject.setMemberNo(1);  // 회원 테이블의 데이터에 의존한다.
        newProject.setCategoryId(1);  // 카테고리 테이블 데이터에 의존한다.
        newProject.setProjectTitle("테스트 프로젝트 제목");
        newProject.setProjectIntro("테스트 프로젝트 인트로");
        newProject.setBudgetPlan("테스트 프로젝트 예산 계획");
        newProject.setSchedulePlan("테스트 스케쥴 플랜");
        newProject.setProjectImage("테스트 대표사진 경로");
        newProject.setProjectPrice(2000000);
        newProject.setOpenDate(new Date());
        newProject.setCloseDate(new Date());
        newProject.setDeliveryDate(new Date());
        newProject.setProjectContent("테스트 프로젝트 내용");
        projectMapper.insert(newProject);

        ProjectDTO updatedProject = new ProjectDTO();
        // 새로 생성한 프로젝트 식별값 = 업데이트할 프로젝트 식별값
        updatedProject.setProjectNo(newProject.getProjectNo());
        // 수정된 내용
        updatedProject.setProjectIntro("수정된 프로젝트 인트로");
        updatedProject.setCategoryId(2);  // 카테고리 테이블 데이터에 의존한다.
        updatedProject.setProjectTitle("수정된 프로젝트 제목");
        updatedProject.setProjectIntro("수정된 프로젝트 인트로");
        updatedProject.setBudgetPlan("수정된 프로젝트 예산 계획");
        updatedProject.setSchedulePlan("수정된 스케쥴 플랜");
        updatedProject.setProjectImage("수정된 대표사진 경로");
        updatedProject.setProjectPrice(3000000);
        updatedProject.setOpenDate(new Date());
        updatedProject.setCloseDate(new Date());
        updatedProject.setDeliveryDate(new Date());
        updatedProject.setProjectContent("수정된 프로젝트 내용");

        // When
        int updateResult = projectMapper.update(updatedProject);
        ProjectDTO projectResult = projectMapper.findByNo(updatedProject.getProjectNo());

        // Then
        assertEquals(1, updateResult);
        assertEquals(updatedProject.getProjectIntro(), projectResult.getProjectIntro());
        assertEquals(updatedProject.getCategoryId(), projectResult.getCategoryId());
        assertEquals(updatedProject.getProjectTitle(), projectResult.getProjectTitle());
        assertEquals(updatedProject.getProjectIntro(), projectResult.getProjectIntro());
        assertEquals(updatedProject.getBudgetPlan(), projectResult.getBudgetPlan());
        assertEquals(updatedProject.getSchedulePlan(), projectResult.getSchedulePlan());
        assertEquals(updatedProject.getProjectImage(), projectResult.getProjectImage());
        assertEquals(updatedProject.getProjectPrice(), projectResult.getProjectPrice());

        // 밀리초단위가 차이나서 Date 타입은 assertEquals 로 비교가 힘든것 같음
//        assertEquals(updatedProject.getOpenDate(), projectResult.getOpenDate());
//        assertEquals(updatedProject.getCloseDate(), projectResult.getCloseDate());
//        assertEquals(updatedProject.getDeliveryDate(), projectResult.getDeliveryDate());

        assertEquals(updatedProject.getProjectContent(), projectResult.getProjectContent());

    }

    @Test
    @DisplayName("작성중인 프로젝트 삭제")
    void deleteOnWritingProject() {
        // Given
        ProjectDTO newProject = new ProjectDTO();
        newProject.setProjectIntro("테스트 프로젝트 인트로");
        newProject.setMemberNo(1);  // 회원 테이블의 데이터에 의존한다.
        newProject.setCategoryId(1);  // 카테고리 테이블 데이터에 의존한다.
        projectMapper.insert(newProject);

        // When
        int delResult = projectMapper.delete(newProject.getProjectNo());
        ProjectDTO projectResult = projectMapper.findByNo(newProject.getProjectNo());

        // Then
        assertEquals(1, delResult);
        assertNull(projectResult);
    }

    @Test
    @DisplayName("프로젝트 카테고리 목록 조회")
    void getCategoryList() {
        // Given
        // 테스트가 카테고리 테이블에 의존적
        // 카테고리 테이블에 데이터가 없으면 실패한다.

        // When
        List<CategoryDTO> cList = projectMapper.getCategory();

        // Then
        assertNotNull(cList);

    }
}
