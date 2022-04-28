package me.project.funding.unitTest.mapper;

import me.project.funding.dto.MemberDTO;
import me.project.funding.mapper.MemberMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void 매퍼작동테스트() {
        MemberDTO member = memberMapper.test("tester");

        assertEquals(9000, member.getId());
    }
}
