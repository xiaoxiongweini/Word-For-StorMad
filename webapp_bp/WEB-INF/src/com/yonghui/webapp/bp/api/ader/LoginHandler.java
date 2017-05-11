package com.yonghui.webapp.bp.api.ader;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.feizhu.webutil.net.CookieBox;
import com.yonghui.comp.ader.share.AderClient;
import com.yonghui.comp.ader.share.AderService;
import com.yonghui.comp.ader.share.bean.AderEntity;
import com.yonghui.comp.log.share.enums.OpType;
import com.yonghui.webapp.bp.api.ApiHandler;
import com.yonghui.webapp.bp.util.ImageVerifyCode;
import com.yonghui.webapp.bp.util.JsonUtil;
import com.yonghui.webapp.bp.util.OpLogUtil;

import cn770880.jutil.data.RespWrapper;
import cn770880.jutil.string.StringUtil;

public class LoginHandler implements ApiHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Writer out, AderEntity ader)
			throws IOException {
		
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String id = request.getParameter("vid");
		String vCode = request.getParameter("vcode");
		
		if(StringUtil.isEmpty(id) || StringUtil.isEmpty(vCode)) {
			JsonUtil.MAPPER.writeValue( out, RespWrapper.makeResp(2006, "请输入正确的验证码", null));
			return;
		}
		
		if(!ImageVerifyCode.verifyCode(id, vCode)) {
			JsonUtil.MAPPER.writeValue( out, RespWrapper.makeResp(2006, "请输入正确的验证码", null));
			return;
		}
		
		String sid = "";
		AderService service = AderClient.getAderService();
		RespWrapper<String> resp = service.login(loginName, password);
		
		System.out.println("登陆状态["+ resp.getErrCode() +"]");
		
		if(resp.getErrCode() == 0 || resp.getErrCode() == 2017) {
			sid = resp.getObj();
			
			System.out.println("登陆sid["+ sid +"]");
			
			CookieBox cookieBox = new CookieBox( request, response );
            cookieBox.setCookie("bp_sid", sid, ".yonghui.cn", 15*60, "/");
            
            AderEntity entity = service.getAderBySid(sid).getObj();
            if(entity != null) {
	            cookieBox.setCookie("bp_logo_url", entity.getLogoUrl(), ".yonghui.cn", 15*60, "/");
	            cookieBox.setCookie("bp_login_name", entity.getLoginName(), ".yonghui.cn", 15*60, "/");
            }
		}
		OpLogUtil.writeOperateLog("广告主["+loginName+"]登录", 0, "管理员登录", OpType.UPDATE, StringUtil.isEmpty(resp.getObj()));
		
		JsonUtil.MAPPER.writeValue( out, resp);
	}
}