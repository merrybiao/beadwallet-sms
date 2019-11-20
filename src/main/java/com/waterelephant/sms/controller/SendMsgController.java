
package com.waterelephant.sms.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.waterelephant.sms.jedis.JedisClient;
import com.waterelephant.sms.utils.SmsMD5Util;
import com.waterelephant.sms.vo.DefaultResponse;
import com.waterelephant.sms.vo.RequestData;
import com.waterelephant.sms.vo.SmsContent;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("msg/")
@Slf4j
public class SendMsgController {
		
	@Autowired
	private JedisClient jedisClient;

    private final String COMMOM_SEND_REDIS_KEY = "system:sendMessage";
    private final String GAME_SEND_REDIS_KEY = "system:game_sendMessage";
    private final String CUTPAY_SEND_REDIS_KEY = "system:cutPaySendMessage";
    private final String CSSX_SEND_REDIS_KEY = "system:cssx_sendMessage";
    private final String SHOPPINGMALL_SEND_REDIS_KEY = "system:sendMessage_shanngCheng";
    private final String SXYP_SEND_REDIS_KEY = "system:sxyp_sendMessage";
    private final String XMJB_SEND_REDIS_KEY = "system:xmjb_sendMessage";
    private final String LINALU_SEND_REDIS_KEY = "system:lianlu_sendMessage";
    private final String HLK_SEND_REDIS_KEY = "system:tryun_sendMessage";
    
    private final String yimei_code = "1001";
    private final String lianlu_code = "2001";
    private final String tryun_code = "3001";
    
    private final String game_flag = "game";
    private final String cutPay_flag = "cutPay";
    private final String sxyp_flag = "sxyp";
    private final String shanngCheng_flag = "shanngCheng";
    private final String cssx_flag = "cssx";
    private final String xmjb_flag = "xmjb";
    private final String common_flag = "common";
    private final String hlk_flag = "hlk";
    private final String hlksc_flag = "hlksc";
    
    private final String notice_template_key = "sms:tryun:notice_template";
    
    private final String tryun_notice="notice";
    private final String tryun_marketing="marketing";

    
    private final String[] singArray = new String[] {"yimei_sign","lianlu_sign","tianryun_sign"};
    
