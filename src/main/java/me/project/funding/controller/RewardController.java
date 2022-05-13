package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.RewardDTO;
import me.project.funding.dto.RewardOptionDTO;
import me.project.funding.service.face.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class RewardController {
    @Autowired
    private RewardService rewardService;

    @PostMapping("/reward/create")
    public ModelAndView createReward(RewardDTO reward) {
        log.info("[/reward/create][POST]");
        log.info("요청 파라미터: {}", reward);

        // 요청 파라미터 검증
        if (reward.getProjectNo() < 1
                || reward.getRewardPrice() < 1
                || reward.getRewardIntro().equals("")) {
            log.error("프로젝트 식별값이 유효하지 않음");
            throw new RuntimeException("유효하지 않은 요청 전달값");
        }

        ModelAndView mav = new ModelAndView("jsonView");

        int result = rewardService.createReward(reward);

        if (result == 1) {
            mav.addObject("result", 1);
            mav.addObject("msg", "리워드 등록 성공");
            return mav;
        } else {
            log.info("리워드 등록 실패");
            mav.addObject("result", -1);
            mav.addObject("msg", "리워드 등록 실패");
            return mav;
        }
    }

    @PostMapping("/option/create")
    @ResponseBody
    public Map<String, String> createOption(RewardOptionDTO option) {
        log.info("[/option/create][POST]");
        log.info("요청 파라미터: {}", option);
        Map<String, String> resultMap = new HashMap<>();
        // 옵션 생성 결과(success, fail)
        String result = rewardService.createOption(option);

        // json 으로 옵션 생성 결과 반환
        resultMap.put("result", result);
        return resultMap;
    }

    @GetMapping("/options")
    public ModelAndView getOptions(int projectNo) {
        log.info("[/options][GET]");
        ModelAndView mav = new ModelAndView("jsonView");
        List<RewardOptionDTO> list = rewardService.getRewardOptions(projectNo);
        log.info("옵션 조회 결과: {}", list);
        mav.addObject("list",list);
        return mav;
    }

    @PostMapping("/options/delete")
    public ModelAndView deleteOption(int optionNo) {
        log.info("[/options/delete][POST]");
        log.info("요청 파라미터: {}", optionNo);
        ModelAndView mav = new ModelAndView("jsonView");
        rewardService.deleteOption(optionNo);
        return mav;
    }

}
