package io.feikuai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author liudo
 * @date 2018/12/26
 */
@MapperScan("io.feikuai.dao")
@SpringBootApplication
public class AutoTable2beanApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoTable2beanApplication.class, args);
	}

}

