package me.project.funding.unitTest.controller;

import me.project.funding.controller.ProjectController;
import me.project.funding.dto.CategoryDTO;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectDTO;
import me.project.funding.service.face.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    ProjectController projectController = new ProjectController();

    @Mock
    ProjectService projectService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    @DisplayName("프로젝트 생성 스타트 페이지")
    void projectStartPage() throws Exception {
        // given
        List<ProjectDTO> list = new ArrayList<>();
        List<CategoryDTO> categoryList = new ArrayList<>();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginId", "testId");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/project/start")
                .session(session);

        // projectService stubbing
        given(projectService.getOnWritingProject(any())).willReturn(list);
        given(projectService.getCategoryList()).willReturn(categoryList);

        // when
        ResultActions result = this.mockMvc.perform(request);

        // then
        MvcResult mvcResult = result.andExpectAll(
                status().isOk()
                , view().name("project/start")
                , model().attribute("list", list)
                , model().attribute("cList", categoryList)
        ).andReturn();

        // 로그인 세션 확인하는 코드가 필요하려나..
        Object loginId = mvcResult.getRequest().getSession().getAttribute("loginId");
        assertNotNull(loginId);
    }

    // @Test 어노테이션과 중복되면 안됨
    @Disabled
    @DisplayName("프로젝트 에디터 페이지")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {1, 2})
    void projectEditorPage(int projectNo) throws Exception {
        // given
        MemberDTO member = new MemberDTO();
        member.setId("testId");

        ProjectDTO project = new ProjectDTO();
        project.setProjectNo(projectNo);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginId", member.getId());
        RequestBuilder request = MockMvcRequestBuilders
                .get("/project/editor/" + projectNo)
                .param("projectNo", String.valueOf(projectNo))
                .session(session);

        given(projectService.getProject(projectNo)).willReturn(project);

        // when
        ResultActions result = this.mockMvc.perform(request);

        // then
        result.andExpectAll(
                status().isOk()
                , view().name("project/editor")
                , model().attributeExists("project")
        );
    }

    @Disabled
    @Test
    @DisplayName("프로젝트 생성")
    void creatProject() throws Exception {
        // Given
        ProjectDTO project = new ProjectDTO();
        project.setProjectIntro("테스트 프로젝트 인트로");
        project.setCategoryId(1);

        ProjectDTO foundProject = new ProjectDTO();
        foundProject.setProjectIntro("테스트 프로젝트 인트로");

        // 매개변수 O
        RequestBuilder request = MockMvcRequestBuilders
                .post("/project/create")
                .param("projectIntro", project.getProjectIntro())
                .param("categoryId", String.valueOf(project.getCategoryId()));

        // 매개변수 X
        RequestBuilder requestNoParam = MockMvcRequestBuilders
                .post("/project/create");

        willDoNothing().given(projectService).createProject(any());
        // projectService.createProject 수행 시 projectNo가 생성
        foundProject.setProjectNo(100);
        given(projectService.getProject(anyInt())).willReturn(foundProject);

        // When
        ResultActions result = this.mockMvc.perform(request);
        // 인트로를 작성하지 않고 프로젝트 생성 시도하는 경우 테스트
        try {
            this.mockMvc.perform(requestNoParam);
        } catch (NestedServletException e) {
            assertThrows(RuntimeException.class, () -> {
                throw e.getCause();
            }, "프로젝트 생성 시도 시 필요한 변수가 부족합니다.");
        }

        // Then
        result.andExpectAll(
                redirectedUrl("/project/editor/" + foundProject.getProjectNo())
                , flash().attribute("project", foundProject)
        );
        verify(projectService, times(1)).createProject(any());
        verify(projectService, times(1)).getProject(anyInt());


    }

    @Disabled
    @Test
    @DisplayName("프로젝트 업데이트")
    void updateProject() throws Exception {

        // MultiValueMap 의 value 가 List Type 이라서 커멘드 객체 바인딩이 안되는것 같다.
        /*
        ObjectMapper objectMapper = new ObjectMapper();

        MemberDTO member = new MemberDTO();
        member.setMemberNo(1);

        ProjectDTO project = new ProjectDTO();
        project.setProjectNo(100);  // 100 번 프로젝트를 업데이트
        project.setMemberNo(member.getMemberNo());
        project.setCategoryId(1);
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

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> map = objectMapper.convertValue(project, new TypeReference<Map<String, String>>() {});
        params.setAll(map);
        */

        // Given
        RequestBuilder request = MockMvcRequestBuilders
                .post("/project/update")
                .param("projectNo", "100")
                .param("memberNo", "2")
                .param("categoryNo", "1")
                .param("projectTitle", "테스트 프로젝트 제목")
                .param("projectIntro", "테스트 프로젝트 인트로")
                .param("BudgetPlan", "테스트 프로젝트 예산 계획")
                .param("SchedulePlan", "테스트 스케쥴 플랜")
                .param("ProjectImage", "/project/img/thumbnail/test.jpg")
                .param("ProjectPrice", "2000000")
                .param("OpenDate", "2022/06/01")
                .param("CloseDate", "2022/06/30")
                .param("DeliveryDate", "2022/07/01")
                .param("ProjectContent", "테스트 프로젝트 내용");


        // projectService updateProject 수행

        // When
        ResultActions result = this.mockMvc.perform(request);

        // Then
        result.andExpectAll(
                // 추후에 마이페이지 개발 시 마이페이지로 리다이렉트 되도록 변경 할 수 있음
                redirectedUrl("/")
        );
        // update 서비스가 수행되었는지 확인
        verify(projectService, times(1)).updateOnWritingProject(any());

    }

    @Disabled
    @DisplayName("프로젝트 삭제")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {1, 0})
    void deleteProject(int projectNo) throws Exception {
        // Given
        RequestBuilder request = MockMvcRequestBuilders
                .post("/project/delete")
                .param("projectNo", String.valueOf(projectNo));

        // When
//        if (projectNo < 1) {
////            assertThrows(IllegalArgumentException.class, () -> this.mockMvc.perform(request));
//            org.assertj.core.api.Assertions.assertThatThrownBy(
//                            () -> this.mockMvc.perform(request))
//                    .hasCause(new IllegalArgumentException("부적합한 프로젝트 식별자"));
//        }

        try {
             this.mockMvc.perform(request).andExpectAll(
                     redirectedUrl("/")
             );
        } catch (NestedServletException e) {
            assertThrows(IllegalArgumentException.class, () -> {
                throw e.getCause();
            });
        }

        // Then

        // delete 서비스가 수행되었는지 확인
        if (projectNo < 1) {
            verify(projectService, times(0)).deleteOnWritingProject(projectNo);
        } else {
            verify(projectService, times(1)).deleteOnWritingProject(projectNo);
        }
    }

}
