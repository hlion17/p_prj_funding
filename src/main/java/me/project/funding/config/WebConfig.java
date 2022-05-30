package me.project.funding.config;

import me.project.funding.interceptor.LoginInterceptor;
import me.project.funding.interceptor.MemberInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 정적리소스 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("/uploads/")
                .setCachePeriod(20);
    }

    // JSON View Resolver 빈 등록
    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }
    // internal resource view 빈 등록
//    @Bean
//    public ViewResolver configureViewResolver() {
//        InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
//        viewResolve.setPrefix("/WEB-INF/jsp/");
//        viewResolve.setSuffix(".jsp");
//
//        return viewResolve;
//    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/WEB-INF/views/main.jsp");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 인터셉터
        List<String> LOGIN_URL_PATTERNS = Arrays.asList("/member/login");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(LOGIN_URL_PATTERNS);
        // 회원서비스 인터셉터
        List<String> MEMBER_URL_PATTERNS = Arrays.asList("/chat/room");
        registry.addInterceptor(new MemberInterceptor())
                .addPathPatterns(MEMBER_URL_PATTERNS);
    }
}
