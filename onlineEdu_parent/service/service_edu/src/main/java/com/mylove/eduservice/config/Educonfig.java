package com.mylove.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-06-03 19:20
 */
@Configuration
@MapperScan("com.mylove.eduservice.mapper")
public class Educonfig {

    /**
     * 逻辑删除插件
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
}
