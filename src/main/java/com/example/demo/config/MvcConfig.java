package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Value("${static.dir.path}")
    private String staticDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/vms/**")
                .addResourceLocations("D:/spring_file_upload/");
    }
}
//http://localhost:8080/vms/testfile_1678608008323_Amit.jpg