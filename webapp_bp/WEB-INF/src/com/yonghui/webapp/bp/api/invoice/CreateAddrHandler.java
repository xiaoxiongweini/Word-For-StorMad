package com.yonghui.webapp.bp.api.invoice;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonghui.comp.ader.share.bean.AderEntity;
import com.yonghui.comp.invoice.share.AddrService;
import com.yonghui.comp.invoice.share.InvoiceClient;
import com.yonghui.comp.invoice.share.bean.AddrEntity;
import com.yonghui.comp.log.share.enums.OpType;
import com.yonghui.webapp.bp.api.ApiHandler;
import com.yonghui.webapp.bp.util.JsonUtil;
import com.yonghui.webapp.bp.util.OpLogUtil;

import cn770880.jutil.data.RespWrapper;
import cn770880.jutil.string.StringUtil;

public class CreateAddrHandler implements ApiHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Writer out, AderEntity ader)
			throws IOException {
		
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String address = request.getParameter("address");
//		String postcode = request.getParameter("postcode");
		String phone = request.getParameter("phone");
		String consignee = request.getParameter("consignee");
		
		if(StringUtil.isEmpty(province) 
				|| StringUtil.isEmpty(city)
				|| StringUtil.isEmpty(district)
				|| StringUtil.isEmpty(consignee)
				|| StringUtil.isEmpty(address)
				|| StringUtil.isEmpty(phone)
				|| StringUtil.isEmpty(consignee)) {
			RespWrapper<Boolean> resp = RespWrapper.makeResp(7101, "所有项均为必填项，请填写完成！", false);
			JsonUtil.MAPPER.writeValue(out, resp);
			return;
		}
		
		AddrEntity entity = new AddrEntity();
		entity.setProvince(province);
		entity.setCity(city);
		entity.setDistrict(district);
		entity.setAddress(address);
		entity.setPhone(phone);
		entity.setConsignee(consignee);
		entity.setAdUin(ader.getAdUin());
		entity.setCrtTime(System.currentTimeMillis());
		entity.setCrtUser(ader.getAdUin());
		
		AddrService service = InvoiceClient.getAddrService();
		RespWrapper<Boolean> resp = service.add(entity);
		
		OpLogUtil.writeOperateLog("广告主["+ader.getLoginName()+"]新增地址["+ entity.getAddress() +"]", ader.getAdUin(), ader.getLoginName(), OpType.ADD, resp.getObj());
		
		JsonUtil.MAPPER.writeValue(out, resp);
	}

}