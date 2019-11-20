package com.waterelephant.sms.common;

import lombok.Getter;

/**
 * api调用令牌
 * 
 * @author Luyuan
 * @since 1.8
 * @version 1.0
 * @date 2019年1月29日17:37:21
 */
public class ApiToken {

	@Getter
	private String appId;
	@Getter
	private String token;
	@Getter
	private int expTime;
	// 有效时间 30分钟
	public static final int DEFAULT_EXP_TIME = 30 * 60;

	public ApiToken(String appId, String token) {
		this.appId = appId;
		this.token = token;
		this.expTime = DEFAULT_EXP_TIME;
	}

}
