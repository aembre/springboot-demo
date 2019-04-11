package com.zwj.springboot.demo.controller;

import com.zwj.springboot.demo.config.PersonConfig;
import com.zwj.springboot.demo.config.ProfileConfig;
import com.zwj.springboot.demo.config.PropertyConfig;
import com.zwj.springboot.demo.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    private PersonConfig personConfig;

    @Autowired
    private PropertyConfig propertyConfig;

    @Autowired
    private TestConfig testConfig;
    @Autowired
    private ProfileConfig profileConfig;

    @RequestMapping("/person")
    public String getPerson(){
        return personConfig.getName() + "--" + personConfig.getAge() + "--" + personConfig.getDescription();
    }

    @GetMapping("/property")
    public String getProPerty(){
        return propertyConfig.getName();
    }

    @GetMapping("test")
    public String test(){
        return testConfig.getName() + "--" + testConfig.getAge();
    }

    @GetMapping("/profile")
    public String profile(){
        return profileConfig.getName();
    }
}
