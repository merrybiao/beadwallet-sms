package com.waterelephant.sms.demo.vo;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 发送短信VO demo
 * 
 * @author luyuan
 *
 */
@Data
@ApiModel("短信")
public class SmsVo {

	// 应用ID
	@ApiModelProperty("应用ID")
	private String appId;
	// 令牌
	@ApiModelProperty("令牌")
	private String token;

	// 手机号码
	@ApiModelProperty("手机号")
	private String mobile;

	// 短信内容
	@ApiModelProperty("内容")
	private String content;

	// 签名
	@ApiModelProperty("签名")
	private String sign;

	// 发送时间
	@ApiModelProperty("发送时间")
	private LocalDateTime sendTime;

	// 是否测试短信 默认0：不是,1:测试短信
	@ApiModelProperty("是否测试短信 默认0：不是,1:测试短信")
	private String isTest = "0";

}
