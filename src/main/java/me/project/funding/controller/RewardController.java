package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.RewardDTO;
import me.project.funding.service.face.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
