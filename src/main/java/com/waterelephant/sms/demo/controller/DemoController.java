package com.waterelephant.sms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.waterelephant.sms.common.ApiDataResponse;
import com.waterelephant.sms.constant.RedisKeyConstant;
import com.waterelephant.sms.demo.pojo.Sms;
import com.waterelephant.sms.demo.vo.SmsBatchVo;
import com.waterelephant.sms.demo.vo.SmsVo;
import com.waterelephant.sms.demo.vo.SmsBatchVo.SmsModel;
import com.waterelephant.sms.jedis.JedisClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * IndexController.java test or demo
 * 
 * @author Luyuan
 *
 */
@Api(description = "首页")
@RestController
public class DemoController {

	@Autowired
	private JedisClient client;

//	@GetMapping(path = {"","/","/index"})
//	@ApiOperation("首页/欢迎页")
//	public ApiDataResponse index() {
//		return ApiDataResponse.success();
//	}

	@PostMapping("/send")
	@ApiOperation("/发送短信demo")
	public ApiDataResponse sendSms(@RequestBody SmsVo vo) {

		// TODO 对入参进行校验 例如：token appId sign mobile content等字段

		String appId = vo.getAppId();
		String mobile = vo.getMobile();
		String content = vo.getContent();

		try {
			String key = String.format(RedisKeyConstant.SMS_QUEUE, appId);
			Sms sms = new Sms(appId, mobile, content);
			// 将短信内容存入指定的redis中，由job去处理
			client.rpush(key, JSON.toJSONString(sms));
			return ApiDataResponse.success();
		} catch (Exception e) {
			e.printStackTrace();
			return ApiDataResponse.fail();
		}

	}

	@PostMapping("/send_batch")
	@ApiOperation("批量发送短信demo")
	public ApiDataResponse sendBatch(@RequestBody SmsBatchVo vo) {

		// TODO 对入参进行校验 例如：token appId sign等字段
		String appId = vo.getAppId();
		try {
			String key = String.format(RedisKeyConstant.SMS_QUEUE, appId);
			int index = 1;
			String[] values = new String[100];
			for (SmsModel model : vo.getData()) {

				Sms sms = new Sms(appId, model.getMobile(), model.getContent());
				values[index] = JSON.toJSONString(sms);
				index++;
				if (index > values.length) {
					// 将短信内容存入指定的redis中，由job去处理
					client.rpush(key, values);
					values = new String[100];
					index = 1;
				}
			}
			return ApiDataResponse.success(values);
		} catch (Exception e) {
			e.printStackTrace();
			return ApiDataResponse.fail();
		}
	}

}
