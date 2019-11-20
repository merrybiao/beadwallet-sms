package com.waterelephant.sms.demo.vo;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("批量短信")
public class SmsBatchVo {

	// 应用ID
	@ApiModelProperty("应用ID")
	private String appId;
	// 令牌
	@ApiModelProperty("令牌")
	private String token;

	@ApiModelProperty("批量短信数据")
	private List<SmsModel> data;
	// 签名
	@ApiModelProperty("签名")
	private String sign;
	// 发送时间
	@ApiModelProperty("发送时间")
	private LocalDateTime sendTime;

	@Data
	@ApiModel("短信模型")
	public class SmsModel {

		@ApiModelProperty("手机号码")
		private String mobile;
		@ApiModelProperty("内容")
		private String content;
		@ApiModelProperty("是否测试短信 默认0：不是,1:测试短信")
		private String isTest = "0";
	}

}
