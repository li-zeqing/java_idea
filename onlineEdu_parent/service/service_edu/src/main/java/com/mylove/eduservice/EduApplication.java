package com.mylove.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-06-03 19:16
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.mylove"})
public class EduApplication {

    public static void main(String[] args) {

        SpringApplication.run(EduApplication.class, args);
    }
}
