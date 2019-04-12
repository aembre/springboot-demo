package com.zwj.springboot.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.zwj.springboot.demo.controller"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder().title("系统RestFul文档")
                .contact(new Contact("zwj", "https://www.izwj.xyz", "1024282982@qq.com"))
                .version("1.0")
                .build();
    }

}
