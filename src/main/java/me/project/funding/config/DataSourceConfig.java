package me.project.funding.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource datasourceBuilder() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("oracle.jdbc.driver.OracleDriver");
        dataSourceBuilder.url("jdbc:oracle:thin:@localhost:1521:xe");
//        dataSourceBuilder.url("jdbc:oracle:thin:@122.46.39.213:11200:xe");
        dataSourceBuilder.username("kh5test");
        dataSourceBuilder.password("1234");
        return dataSourceBuilder.build();
    }
}
