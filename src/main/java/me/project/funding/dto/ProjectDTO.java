package me.project.funding.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class ProjectDTO {
    private int projectNo;
    private int memberNo;
    private int categoryId;
    private String projectTitle;
    private String projectIntro;
    private String budgetPlan;
    private String schedulePlan;
    private String projectImage;
    private int projectPrice;
    private Date openDate;
    private Date closeDate;
    private Date deliveryDate;
    private String projectContent;
    private int fundPrice;
    private int projectStep;

    // Member 테이블과 조인할 경우
    @ToString.Exclude
    MemberDTO member = new MemberDTO();
}
