package me.project.funding.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import me.project.funding.commons.Pagination;
import me.project.funding.dto.CategoryDTO;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectDTO;
import me.project.funding.service.face.MemberService;
import me.project.funding.service.face.ProjectService;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MemberService memberService;

    // Date 타입 바인더
    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 세션에 로그인된 사용자 데이터를 가지고
     * 사용자가 작성중인 프로젝트 목록, 프로젝트 카테고리 목록을 가져와서
     * start.jsp 페이지를 렌더링 한다.
     * @param session 로그인 사용자 데이터
     * @return 진행중인 프로젝트 목록, 카테고리 목록
     */
    @GetMapping("/project/start")
    public ModelAndView projectStartPage(HttpSession session) {
        log.info("[/project/start][GET]");

        // 로그인 세션 검증
        // 인터셉터에서 인증 처리할 수 있을 것 같다.
        MemberDTO member = null;
        if (session.getAttribute("loginId") == null) {
            log.error("로그인 정보가 없음");
            throw new RuntimeException("잘못된 접근입니다.");
        } else {
            member = new MemberDTO();
            member.setId((String) session.getAttribute("loginId"));
        }

        ModelAndView mav = new ModelAndView("project/start");
        Map<String, Object> model = mav.getModel();

        // 작성중인 프로젝트가 있는 경우 불러온다.
        List<ProjectDTO> list = projectService.getOnWritingProject(member);
        log.info("작성중인 프로젝트 목록: {}", list);

        // 프로젝트 카테고리 목록을 불러온다.
        List<CategoryDTO> categoryList = projectService.getCategoryList();
        log.info("조회된 프로젝트 카테고리 목록: {}", categoryList);

        // View 전달 데이터
        model.put("list", list);
        model.put("cList", categoryList);

        return mav;
    }

    /**
     * 작성중인 프로젝트를 편집하는 페이지에 접근한다.
     * @param projectNo 작성중인 프로젝트 식별자
     * @return 작성중인 프로젝트 정보
     */
    @GetMapping("/project/editor/{projectNo}")
    public ModelAndView projectEditorPage(@PathVariable int projectNo) {
        log.info("[/project/editor/{}][GET]", projectNo);

        // 파라미터 검증
        if (projectNo < 1) {
            log.error("적절하지 않은 프로젝트 식별자로 페이지 접근");
            throw new IllegalArgumentException("부적합한 프로젝트 식별자");
        }

        ModelAndView mav = new ModelAndView("project/editor");
        Map<String, Object> model = mav.getModel();

        // 프로젝트 정보 가져오기
        ProjectDTO project = projectService.getProject(projectNo);
        log.info("조회된 프로젝트: {}", project);

        // 카테고리 정보 가져오기
        List<CategoryDTO> cList = projectService.getCategoryList();
        log.info("카테고리 목록: {}", cList);

        // View 전달 데이터
        model.put("project", project);
        model.put("cList", cList);

        return mav;
    }

    /**
     * 프로젝트 인트로 정보로 프로젝트를 생성하고 해당 프로젝트 정보 반환
     * 프로젝트 편집 화면으로 이동(redirect)
     * @param project 프로젝트 인트로 정보
     * @return
     */
    @PostMapping("/project/create")
    public ModelAndView createProject(ProjectDTO project, RedirectAttributes rtts) {
        log.info("[/project/create][POST]");
        // 매개변수 검증
        // category 를 단순 int 값으로 검증하면 존재하지 않는 카테고리 번호 검증할 수 없다.
        // -> enum 사용 고려
        log.info("파라미터: {}", project);
        if (project.getProjectIntro() == null || project.getCategoryId() < 1) {
            log.error("프로젝트 생성 오류: 인트로 매개변수 부재");
            throw new RuntimeException("프로젝트 생성 시도 시 필요한 변수가 부족합니다.");
        }
        log.info("파라미터: {}", project.getProjectIntro());

        if (project.getMemberNo() < 1) {
            log.error("회원 식별값 없음");
            throw new RuntimeException("프로젝트 생성 시도 시 필요한 변수가 부족합니다.");
        }

        // 프로젝트 셍성
        // 결과로 DTO 에 projectNo 생성
        projectService.createProject(project);
        log.info("프로젝트 생성 결과: {}", project.getProjectNo());

        // 프로젝트 조회
        ProjectDTO result = projectService.getProject(project.getProjectNo());
        log.info("프로젝트 조회 결과: {}", result);

        // View 전달 데이터
        ModelAndView mav = new ModelAndView("redirect:/project/editor/" + result.getProjectNo());
        rtts.addFlashAttribute("project", result);
        return mav;
    }

    /**
     * 작성중인 프로젝트의 정보를 업데이트 후 메인으로 이동한다(redirect).
     * @param project 작성중인 프로젝트에 대한 업데이트 정보
     * @return 메인으로 리다이렉트
     */
    @PostMapping("/project/update")
    public ModelAndView updateProject(ProjectDTO project) {
        log.info("[/project/update][POST]");
        ModelAndView mav = new ModelAndView("redirect:/");

        log.info("파라미터: {}", project);

        // 파라미터 검증
        // Validation 에 대한 공부 필요, Spring Validation
        // DTO 검증에 대한 로직은 DTO 안에 있을 필요가 있다.
        if (project.getProjectNo() < 1) {
            log.error("적절하지 않은 프로젝트 식별자로 페이지 접근");
            throw new IllegalArgumentException("부적합한 프로젝트 식별자");
        }
        log.info("프로젝트 업데이트 파라미터: {}, {}", project.getProjectNo(), project);

        // 작성중인 프로젝트에 대한 업데이트
        projectService.updateOnWritingProject(project);

        // 마이페이지 구현 후 마이페이지로 리다이렉트 할 경우 RedirectAttribute 필요
        // 임시 저장에도 해당 컨트롤러를 사용할 계획이 있다면 JSON 반환 검토
        return mav;
    }

    /**
     * 작성중인 프로젝트를 삭제
     * @param projectNo 삭제할 프로젝트 식별값
     * @return 메인페이지로 리다이렉트
     */
    @PostMapping("/project/delete")
    public ModelAndView deleteProject(int projectNo) {
        log.info("[/project/delete][POST]");
        ModelAndView mav = new ModelAndView("redirect:/");

        // 파라미터 검증
        if (projectNo < 1) {
            log.error("적절하지 않은 프로젝트 식별자로 페이지 접근");
            throw new IllegalArgumentException("부적합한 프로젝트 식별자");
        }
        log.info("프로젝트 업데이트 파라미터: {}", projectNo);

        // 작성중인 프로젝트 삭제
        projectService.deleteOnWritingProject(projectNo);

        return mav;
    }

    // not tested
    /**
     * 파일 정보를 받아 업로드 처리하고 업로드된 파일 url 반납
     * @param file 업로드할 파일의 정보
     * @return 업로드한 파일 url
     */
    @PostMapping("/project/fileUpload")
    @ResponseBody
    public Map<String, Object> fileUpload(MultipartFile file) {
        log.info("[/project/fileUpload][POST]");
        Map<String, Object> jsonMap = new HashMap<>();

        // 요청 파라미터 검증
        // 빈 파일 업로드 확인
        if (file.getSize() <= 0) {
            log.info("업로드 시도한 파일이 비어있음");
            throw new RuntimeException("파일크기가 0 보다 작습니다.");
        }

        // 업로드 경로
        String path = "uploads";
        // 파일 업로드
        String fileName = projectService.uploadFile(file, path);

        // 파일 url 반납
        jsonMap.put("fileUrl", "/uploads/" + fileName);
        return jsonMap;
    }

    // not tested
    /**
     * ck 에디터 이미지 파일을 올리는 요청 처리
     * @param file 업로드할 이미지 파일
     * @return 업로드 처리결과 JSON(성공여부, 파일이름(storedName), FileUrl)
     */
    @PostMapping("/ck/upload")
    @ResponseBody
    public Map<String, Object> ckUpload(@RequestParam("upload") MultipartFile file) {
        log.info("[/ck/upload][POST]");
        Map<String, Object> jsonMap = new HashMap<>();

        // 요청 파라미터 검증
        // 빈 파일 업로드 확인
        if (file.getSize() <= 0) {
            log.info("업로드 시도한 파일이 비어있음");
            throw new RuntimeException("파일크기가 0 보다 작습니다.");
        }

        // 업로드 경로
        String path = "uploads";
        // 파일 업로드
        String fileName = projectService.uploadFile(file, path);

        // 반환 json
        jsonMap.put("uploaded", 1);
        jsonMap.put("fileName", fileName);
        jsonMap.put("url", "/" + path + "/" + fileName);
        log.info("jsonResult: {}", jsonMap);
        return jsonMap;
    }

    // not tested
    @GetMapping("/project/list")
    public String list(Pagination pagination, Model model) {
        log.info("[/project/list][GET]");

        // 프로젝트 리스트
        List<ProjectDTO> list = projectService.getPageList(pagination);

        // 카테고리 리스트
        List<CategoryDTO> cList = projectService.getCategoryList();

        model.addAttribute("cList", cList);
        log.info("카테고리 조회결과: {}", cList);
        model.addAttribute("list", list);
        log.info("조회결과: {}", list);
        model.addAttribute("pagination", pagination);
        log.info("pagination: {}", pagination);

        return "project/list";
    }

    // not tested
    /**
     * 개별 프로젝트 조회
     * @param projectNo 조회할 프로젝트 식별값
     * @return 조회된 프로젝트
     */
    @GetMapping("/project/{projectNo}")
    public ModelAndView detail(@PathVariable int projectNo) {
        log.info("[/project/{}][GET]", projectNo);
        ModelAndView mav = new ModelAndView("project/detail");
        Map<String, Object> model = mav.getModel();

        // 프로젝트 조회
        ProjectDTO project = projectService.getProject(projectNo);

        // View 전달 데이터
        model.put("project", project);
        return mav;
    }

    // not tested
    // 프로제트 삭제를 시험하기 위해 임시로 만듬
    @GetMapping("/project/my/{memberNo}")
    public ModelAndView getMyProject(@PathVariable int memberNo, HttpSession session) {
        log.info("[/project/my/{}][GET]", memberNo);
        ModelAndView mav = new ModelAndView("project/test");
        Map<String, Object> model = mav.getModel();

        if (memberNo < 1) {
            log.error("올바르지 않는 회원 식별값: {}", memberNo);
            throw new RuntimeException("요청 파라미터가 올바르지 않습니다.");
        }

        MemberDTO member = new MemberDTO();
        member.setMemberNo(memberNo);
        member.setId((String) session.getAttribute("loginId"));
        log.info("파라미터: {}", member);

        // 특정 회원의 프로젝트를 가져온다.
        List<ProjectDTO> result = projectService.getOnWritingProject(member);

        // View 전달 데이터
        model.put("list", result);
        log.info("조회된 결과: {}", result);
        return mav;
    }
}
