package me.project.funding.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class FilterConfig {

     // 한글 인코딩 설정
     @Bean
     public FilterRegistrationBean encodingFilterBean() {
          FilterRegistrationBean registrationBean = new FilterRegistrationBean();
          CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
          characterEncodingFilter.setEncoding("UTF-8");
          registrationBean.setFilter(characterEncodingFilter);
          registrationBean.addUrlPatterns("/*");
          return registrationBean;
     }
}
