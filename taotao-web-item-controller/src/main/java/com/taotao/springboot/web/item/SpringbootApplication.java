package com.taotao.springboot.web.item;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>Title: SpringbootApplication</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-11 23:59</p>
 * @author ChengTengfei
 * @version 1.0
 */
//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.taotao.springboot.web.item.*")
@EnableDubboConfiguration                                           // 启动Dubbo配置
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
