package com.waterelephant.sms.vo;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName="yimei_error_info",type="es_message_error_info")
public class BwYiMeiEsErrorInfoSms {	
	
	private String phone;
	
	private String msg;
	
	private String chenal;
	
	private String type;
	
}
