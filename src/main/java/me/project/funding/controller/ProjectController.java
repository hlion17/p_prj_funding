package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.commons.Pagination;
import me.project.funding.dto.*;
import me.project.funding.mapper.ProjectMapper;
import me.project.funding.service.face.MemberService;
import me.project.funding.service.face.ProjectService;
import me.project.funding.service.face.RewardService;
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
import java.lang.reflect.Member;
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
    private ProjectMapper projectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RewardService rewardService;

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
     *
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
     *
     * @param projectNo 작성중인 프로젝트 식별자
     * @return 작성중인 프로젝트 정보
     */
    @GetMapping("/project/editor/{projectNo}/{editorCategory}")
    public ModelAndView projectEditorPage(@PathVariable(value = "projectNo") int projectNo
            , @PathVariable("editorCategory") String editorCategory) {
        log.info("[/project/editor/{}][GET]", projectNo);
        // 요청 파라미터 분석
        log.info("projectNo: {}", projectNo);
        log.info("editorCategory: {}", editorCategory);
        String viewName = "";
//        System.out.println("리워드 카테고리 값: " + rewardCategory);
        switch (editorCategory) {
            case "basic":
                viewName = "project/editor/basic";
                break;
            case "content":
                viewName = "project/editor/content";
                break;
            case "schedule":
                viewName = "project/editor/schedule";
                break;
            case "budget":
                viewName = "project/editor/budget";
                break;
            case "reward":
                viewName = "project/editor/reward";
                break;
            case "reward_option":
                System.out.println("옵션 페이지");
                viewName = "project/editor/reward_option";
                break;
        }
        log.info("viewName: {}", viewName);

        // 파라미터 검증
        if (projectNo < 1) {
            log.error("적절하지 않은 프로젝트 식별자로 페이지 접근");
            throw new IllegalArgumentException("부적합한 프로젝트 식별자");
        }

        ModelAndView mav = new ModelAndView(viewName);
//        ModelAndView mav = new ModelAndView("project/editor");
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
     *
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
     *
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
     *
     * @param projectNo 삭제할 프로젝트 식별값
     * @return 메인페이지로 리다이렉트
     */
    @PostMapping("/project/delete")
    @ResponseBody
    public Map<String, Object> deleteProject(int projectNo) {
        log.info("[/project/delete][POST]");
        Map<String, java.lang.Object> jsonResult = new HashMap<>();
        //ModelAndView mav = new ModelAndView("redirect:/");

        // 파라미터 검증
        if (projectNo < 1) {
            log.error("적절하지 않은 프로젝트 식별자로 페이지 접근");
            throw new IllegalArgumentException("부적합한 프로젝트 식별자");
        }
        log.info("프로젝트 업데이트 파라미터: {}", projectNo);

        // 작성중인 프로젝트 삭제
        //projectService.deleteOnWritingProject(projectNo);
        int result = projectService.removeProject(projectNo);

        if (result == 1) {
            jsonResult.put("message", "프로젝트 삭제 성공");
        } else {
            jsonResult.put("message", "프로젝트 삭제 실패");
        }

        return jsonResult;
    }

    // not tested

    /**
     * 파일 정보를 받아 업로드 처리하고 업로드된 파일 url 반납
     *
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
     *
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
     *
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
        log.info("조회된 프로젝트: {}", project);

        // 후원자 조회
        Integer contributors = projectMapper.getContributorsCntByProjectNo(projectNo);
        log.info("후원자수: {}", contributors);

        // 리워드 조회
        List<RewardDTO> rewards = rewardService.getRewards(projectNo);
        log.info("조회된 리워드: {}", rewards);

        // View 전달 데이터
        model.put("project", project);
        model.put("contributors", contributors);
        model.put("rewards", rewards);
        return mav;
    }

    // not tested
    @GetMapping("/projects/like")
    @ResponseBody
    public Map<String, Object> getLike(int projectNo, HttpSession session) {
        log.info("[/projects/like][GET]");
        log.info("요청 파라미터: {}", projectNo);
        Map<String, Object> jsonResponse = new HashMap<>();

        // 요청 파라미터 검증 (프로젝트 식별값)
        if (projectNo < 1) {
            log.error("잘못된 프로젝트 식별값");
            jsonResponse.put("result", "fail");
            jsonResponse.put("message", "잘못된 프로젝트 요청");
            return jsonResponse;
        }
        // 로그인 세션 확인
        if (session.getAttribute("loginMemberNo") == null) {
            log.error("로그인 정보 없음");
            jsonResponse.put("result", "noLoginInfo");
            jsonResponse.put("message", "로그인 정보 없음");
            return jsonResponse;
        }
        // 로그인 회원 식별값
        int memberNo = (Integer) session.getAttribute("loginMemberNo");

        if (projectService.getLikeResult(projectNo, memberNo) == 1) {
            log.info("좋아요 조회");
            jsonResponse.put("result", "success");
        } else {
            log.info("조회된 좋아요 없음");
            jsonResponse.put("result", "fail");
        }

        return jsonResponse;
    }

    // not tested
    @PostMapping("/projects/like")
    @ResponseBody
    public Map<String, Object> likeCheck(int projectNo, HttpSession session) {
        log.info("[/projects/like][GET]");
        Map<String, Object> jsonResponse = new HashMap<>();

        // 요청 파라미터 확인 (프로젝트 식별값)
        if (projectNo < 1) {
            log.error("유효하지 않은 프로젝트 식별값");
            jsonResponse.put("error", "잘못된 클라이언트 요청");
            jsonResponse.put("message", "잘못된 프로젝트 입니다.");
            return jsonResponse;
        }
        log.info("프로젝트 식별값: {}", projectNo);

        // 로그인 검증
        if (session.getAttribute("loginMemberNo") == null
                || "".equals(session.getAttribute("loginMemberNo"))) {
            log.error("세션 로그인 정보가 존재하지 않습니다.");
            jsonResponse.put("error", "잘못된 클라이언트 요청");
            jsonResponse.put("msg", "로그인 세션 정보가 존재하지 않음");
            return jsonResponse;
        }
        log.info("로그인 회원 식별값: {}", session.getAttribute("loginMemberNo"));

        // 회원 식별값 (세션)
        int memberNo = (Integer) session.getAttribute("loginMemberNo");

        // 좋아요 여부 조회
        // - 조회 결과가 없으면 insert, 있으면 delete
        return projectService.checkLike(projectNo, memberNo, jsonResponse);
    }

    @GetMapping("/projects/{projectNo}/community")
    public ModelAndView getCommunityPage(@PathVariable int projectNo) {
        log.info("[/projects/{}/community][GET]", projectNo);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("project/detail_community");

        // 커뮤니티 게시글 조회
        List<ProjectBoardDTO> list = projectService.getCommunityPosts(projectNo);
        log.info("조회된 커뮤니티 게시글: {}", list);
        mav.addObject("list", list);
        return mav;
    }

    @PostMapping("/project/board/write")
    @ResponseBody
    public Map<String, Object> writeProjectBoard(ProjectBoardDTO board, HttpSession session) {
        log.info("[/project/board/write][POST]");
        Map<String, Object> jsonResult = new HashMap<>();

        // 로그인 세션 회원 확인
        if (session.getAttribute("loginMemberNo") == null) {
            log.error("로그인 정보 없음");
            jsonResult.put("error", "로그인 에러");
            jsonResult.put("message", "로그인이 필요한 서비스입니다.");
            return jsonResult;
        }

        board.setWriterNo((Integer) session.getAttribute("loginMemberNo"));

        log.info("파라미터 확인: {}", board);
        int result = projectService.saveProjectBoard(board);

        if (result == 1) {
            jsonResult.put("result", "success");
        }

        return jsonResult;
    }
}
