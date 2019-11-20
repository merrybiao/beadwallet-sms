package com.waterelephant.sms.common;

import java.util.HashMap;

import com.alibaba.fastjson.annotation.JSONField;
import com.waterelephant.sms.constant.ErrorCode;

import lombok.Data;

/**
 * Api数据响应结构
 * 
 * @author Luyuan
 *
 */
@Data
public class ApiDataResponse {

	@JSONField(ordinal = 1)
	private String code = "";
	@JSONField(ordinal = 2)
	private String msg = "";
	@JSONField(ordinal = 3)
	private Long reqTime = System.currentTimeMillis();
	@JSONField(ordinal = 4)
	private Object data = new HashMap<>();

	public ApiDataResponse(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ApiDataResponse(String code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		if (null != data) {
			this.data = data;
		}
	}

	public static ApiDataResponse success() {
		return new ApiDataResponse(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getDesc());
	}

	public static ApiDataResponse success(String msg) {
		return new ApiDataResponse(ErrorCode.SUCCESS.getCode(), msg);
	}

	public static ApiDataResponse success(Object data) {
		return new ApiDataResponse(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getDesc(), data);
	}

	public static ApiDataResponse fail() {
		return new ApiDataResponse(ErrorCode.FAIL.getCode(), ErrorCode.FAIL.getDesc());
	}

	public static ApiDataResponse fail(String code, String msg) {
		return new ApiDataResponse(code, msg);
	}

	public static ApiDataResponse fail(ErrorCode errorCode, String... msg) {
		return new ApiDataResponse(errorCode.getCode(),
				(msg == null || msg.length <= 0) ? errorCode.getDesc() : msg[0]);
	}

}
