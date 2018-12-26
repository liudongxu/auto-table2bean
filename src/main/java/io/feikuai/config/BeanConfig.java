package io.feikuai.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bean 配置类
 * @author liudo
 * @date 2018/12/26
 */
@Configuration
public class BeanConfig {
	/**
	 * 获取类型配置文件
	 * @return
	 */
	@Bean(name = "typeConfiguration")
	public org.apache.commons.configuration.Configuration typeConfiguration() {
		try {
			return new PropertiesConfiguration("type.properties");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
