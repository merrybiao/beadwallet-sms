package com.waterelephant.sms.service;

public interface YiMeiSmsQueryService {
	
	 Integer queryTotalSmsByTimeTotal(Long statTime,Long endTime);
	 
	 Integer queryTotalSmsByTimeArriveTotal(String statTime,String endTime);
	 
	 Integer queryTotalSmsByTimeAndSuccess(Long statTime,Long endTime);
	 
	 Integer queryTotalSmsByTimeAndFail(Long statTime,Long endTime);

}
