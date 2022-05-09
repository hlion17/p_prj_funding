package me.project.funding.service.impl;


import lombok.extern.slf4j.Slf4j;
import me.project.funding.commons.Pagination;
import me.project.funding.dto.CategoryDTO;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectDTO;
import me.project.funding.mapper.MemberMapper;
import me.project.funding.mapper.ProjectMapper;
import me.project.funding.service.face.MemberService;
import me.project.funding.service.face.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    ServletContext context;

    @Override
    public List<ProjectDTO> getOnWritingProject(MemberDTO member) {
        // 세션 로그인 아이디로 회원의 식별값 조회
        MemberDTO foundMember = memberMapper.findById(member);
        // 작성중인 프로젝트 목록 조회
        return projectMapper.findOnWritingProject(foundMember);
    }

    @Override
    public ProjectDTO getProject(int projectNo) {
        // 프로젝트 조회
        return projectMapper.findByNo(projectNo);
    }

    @Override
    public void createProject(ProjectDTO project) {
        // 프로젝트 생성
        // 프로젝트 생성 수행 결과 project 참조에 projectNo 값이 들어가야 한다.
        // selectKey 사용
        int result = projectMapper.insert(project);

        // insert 결과 검증
        if (result == 1) {
            log.info("프로젝트 생성 성공");
        } else {
            log.error("프로젝트 생성 실패");
            throw new RuntimeException("프로젝트 생성 실패");
        }
    }

    @Override
    public void updateOnWritingProject(ProjectDTO project) {
        if (project.getProjectStep() != 0) {
            log.error("작성중인 프로젝트가 아닌 프로젝트로 업데이트 시도");
            throw new RuntimeException("프로젝트 진행상태가 올바르지 않습니다.");
        }

        // 작성중인 프로젝트 내용을 업데이트 한다.
        int result = projectMapper.update(project);

        // update 결과 검증
        if (result == 1) {
            log.info("프로젝트 업데이트 성공");
        } else {
            log.error("프로젝트 업데이트 실패");
            throw new RuntimeException("프로젝트 업데이트 실패");
        }
    }

    @Override
    public void deleteOnWritingProject(int projectNo) {
        // 프로젝트 진행상태 검증
        if (projectMapper.findByNo(projectNo).getProjectStep() != 0) {
            log.error("작성중인 프로젝트가 아닌 프로젝트로 삭제 시도");
            throw new RuntimeException("프로젝트 진행상태가 올바르지 않습니다.");
        }

        // 작성중인 프로젝트를 삭제한다.
        int result = projectMapper.delete(projectNo);

        // delete 결과 검증
        if (result == 1) {
            log.info("프로젝트 삭제 성공");
        } else {
            log.error("프로젝트 삭제 실패");
            throw new RuntimeException("프로젝트 삭제 실패");
        }
    }

    @Override
    public List<CategoryDTO> getCategoryList() {
        // 프로젝트 카테고리 목록을 가져온다.
        return projectMapper.getCategory();
    }

    // not tested
    @Override
    public String uploadFile(MultipartFile file, String path) {
        // 업로드 폴더 정보 생성
        String uploadPath = context.getRealPath(path);
        // 스프링 부트에서는 내장 톰켓을 사용하여 war 를 읽어들이기 떄문에
        // getRealPath 가 정확한 위치를 반환하지 않는다고 한다.
        log.info("서블릿 경로: {}", uploadPath);
        File uploadFolder = new File(uploadPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdir();
            log.info("업로드 경로 생성: {}", uploadFolder.getPath());
        }

        // 업로드 파일정보 생성
        String fileName = UUID.randomUUID().toString().split("-")[4];
        log.info("저장될 파일 이름: {}", fileName);
        File dest = new File(uploadPath, fileName);

        // 파일 업로드
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("파일 업로드 실패");
            e.printStackTrace();
        }
        return fileName;
    }

    // not tested

    @Override
    public List<ProjectDTO> getPageList(Pagination pagination) {
        log.info("pagination: {}", pagination);

        int total = projectMapper.getTotalCnt(pagination);

        // 한 페이지에 보여줄 프로젝트 수 10 개로 지정
        pagination.build(10, total);

        List<ProjectDTO> list = projectMapper.findAllByFilterAndOrder(pagination);

        return list;
    }
}
