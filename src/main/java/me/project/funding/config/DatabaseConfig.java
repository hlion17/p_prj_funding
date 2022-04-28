package me.project.funding.config;

import com.zaxxer.hikari.util.DriverDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "me.project.funding")  // Mapper interface 를 스캔할 위치
@EnableTransactionManagement  // DataSourceTransactionManager Bean 을 Transaction Manager 로 사용
public class DatabaseConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        // mybatis bean 으로 등록할 factory 객체 - mybatis 수행객체 (sqlSession) 생성
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // database 정보 설정
        sessionFactory.setDataSource(dataSource);

        // DTO 에 @Alias 를 붙혀 typeAlias 설정을 가능하게 해주는 설정
//        sessionFactory.setTypeAliasesPackage("me.project.funding.dto");

//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // Mapper.xml 위치 설정 - 여기서 설정하면 작동 안함
        // application.properties 에서 설정
//        sessionFactory.setMapperLocations(resolver.getResource("classpath:mybatis/mapper/*.xml"));
        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
}