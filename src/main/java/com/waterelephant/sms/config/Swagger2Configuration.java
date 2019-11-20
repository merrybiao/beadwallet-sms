package com.waterelephant.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 API文档配置
 * 
 * @author Luyuan
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

	private static final String BASE_PACKAGE = "com.waterelephant.sms.controller";

	private static final String API_VERSION = "1.0";

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE)).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SMS管理平台").description("SMS管理平台用于提供新的短信发送服务")
//				.termsOfServiceUrl(termsOfServiceUrl)
				.version(API_VERSION).build();
	}

}
