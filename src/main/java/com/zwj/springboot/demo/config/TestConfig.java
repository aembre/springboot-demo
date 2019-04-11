package com.zwj.springboot.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 自定义配置文件
 */
@Component
@ConfigurationProperties("test")
@PropertySource("classpath:test.properties")
@Getter
@Setter
public class TestConfig {

    private String name;

    private int age;

}
