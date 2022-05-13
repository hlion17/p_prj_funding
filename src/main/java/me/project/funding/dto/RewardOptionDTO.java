package me.project.funding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class RewardOptionDTO {
    private int optionNo;
    private String optionName;
    private int optionCnt;
    private int projectNo;
}
