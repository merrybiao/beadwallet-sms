package com.waterelephant.sms.service.Impl;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import com.waterelephant.sms.service.YiMeiSmsQueryService;
import com.waterelephant.sms.vo.BwTryEsReport;
import com.waterelephant.sms.vo.BwTryunEsMessageInfo;

@Service
public class YiMeiSmsQueryServiceImpl implements YiMeiSmsQueryService{
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	private final int max_result_window = 1000000;
	
	private static final long SCROLL_TIMEOUT = 3000;

	@Override
	public Integer queryTotalSmsByTimeTotal(Long statTime, Long endTime) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.filter(QueryBuilders.rangeQuery("sendTime").gte(statTime).lte(endTime));
		NativeSearchQuery querybuild = new NativeSearchQuery(boolQueryBuilder);
		querybuild.setPageable(PageRequest.of(0, max_result_window));
		List<BwTryEsReport> infolist = elasticsearchTemplate.queryForList(querybuild, BwTryEsReport.class);
		return null == infolist || infolist.isEmpty() ? 0 :infolist.size();
	}

	@Override
	public Integer queryTotalSmsByTimeArriveTotal(String statTime, String endTime) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.mustNot(QueryBuilders.matchQuery("deliverResult", ""));
		boolQueryBuilder.filter(QueryBuilders.rangeQuery("sdeliverTime").gte(statTime).lte(endTime));
		NativeSearchQuery querybuild = new NativeSearchQuery(boolQueryBuilder);
		querybuild.setPageable(PageRequest.of(0, max_result_window));
		List<BwTryEsReport> infolist = elasticsearchTemplate.queryForList(querybuild, BwTryEsReport.class);
	 	return null == infolist || infolist.isEmpty() ? 0 :infolist.size();
	}

	/**
	 * 使用游标方式查询
	 */
	@Override
	public Integer queryTotalSmsByTimeAndSuccess(Long statTime, Long endTime) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchQuery("deliverResult", "DELIVRD"));
		boolQueryBuilder.filter(QueryBuilders.rangeQuery("sendTime").gte(statTime).lte(endTime));
		NativeSearchQuery querybuild = new NativeSearchQuery(boolQueryBuilder);
		querybuild.setPageable(PageRequest.of(0, max_result_window));
		Page<BwTryunEsMessageInfo> infolist = elasticsearchTemplate.startScroll(SCROLL_TIMEOUT,querybuild, BwTryunEsMessageInfo.class);
		return null == infolist || infolist.isEmpty() ? 0 : Long.valueOf(infolist.getTotalElements()).intValue();
	}

	/**
	 * 使用游标方式查询
	 */
	@Override
	public Integer queryTotalSmsByTimeAndFail(Long statTime, Long endTime) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.mustNot(QueryBuilders.matchQuery("deliverResult", "DELIVRD"));
		boolQueryBuilder.filter(QueryBuilders.rangeQuery("sendTime").gte(statTime).lte(endTime));
		NativeSearchQuery querybuild = new NativeSearchQuery(boolQueryBuilder);
		querybuild.setPageable(PageRequest.of(0, max_result_window));
		Page<BwTryunEsMessageInfo> infolist = elasticsearchTemplate.startScroll(SCROLL_TIMEOUT,querybuild, BwTryunEsMessageInfo.class);
	 	return null == infolist || infolist.isEmpty() ? 0 : Long.valueOf(infolist.getTotalElements()).intValue();
	}

}
