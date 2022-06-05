package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.commons.Pagination;
import me.project.funding.dto.ProjectDTO;
import me.project.funding.service.face.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(value = {"/main", "/"})
    public ModelAndView getMainPage() {
        log.info("[/main][GET]");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("main");

        List<ProjectDTO> likeRank = projectService.getRankedProject();
        // 전체 프로젝트 조회
        List<ProjectDTO> mainList = projectService.getPageList(new Pagination());
        mav.addObject("likeRank", likeRank);
        return mav;
    }



}
