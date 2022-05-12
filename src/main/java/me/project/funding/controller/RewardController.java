package me.project.funding.controller;

import me.project.funding.service.face.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RewardController {
    @Autowired
    private RewardService rewardService;
}
