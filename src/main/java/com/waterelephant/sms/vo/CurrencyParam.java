package com.waterelephant.sms.vo;

import com.alibaba.fastjson.annotation.JSONField;


public class CurrencyParam {
	
	@JSONField(name="mobile",ordinal =1)
	private String mobile;
	@JSONField(name="sign",ordinal =2)
	private String sign;
	@JSONField(name="content",ordinal =3)
	private String content;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
