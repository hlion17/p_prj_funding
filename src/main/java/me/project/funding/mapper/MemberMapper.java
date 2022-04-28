package me.project.funding.mapper;

import me.project.funding.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    MemberDTO test(@Param("id") String id);
}
