package com.zcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * springBoot默认配置了静态资源放行，要想手动配置需要配置spring.resources.static-locations=classpath:/...
 * 	否则外界若想直接访问静态资源需要将静态资源放在/static、/resources、/public、/META-INF/resources文件夹内
 * 
 */
@SpringBootApplication
public class DbtmApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbtmApplication.class, args);
	}

}
