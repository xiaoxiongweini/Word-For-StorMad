package com.yonghui.webapp.bp.api.ad.spread;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn770880.jutil.data.RespWrapper;
import cn770880.jutil.net.NetUtil;
import cn770880.jutil.string.StringUtil;

import com.yonghui.comp.ad.share.AdClient;
import com.yonghui.comp.ad.share.SpreadService;
import com.yonghui.comp.ad.share.bean.SpreadGroup;
import com.yonghui.comp.ader.share.bean.AderEntity;
import com.yonghui.comp.log.share.enums.OpType;
import com.yonghui.webapp.bp.api.ApiHandler;
import com.yonghui.webapp.bp.util.JsonUtil;
import com.yonghui.webapp.bp.util.OpLogUtil;

/**
 * 
 * <br>
 * <b>功能：</b>更新推广组<br>
 * <b>日期：</b>2016年11月9日<br>
 * <b>作者：</b>RUSH<br>
 *
 */
public class UpdateSpreadGroup implements ApiHandler {

	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response, Writer out, AderEntity ader) throws IOException {
		SpreadService service = AdClient.getSpreadService();

		String operator = ader.getAccountName();
		int sgId = NetUtil.getIntParameter(request, "sgId", 0);
		String sgName = NetUtil.getStringParameter( request, "sgName", "");

		if (StringUtil.isEmpty(sgName) || sgId < 1){
			throw new RuntimeException("参数异常!");
		}

		SpreadGroup sg = service.getOneSpreadGroup(sgId).getObj();
		RespWrapper<Boolean> result = service.updateSpreadGroup(sgId, sgName, operator);
		//===============日志记录
		String opContent = "修改推广组["+(sg == null ? "" : sg.getSgName())+"]";
		OpLogUtil.writeOperateLog(opContent, ader.getAdUin(), 
				ader.getLoginName(), OpType.UPDATE, (result.getErrCode() == 0));
		//=====
		JsonUtil.MAPPER.writeValue( out, result);
	}
}