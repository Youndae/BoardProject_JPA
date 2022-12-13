package com.board.project.config;


import com.board.project.config.interceptor.CustomAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.board.project")
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    CustomAuthInterceptor interceptor;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/img/**")
                .addResourceLocations("/img/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/board/boardModify"
                        , "/board/boardDelete"
                        , "/comment/commentDelete"
                        , "/imageBoard/imageModify"
                        , "/imageBoard/imageDelete");

    }
}
