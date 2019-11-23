package cn.ffast.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ={"cn.ffast.*"})
@MapperScan(basePackages="cn.ffast.**.dao.**")
/**
 * sprint boot 启动入口
 * idea的话请把项目添加为maven项目即可启动
 */
public class WebApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
