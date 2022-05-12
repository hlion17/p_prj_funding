package me.project.funding.service.impl;

import me.project.funding.dto.RewardDTO;
import me.project.funding.mapper.RewardMapper;
import me.project.funding.service.face.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardServiceImpl implements RewardService {
    @Autowired
    RewardMapper rewardMapper;

    @Override
    public int createReward(RewardDTO reward) {
        return rewardMapper.insert(reward);
    }

    @Override
    public List<RewardDTO> getAllRewardByProjectNo(int projectNo) {
        return rewardMapper.findAllByProjectNo(projectNo);
    }
}
