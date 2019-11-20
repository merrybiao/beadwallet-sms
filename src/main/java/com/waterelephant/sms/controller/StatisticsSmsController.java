package com.waterelephant.sms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waterelephant.sms.service.YiMeiSmsQueryService;
import com.waterelephant.sms.utils.DateUtil;

@RestController
public class StatisticsSmsController {
	
	private Logger logger = LoggerFactory.getLogger(StatisticsSmsController.class);
	
	@Autowired
	private YiMeiSmsQueryService yiMeiSmsQueryServiceImpl;
	
	@PostMapping("/statistics")
	public Map<String,Object> statistics(@RequestParam(name="statTime")Long statTime,@RequestParam(name="endTime") Long endTime){
		HashMap<String, Object> map = new HashMap<>();
		try {
			//总的发送量
			Integer total = yiMeiSmsQueryServiceImpl.queryTotalSmsByTimeTotal(statTime, endTime);
			logger.info("时间区间,起始时间{},结束时间{}短信总数据为{}",statTime,endTime,total);
			//短信到达天瑞云的量
			Integer arriveTotal = yiMeiSmsQueryServiceImpl.queryTotalSmsByTimeArriveTotal(DateUtil.getDateString(new Date(statTime), "yyyy-MM-dd HH:mm:ss"), DateUtil.getDateString(new Date(endTime), "yyyy-MM-dd HH:mm:ss"));
			logger.info("时间区间,起始时间{},结束时间{}短信到达总数据为{}",statTime,endTime,arriveTotal);
			//短信成功发出的量
			Integer successTotal = yiMeiSmsQueryServiceImpl.queryTotalSmsByTimeAndSuccess(statTime, endTime);
			logger.info("时间区间,起始时间{},结束时间{}短信成功总数据为{}",statTime,endTime,successTotal);
			//短信发送失败的量
			Integer failTotal = yiMeiSmsQueryServiceImpl.queryTotalSmsByTimeAndFail(statTime, endTime);	
			logger.info("时间区间,起始时间{},结束时间{}短信失败总数据为{}",statTime,endTime,failTotal);
			map.put("total", total);
			map.put("arrive_total", arriveTotal);
			map.put("success_total", successTotal);
			map.put("fail_total", failTotal);
		} catch (Exception e) {
			map.put("ret_code", 700);
			map.put("ret_msg", "~服务器开小差啦，请稍后再试~");
			return map;
		}
		return map;
	}
	
//	public static void main(String[] args) {
//		String REG_EXP_DATE = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$"; 
//		 Pattern pattern = Pattern.compile(REG_EXP_DATE);
//	        String test2 = "2018-12-11 12:12:11";
//	        Matcher matcher = pattern.matcher(test2);
//	        System.out.println(matcher.matches());//返回true
//	}
}
