package com.zwj.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//不加，配置类上需加@Component注解
//@EnableConfigurationProperties({PersonConfig.class,TestConfig.class,PropertyConfig.class})

//虽然Spring Boot并不推荐我们继续使用xml配置，但如果出现不得不使用xml配置的情况，
// Spring Boot允许我们在入口类里通过注解@ImportResource({"classpath:some-application.xml"})来引入xml配置文件。
//@ImportResource("classpath:test.xml")
public class SpringbootDemoApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SpringbootDemoApplication.class, args);
        SpringApplication app = new SpringApplication(SpringbootDemoApplication.class);
        /**
         * Spring Boot项目在启动的时候会有一个默认的启动图案：
         * 我们可以把这个图案修改为自己想要的。在src/main/resources目录下新建banner.txt文件，然后将自己的图案黏贴进去即可。
         * ASCII图案可通过网站http://www.network-science.de/ascii/一键生成.
         */
//        关闭banner
//        app.setBannerMode(Banner.Mode.OFF);

//        禁止命令行参数
//        app.setAddCommandLineProperties(false);
        app.run(args);
    }

}
