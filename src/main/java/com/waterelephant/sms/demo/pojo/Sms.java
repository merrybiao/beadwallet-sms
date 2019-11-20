package com.waterelephant.sms.demo.pojo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Sms {

	private Long id;
	// 应用ID
	private String appId;
	// 手机号
	private String mobile;
	// 短信内容
	private String content;
	// 请求时间
	private LocalDateTime reqTime;
	// 短信状态
	private Integer stateId;
	// 实际发送时间
	private LocalDateTime sendTime;
	// 是否测试短信 默认0：不是,1:测试短信
	private String isTest = "0";

	public Sms() {
	}

	public Sms(String appId, String mobile, String content) {
		this.appId = appId;
		this.mobile = mobile;
		this.content = content;
	}

}
