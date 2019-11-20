//package com.waterelephant.sms.utils;
//
//import java.util.List;
//
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//
//import com.waterelephant.sms.vo.BwYiMeiEsInfoSms;
//
//
//
//public class EsSearchUtils {
//	
//	@Autowired
//	private static ElasticsearchTemplate estemplate;
//	
//	private final static String create_time = "create_time.keyword";
//	
//	private Integer queryTotalSmsByTime(String statTime,String endTime) {
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//		boolQueryBuilder.filter(QueryBuilders.rangeQuery(create_time).gte(statTime).lte(endTime));
//		NativeSearchQuery querybuild = new NativeSearchQuery(boolQueryBuilder);
//		List<BwYiMeiEsInfoSms> listall = estemplate.queryForList(querybuild, BwYiMeiEsInfoSms.class);
//		return null == listall || listall.isEmpty() ? 0 : listall.size();
//	}
//}