	@ResponseBody
	@PostMapping("sendCommonMsg")
	public DefaultResponse sendMsg(@RequestBody RequestData<SmsContent> data) {
		DefaultResponse response = new DefaultResponse("0000","请求成功");
   try {
	    SmsContent param = data.getContent();
		String secretkey = data.getSecretkey();
		Assert.hasText(secretkey, "传入参数不完整，缺少[~secretkey~]");
		Assert.isTrue(!StringUtils.isEmpty(param), "~传入参数不完整，缺少[~content~]~");
        String pk = SmsMD5Util.encoding(JSON.toJSONString(param));
        log.info("加密的key值为："+pk);
        if(!pk.equals(secretkey)) return new DefaultResponse("300","非法访问，加密秘钥不正确，请重新输入");
        	String msg = param.getMsg();
        	String sign = param.getSign();
        	String phone = param.getPhone();
        	String type = param.getType();
        	String channel = param.getChannel();
        	String businessScenari = param.getBusinessScenario();
    		Assert.hasText(param.getBusinessScenario(), "~传入参数不完整，缺少[~businessScenari~]~");
    		Assert.hasText(msg, "~传入参数不完整，缺少[~msg~]~");
    		Assert.hasText(phone, "~传入参数不完整，缺少[~phone~]~");
    		Assert.hasText(type, "~传入参数不完整，缺少[~type~]~");
    		Assert.hasText(channel, "~传入参数不完整，缺少[~channel~]~");
    		if(tryun_code.equals(businessScenari)) {
    			Assert.hasText(param.isUseTemplate()+"", "~传入参数不完整，缺少[~useTemplate~]~");
    			Assert.hasText(param.getNoticeOrMarketing(), "~传入参数不完整，缺少[~noticeOrMarking~]~");
    			if(!(tryun_notice.equals(param.getNoticeOrMarketing()) || tryun_marketing.equals(param.getNoticeOrMarketing()))) throw new IllegalArgumentException("~不存在的发送模式，仅支持[notice、marketing]模式~"); 
    			if(param.getNoticeOrMarketing().equals("notice")) {
    				if(param.isUseTemplate()) {
    					//Assert.isTrue(NumberUtils.isNumber(param.getMsg()), "~请传入正确的参数~");
        				Assert.hasText(param.getTemplateNo(), "~传入参数不完整，缺少[~templateNo~]~");
        				boolean flag = validataTemplate(param.getTemplateNo(),notice_template_key);
        				Assert.isTrue(flag, "~不存在的模板号，请输入正确的模板号~");
        			}
    			} else {
    				if(param.isUseTemplate()) {
//        				Assert.hasText(param.getTemplateNo(), "~传入参数不完整，缺少[~templateNo~]~");
//        				boolean flag = validataTemplate(param.getTemplateNo(),marketing_template_key);
//        				Assert.isTrue(flag, "~不存在的模板号~");
    					throw new IllegalArgumentException("~[marketing]营销短信目前不支持使用模板，请知悉~");
        			}
    			}
    		}
    		//判断是用那个运营商发送短信
    		switch (businessScenari) {
    		case yimei_code:
    			boolean validataSign = validataSign(sign,singArray[0]);
    			if(!validataSign) {
    				return new DefaultResponse("400", "~非法访问，亿美授权的签名中不包含此签名，请授权后再提交~");
    			}
    			Map<String,String> yimeimap = new HashMap<String, String>();
    			yimeimap.put("phone", phone);
    			yimeimap.put("msg", sign+msg);
    			yimeimap.put("type", type);
    			switch (channel) {
					case sxyp_flag:
						jedisClient.lpush(SXYP_SEND_REDIS_KEY, JSON.toJSONString(yimeimap));
						break;
					case cutPay_flag:
						jedisClient.lpush(CUTPAY_SEND_REDIS_KEY, JSON.toJSONString(yimeimap));					
						break;
					case game_flag:
						jedisClient.lpush(GAME_SEND_REDIS_KEY, JSON.toJSONString(yimeimap));					
						break;
					case shanngCheng_flag:
						jedisClient.lpush(SHOPPINGMALL_SEND_REDIS_KEY, JSON.toJSONString(yimeimap));					
						break;
					case cssx_flag:
						jedisClient.lpush(CSSX_SEND_REDIS_KEY, JSON.toJSONString(yimeimap));					
						break;
					case xmjb_flag:
						jedisClient.lpush(XMJB_SEND_REDIS_KEY, JSON.toJSONString(yimeimap));					
						break;
					case common_flag:
						jedisClient.lpush(COMMOM_SEND_REDIS_KEY, JSON.toJSONString(yimeimap));					
						break;
					default:
						response = new DefaultResponse("600", "~亿美暂不支持此[channel]的业务渠道，请尝试其他业务~") ;
						break;
				  }
    			break;
    		
    		case lianlu_code:
    			boolean lainlusign = validataSign(sign,singArray[1]);
    			if(!lainlusign) {
    				return new DefaultResponse("400", "~非法访问，联麓授权的签名中不包含此签名，请授权后再提交~");
    			}
    			Map<String,String> lianlumap = new HashMap<String, String>();
    			lianlumap.put("mobile", phone);
    			lianlumap.put("content", msg);
    			lianlumap.put("sign", sign);
    			switch (channel) {
				case sxyp_flag:
					jedisClient.lpush(LINALU_SEND_REDIS_KEY, JSON.toJSONString(lianlumap));	
					break;
				default:
					response = new DefaultResponse("600", "~联麓暂不支持此[channel]的业务渠道，请尝试其他业务~");
					break;
				}
    			break;
    			
    		case tryun_code:
    			boolean tryunsign = validataSign(sign,singArray[2]);
    			if(!tryunsign) {
    				return new DefaultResponse("400", "~非法访问，天瑞云授权的签名中不包含此签名，请授权后再提交~");
    			}
    			Map<String,Object> tryunmap = new HashMap<String, Object>();
    			tryunmap.put("mobile", phone);
    			tryunmap.put("content", msg);
    			tryunmap.put("sign", sign);
    			tryunmap.put("useTemplate", param.isUseTemplate());
    			tryunmap.put("templateNo", param.getTemplateNo());
    			tryunmap.put("noticeOrMarketing", param.getNoticeOrMarketing());
    			switch (channel) {
    			case hlk_flag:
    			case hlksc_flag:
    				jedisClient.lpush(HLK_SEND_REDIS_KEY, JSON.toJSONString(tryunmap));	
    				break;
    			default:
    				response = new DefaultResponse("600", "~火力卡暂不支持此[channel]的业务渠道，请尝试其他业务~");
    				break;
    			}
    			break;
    		default:
    			response = new DefaultResponse("777", "~暂不支持此[businessScenari]的发送方式，请尝试其他方式~") ;
    			break;
    		}
		} catch (IllegalArgumentException e) {
			response = new DefaultResponse("700", e.getMessage());
		}  catch (Exception e) {
			log.error("短信存储出现异常，异常信息为{}",e.getMessage());
			e.printStackTrace();
			response = new DefaultResponse("999", "~系统开小差了，请稍后再试~");
	} 
		return response;
	}
    
    /**
     * 验证签名
     * @param sign
     * @param key
     * @return
     */
    private boolean validataSign(String sign,String key) {
		boolean flag = false;
    	if(jedisClient.exists(key)) {
    		Set<String> keys = jedisClient.hkeys(key);
        	Iterator<String> iterator = keys.iterator();
        	while(iterator.hasNext()){
    			String keyval = iterator.next();
    			String redisSign =  jedisClient.hget(key, keyval);
    			if(sign.equals(redisSign)){
    				flag = true;
    				break;
    			}
    		}
    	}
		return flag;
    };
    
    private boolean validataTemplate(String template,String key) {
 		boolean flag = false;
     	if(jedisClient.exists(key)) {
     		Set<String> keys = jedisClient.hkeys(key);
         	Iterator<String> iterator = keys.iterator();
         	while(iterator.hasNext()){
     			String keyval = iterator.next();
     			//String redisSign =  jedisClient.hget(key, keyval);
     			if(template.equals(keyval)){
     				flag = true;
     				break;
     			}
     		}
     	}
 		return flag;
     };
}
