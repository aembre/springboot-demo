package com.zwj.springboot.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "person")
@Component
public class PersonConfig {

    private String name;

    private int age;

    private String description;

}
