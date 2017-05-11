package com.yonghui.webapp.bp.api.bidplan;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonghui.comp.ader.share.bean.AderEntity;
import com.yonghui.comp.bidplan.share.BidPlanClient;
import com.yonghui.comp.bidplan.share.BidPlanService;
import com.yonghui.comp.log.share.LogClient;
import com.yonghui.comp.log.share.LogService;
import com.yonghui.comp.log.share.enums.OpType;
import com.yonghui.comp.log.share.enums.SysType;
import com.yonghui.webapp.bp.api.ApiHandler;
import com.yonghui.webapp.bp.util.JsonUtil;

import cn770880.jutil.data.RespWrapper;
import cn770880.jutil.string.StringUtil;

/**
 * 绑定广告/解绑广告
 * @author bob
 *
 */
public class BindHandler implements ApiHandler {
	
	private LogService logService = LogClient.getLogService();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Writer out, AderEntity ader)
			throws IOException {
		RespWrapper<Boolean> resp = RespWrapper.makeResp(4101, "档期绑定广告失败", false);
		
		int bpaId = StringUtil.convertInt(request.getParameter("bpaId"), 0);	//竞价成功档期ID
		int adId = StringUtil.convertInt(request.getParameter("adId"), 0);		//广告ID
		int op = StringUtil.convertInt(request.getParameter("op"), 0);			//0 解绑  1 绑定
		
		if(bpaId == 0) {
			resp.setErrMsg("请选择有效的档期");
			JsonUtil.MAPPER.writeValue(out, resp);
			return;
		}
		
		if(adId == 0 && op == 1) {
			resp.setErrMsg("请选择有效的广告");
			JsonUtil.MAPPER.writeValue(out, resp);
			return;
		}
		
		BidPlanService service = BidPlanClient.getBidPlanService();
		if(op == 1) {
			resp = service.bindAd(bpaId, adId, ader.getAdUin());
		} else {
			resp = service.unBindAd(bpaId, adId, ader.getAdUin());
		}

		logService.writeOperateLog("档期["+bpaId+"]绑定广告["+adId+"]", ader.getAdUin(), ader.getLoginName(), OpType.UPDATE, SysType.BP, resp.getObj());
		
		JsonUtil.MAPPER.writeValue(out, resp);
	}

}