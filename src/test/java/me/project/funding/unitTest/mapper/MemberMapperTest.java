package me.project.funding.unitTest.mapper;

import me.project.funding.dto.MemberDTO;
import me.project.funding.mapper.MemberMapper;
import me.project.funding.mapper.TestMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private TestMapper testMapper;

    @Test
    void 매퍼작동테스트() {
        MemberDTO member = memberMapper.test("tester");
        MemberDTO member2 = testMapper.test2();

        System.out.println("member: " + member);
        System.out.println("member2: " + member2);
        Assertions.assertEquals(9000, member.getEmpno());
        Assertions.assertNotNull(member2);
    }
}
