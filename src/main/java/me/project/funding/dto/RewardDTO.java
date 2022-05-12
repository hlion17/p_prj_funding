package me.project.funding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class RewardDTO {
    private int rewardNo;
    private int projectNo;
    private int rewardPrice;
    private int rewardAmount;
    private String rewardName;
    private String rewardIntro;
    private int rewardState;
}
