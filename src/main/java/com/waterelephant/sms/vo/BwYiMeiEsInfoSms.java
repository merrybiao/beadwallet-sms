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
@Document(indexName="yimei_info",type="es_message_info")
public class BwYiMeiEsInfoSms {
	
	  private String msg;
	  private Integer chenal;
	  private String update_time;
	  private String create_time;
	  private String phone;
	  private Integer state;
	  private String state_value;
	  private Integer type;
	  private String seqid;	  
	  
}
