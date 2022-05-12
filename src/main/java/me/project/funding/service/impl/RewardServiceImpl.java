package me.project.funding.service.impl;

import me.project.funding.mapper.RewardMapper;
import me.project.funding.service.face.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardServiceImpl implements RewardService {
    @Autowired
    RewardMapper rewardMapper;
}
