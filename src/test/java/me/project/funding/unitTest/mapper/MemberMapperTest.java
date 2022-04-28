package me.project.funding.unitTest.mapper;

import me.project.funding.dto.MemberDTO;
import me.project.funding.mapper.MemberMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void 매퍼작동테스트() {
        MemberDTO member = memberMapper.test("tester");

        System.out.println("member: " + member);
        Assertions.assertEquals(9000, member.getEmpno());
    }
}
