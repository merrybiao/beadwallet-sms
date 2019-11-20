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
@Document(indexName="yimei_report",type="es_message_report")
public class YiMeiEsReportSms {
	
	private String chenal;
	
	private String create_time;
	
	private String phone;
	
	private String seqid;
	
	private String state_value;
	
	private String type;
	
	private String update_time;

}
