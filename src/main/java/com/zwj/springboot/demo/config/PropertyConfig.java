package com.zwj.springboot.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PropertyConfig {

    @Value("${person.name}")
    private String name;

}
