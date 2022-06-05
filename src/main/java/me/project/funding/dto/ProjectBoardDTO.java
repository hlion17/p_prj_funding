package me.project.funding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class ProjectBoardDTO {
    private int boardNo;
    private int projectNo;
    private String content;
    private Date regDate;
    private int writerNo;

    private MemberDTO member;
}
