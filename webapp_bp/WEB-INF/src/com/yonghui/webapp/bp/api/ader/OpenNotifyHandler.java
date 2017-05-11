package com.yonghui.webapp.bp.api.ader;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonghui.comp.ader.share.AderClient;
import com.yonghui.comp.ader.share.AderService;
import com.yonghui.comp.ader.share.bean.AderEntity;
import com.yonghui.comp.log.share.LogClient;
import com.yonghui.comp.log.share.LogService;
import com.yonghui.comp.log.share.enums.OpType;
import com.yonghui.comp.log.share.enums.SysType;
import com.yonghui.webapp.bp.api.ApiHandler;
import com.yonghui.webapp.bp.api.test.JsonUtil;

import cn770880.jutil.data.RespWrapper;
import cn770880.jutil.string.StringUtil;

public class OpenNotifyHandler implements ApiHandler {

	private LogService logService = LogClient.getLogService();
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Writer out, AderEntity ader)
			throws IOException {
		RespWrapper<Boolean> resp = RespWrapper.makeResp(0, "", false);

		int open = StringUtil.convertInt(request.getParameter("open"), 0);
		
		AderService service = AderClient.getAderService();
		resp = service.openNotify(ader.getAdUin(), open);
		
		logService.writeOperateLog((open == 1 ? "开启" : "关闭") + "接收竞拍短信通知", ader.getAdUin(), ader.getLoginName(), OpType.UPDATE, SysType.BP, resp.getObj());
		
		JsonUtil.MAPPER.writeValue(out, resp);
	}

}