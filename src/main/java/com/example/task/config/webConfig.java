package com.example.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {

    @Override// securityPart3WebConfig
    public void addViewControllers(ViewControllerRegistry registry) {
        //get method
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/").setViewName("index");
    }

}
