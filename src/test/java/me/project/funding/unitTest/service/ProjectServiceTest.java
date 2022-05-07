package me.project.funding.unitTest.service;

import me.project.funding.dto.CategoryDTO;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectDTO;
import me.project.funding.mapper.MemberMapper;
import me.project.funding.mapper.ProjectMapper;
import me.project.funding.service.face.ProjectService;
import me.project.funding.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @InjectMocks
    ProjectService projectService = new ProjectServiceImpl();

    @Mock
    ProjectMapper projectMapper;

    @Mock
    MemberMapper memberMapper;

    @Test
    @DisplayName("작성중인 프로젝트 리스트 가져오기")
    void getOnWritingProject() {
        // given
        String loginId = "testId";
        MemberDTO member = new MemberDTO();
        member.setId(loginId);

        MemberDTO foundMember = new MemberDTO();
        foundMember.setMemberNo(100);
        foundMember.setId(member.getId());

        ArrayList<ProjectDTO> list = new ArrayList<>();

        given(memberMapper.findById(any())).willReturn(foundMember);
        given(projectMapper.findOnWritingProject(any())).willReturn(list);

        // when
        List<ProjectDTO> result = projectService.getOnWritingProject(member);

        // then
        assertNotNull(result);
        verify(memberMapper, times(1)).findById(any());
        verify(projectMapper, times(1)).findOnWritingProject(any());
    }

    @ParameterizedTest(name = "{index} {displayName}")
    @DisplayName("하나의 프로젝트 가져오기")
    @ValueSource(ints = {1, 2, 3})
    void getProject(int projectNo) {
        // 따로 로직이 있는 것이 아니라서 테스트 할 것이 없어보이는데
        // 단위테스트에서는 뭘 테스트 해야할지 모르겠다.
        // 아래는 무조건 성공하는 테스트라서 의미가 없는 것이 아닐까?

        // Given
        given(projectMapper.findByNo(anyInt())).willReturn(new ProjectDTO());

        // When
        ProjectDTO result = projectService.getProject(projectNo);

        // Then
        assertNotNull(result);
        verify(projectMapper, times(1)).findByNo(anyInt());

    }

    @Test
    @DisplayName("프로젝트 생성")
    void createProject() {
        // Given
        String projectIntro = "테스트 프로젝트 인트로";
        ProjectDTO project = new ProjectDTO();
        project.setProjectIntro(projectIntro);

        given(projectMapper.insert(project))
                .willReturn(1)
                .willReturn(0)
                .willReturn(2);

        // When & Then
        // return 1
        assertDoesNotThrow(() -> {projectService.createProject(project);});

        // return 0
        assertThrows(RuntimeException.class
                , () -> {
                    projectService.createProject(project);}
                , "프로젝트 생성 실패");

        // return 2
        assertThrows(RuntimeException.class
                , () -> {
                    projectService.createProject(project);}
                , "프로젝트 생성 실패");

        // Mock 객체 호출 횟수 확인
        verify(projectMapper, times(3)).insert(any());
    }

    @Test
    @DisplayName("작성중인 프로젝트 내용을 업데이트 한다.")
    void updateOnWritingProject() {
        // Given
        // Mock 객체를 사용하기 때문에 해당 더미데이터는 의미가 없음
        ProjectDTO project = new ProjectDTO();
        project.setProjectNo(100);  // 100 번 프로젝트를 업데이트
        project.setMemberNo(2);  // 2 번 회원의 프로젝트
        project.setCategoryId(1);  //  1번 카테고리에 속한 프로젝트
        project.setProjectTitle("테스트 프로젝트 제목");
        project.setProjectIntro("테스트 프로젝트 인트로");
        project.setBudgetPlan("테스트 프로젝트 예산 계획");
        project.setSchedulePlan("테스트 스케쥴 플랜");
        project.setProjectImage("테스트 대표사진 경로");
        project.setProjectPrice(2000000);
        project.setOpenDate(new Date());
        project.setCloseDate(new Date());
        project.setDeliveryDate(new Date());
        project.setProjectContent("테스트 프로젝트 내용");

        given(projectMapper.update(any()))
                .willReturn(1)
                .willReturn(0)
                .willReturn(2);

        // When & Then
        // return 1
        assertDoesNotThrow(() -> {projectService.updateOnWritingProject(project);});

        // return 0
        assertThrows(RuntimeException.class
                , () -> {
                    projectService.updateOnWritingProject(project);}
                , "프로젝트 생성 실패");

        // return 2
        assertThrows(RuntimeException.class
                , () -> {
                    projectService.updateOnWritingProject(project);}
                , "프로젝트 생성 실패");

        // projectStep != 0
        project.setProjectStep(1);
        assertThrows(RuntimeException.class
                , () -> {
                    projectService.updateOnWritingProject(project);}
                , "프로젝트 진행상태가 올바르지 않습니다.");

        // Mock 객체 호출 횟수 확인
        // projectStep != 0 인 경우는 호출 횟수에 포함되면 안됨
        verify(projectMapper, times(3)).update(any());

    }

    @DisplayName("작성중인 프로젝트를 삭제한다.")
    @ParameterizedTest
    @ValueSource(ints = {1})
    void deleteOnWritingProject(int projectNo) {
        // Given
        ProjectDTO foundProject = new ProjectDTO();
        foundProject.setProjectStep(0);

        given(projectMapper.findByNo(projectNo))
                .willReturn(foundProject);
        given(projectMapper.delete(anyInt()))
                .willReturn(1)
                .willReturn(0)
                .willReturn(2);

        // When & Then
        // return 1
        assertDoesNotThrow(() -> {projectService.deleteOnWritingProject(projectNo);});

        // return 0
        assertThrows(RuntimeException.class
                , () -> {
                    projectService.deleteOnWritingProject(projectNo);}
                , "프로젝트 생성 실패");

        // return 2
        assertThrows(RuntimeException.class
                , () -> {
                    projectService.deleteOnWritingProject(projectNo);}
                , "프로젝트 생성 실패");

        // projectStep != 0
        foundProject.setProjectStep(1);
        assertThrows(RuntimeException.class
                , () -> {
                    projectService.deleteOnWritingProject(projectNo);}
                , "프로젝트 진행상태가 올바르지 않습니다.");

        // Mock 객체 호출 횟수 확인
        // 프로젝트 진행상태 검증 실패 테스트 케이스는 포함하지 않아야 한다.
        verify(projectMapper, times(3)).delete(anyInt());

    }

    @Test
    @DisplayName("프로젝트 카테고리 목록을 가져온다.")
    void getCategoryList() {
        // 반드시 성공하는 테스트 의미 없는거 같다.

        // Given
        List<CategoryDTO> list = new ArrayList<>();

        given(projectMapper.getCategory()).willReturn(list);

        // When
        List<CategoryDTO> result = projectService.getCategoryList();

        // Then
        assertNotNull(result);

    }


}
