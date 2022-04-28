package me.project.funding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.project.funding.etc.MemberType;

@Getter @Setter @ToString
public class MemberDTO {
    private int memberNo;
    private String id;
    private String pw;
    private String name;
    private String email;
    private String nick;
    private String phone;
    private int grade;
    // ENUM 연습용
    private MemberType status;
}
