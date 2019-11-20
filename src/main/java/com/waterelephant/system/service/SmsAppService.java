package com.waterelephant.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.waterelephant.system.entity.SmsApp;
import com.waterelephant.system.entity.SmsUser;

public interface SmsAppService extends IService<SmsApp> {

	List<SmsApp> selectSmsAppByUser(SmsApp app,SmsUser user);

	String getAppId();

	Integer checkAppNameUnique(SmsApp app);

}
