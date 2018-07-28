package com.daly.edumin.web;

import com.daly.edumin.web.common.ApplicationInit;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages={"com.daly.edumin"})
@MapperScan({"com.daly.edumin.basic.mapper","com.daly.edumin.web.mapper","com.daly.edumin.file.mapper"})
public class EduminWebApplication {

	public static void main(String[] args) throws Exception {
		ApplicationInit.init();
		SpringApplication.run(EduminWebApplication.class, args);
	}
}
