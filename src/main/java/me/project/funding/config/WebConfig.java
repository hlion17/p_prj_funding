package me.project.funding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 정적리소스 설정
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/**")
//                .addResourceLocations("classpath:/webapp/resource")
//                .setCachePeriod(20);
//    }

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
}
