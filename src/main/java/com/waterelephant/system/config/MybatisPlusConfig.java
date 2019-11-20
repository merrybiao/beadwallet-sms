package com.waterelephant.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * mybatis plus 分页插件
 * 
 * @author Luyuan
 *
 */
@Configuration
@MapperScan(basePackages = { "com.waterelephant.system.mapper", "com.waterelephant.system.*.mapper" })
public class MybatisPlusConfig {

	/**
	 * 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
}