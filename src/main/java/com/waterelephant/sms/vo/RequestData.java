package com.waterelephant.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("请求参数")
public class RequestData<T> {
	
	@ApiModelProperty("加密私钥")
	private String secretkey;
	
	@ApiModelProperty("短信体")
	private T content;

}
