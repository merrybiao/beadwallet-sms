package com.waterelephant.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("短信响应内容")
public class DefaultResponse {

  @ApiModelProperty("响应编码")
  private String requestCode;
  
  @ApiModelProperty("响应内容")
  private String requestMsg;
 
}
