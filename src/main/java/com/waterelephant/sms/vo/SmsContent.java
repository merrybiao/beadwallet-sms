package com.waterelephant.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("短信体内容")
public class SmsContent {
	
	@ApiModelProperty("电话号码")
	private String phone;
	@ApiModelProperty("短信签名")
	private String sign;
	@ApiModelProperty("短信内容[若为火力卡项目，使用模板的情况下多参数请使用'-'分割]")
	private String msg;
	@ApiModelProperty("短信类别[亿美包含（game、cutPay、sxyp、shanngCheng、common）联麓包含（sxyp）天瑞云包含（hlk、hlksc）]")
	private String channel;
	@ApiModelProperty("短信运营商[1001:亿美      2001：联麓      3001：天瑞云]")
	private String businessScenario;
	@ApiModelProperty("短信发送方式（文字1    语音2，目前只支持1）")
	private String type;
	@ApiModelProperty("是否使用模板（true或false）")
	private boolean useTemplate;
	@ApiModelProperty("模板号（如未使用传入空值）")
	private String templateNo;
	@ApiModelProperty("营销短信或通知短信（营销为marketing,通知为notice）")
	private String noticeOrMarketing;
	
}
