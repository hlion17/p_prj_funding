package me.project.funding.service.impl;


import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.CategoryDTO;
import me.project.funding.dto.MemberDTO;
import me.project.funding.dto.ProjectDTO;
import me.project.funding.mapper.MemberMapper;
import me.project.funding.mapper.ProjectMapper;
import me.project.funding.service.face.MemberService;
import me.project.funding.service.face.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private MemberMapper memberMapper;

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
}
