package com.yonghui.webapp.bss.api.bidplan;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonghui.comp.admin.share.bean.AdminEntity;
import com.yonghui.comp.bidplan.share.BidPlanClient;
import com.yonghui.comp.bidplan.share.BidPlanService;
import com.yonghui.comp.bidplan.share.bean.BidPlanEntity;
import com.yonghui.comp.common.share.AreaShopService;
import com.yonghui.comp.common.share.CommonClient;
import com.yonghui.comp.common.share.CommonService;
import com.yonghui.comp.common.share.bean.BaseShop;
import com.yonghui.comp.common.share.bean.Industry;
import com.yonghui.webapp.bss.api.ApiHandler;
import com.yonghui.webapp.bss.util.JsonUtil;

import cn770880.jutil.data.DataPage;
import cn770880.jutil.data.RespWrapper;
import cn770880.jutil.string.StringUtil;

public class QueryHandler implements ApiHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Writer out, AdminEntity admin)
			throws IOException {
		RespWrapper<DataPage<BidPlanEntity>> resp = RespWrapper.makeResp(1002, "非法操作", null);
		
		int op = StringUtil.convertInt(request.getParameter("op"), 0);
		
		switch(op) {
		case 0:
			queryPlans(request, response, out);
			return;
		case 1:
			queryShops(request, response, out);
			break;
		case 2:
			JsonUtil.MAPPER.writeValue(out, resp);
			break;
		}
	}
	
	/**
	 * 
	 * <b>日期：2016年11月22日</b><br>
	 * <b>作者：bob</b><br>
	 * <b>功能：查询档期</b><br>
	 * <b>@param request
	 * <b>@param response
	 * <b>@param out
	 * <b>@throws IOException</b><br>
	 * <b>void</b>
	 */
	private void queryPlans(HttpServletRequest request, HttpServletResponse response, Writer out) throws IOException {
		RespWrapper<DataPage<BidPlanEntity>> resp = RespWrapper.makeResp(1001, "系统繁忙", null);
		
		int pageNo = StringUtil.convertInt(request.getParameter("pageNo"), 1);
		int pageSize = StringUtil.convertInt(request.getParameter("pageSize"), 20);
		
		String bpName = request.getParameter("bpName");
		String yearMonth = request.getParameter("yearMonth");
		int repeatType = StringUtil.convertInt(request.getParameter("repeatType"), 0);
		int status = StringUtil.convertInt(request.getParameter("status"), -1);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bp_name", bpName);
		if(repeatType > 0) {
			params.put("repeated_type", repeatType);
		}
		if(status > -1) {
			params.put("status", status);
		}
		if(StringUtil.isNotEmpty(yearMonth)) {
			params.put("yearMonth", yearMonth);
		}
		
		BidPlanService service = BidPlanClient.getBidPlanService();
		resp = service.query(params, pageNo, pageSize);
		
		JsonUtil.MAPPER.writeValue(out, resp);
	}
	
	/**
	 * 
	 * <b>日期：2016年11月22日</b><br>
	 * <b>作者：bob</b><br>
	 * <b>功能：查询档期门店</b><br>
	 * <b>@param request
	 * <b>@param response
	 * <b>@param out
	 * <b>@throws IOException</b><br>
	 * <b>void</b>
	 */
	private void queryShops(HttpServletRequest request, HttpServletResponse response, Writer out) throws IOException {
		RespWrapper<Map<String, Object>> resp = RespWrapper.makeResp(1001, "系统繁忙", null);

		int bpId = StringUtil.convertInt(request.getParameter("bpId"), 0);
		
		AreaShopService shopService = CommonClient.getAreaShopService();
		CommonService commonService = CommonClient.getCommonService();
		List<BaseShop> shops = new ArrayList<BaseShop>();
		List<Industry> industries = new ArrayList<Industry>();
		
		BidPlanService service = BidPlanClient.getBidPlanService();
		BidPlanEntity entity = service.findBidPlanById(bpId).getObj();
		if(entity != null) {
			shops = shopService.getBaseShopsByShopCodes(string2List(entity.getShopCodes())).getObj();
			industries = commonService.getIndustrysByIIds(string2List(entity.getIIds())).getObj();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shops", shops);
			map.put("industries", industries);
			
			resp.setErrCode(0);
			resp.setErrMsg("");
			resp.setObj(map);
		}	
		
		JsonUtil.MAPPER.writeValue(out, resp);
	}
	
	/**
	 * 
	 * <b>日期：2016年11月22日</b><br>
	 * <b>作者：bob</b><br>
	 * <b>功能：将逗号分割的字符串转成List</b><br>
	 * <b>@param sIds
	 * <b>@return</b><br>
	 * <b>List<String></b>
	 */
	private List<String> string2List(String sIds) {
		List<String> list = new ArrayList<String>();
		
		if(StringUtil.isNotEmpty(sIds)) {
			String[] ids = sIds.split(",");
			list = Arrays.asList(ids);
		}
		
		return list;
	}

}