package me.project.funding.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.RewardDTO;
import me.project.funding.dto.RewardOptionDTO;
import me.project.funding.mapper.RewardMapper;
import me.project.funding.service.face.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

    @Override
    public String createOption(RewardOptionDTO option) {
        int result = rewardMapper.insertOption(option);
        if (result == 1) {
            log.info("옵션 생성 성공");
            return "success";
        } else {
            log.error("옵션 생성 실패");
            return "fail";
        }
    }

    @Override
    public List<RewardOptionDTO> getRewardOptions(int projectNo) {
        return rewardMapper.findAllOptions(projectNo);
    }

    @Override
    public void deleteOption(int optionNo) {
        int result = rewardMapper.deleteOption(optionNo);
        if (result == 1) {
            log.info("옵션 삭제 성공");
        } else {
            log.error("옵션 삭제 실패");
        }
    }

    @Override
    public List<RewardDTO> getRewards(int projectNo) {
        return rewardMapper.findAllRewards(projectNo);
    }

    @Override
    public void deleteReward(int rewardNo) {
        int result = rewardMapper.delete(rewardNo);
        if (result == 1) {
            log.info("리워드 삭제 성공");
        } else {
            log.error("리워드 삭제 실패");
        }
    }
}
