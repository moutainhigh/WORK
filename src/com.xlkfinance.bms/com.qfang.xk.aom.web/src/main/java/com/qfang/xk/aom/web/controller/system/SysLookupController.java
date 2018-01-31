package com.qfang.xk.aom.web.controller.system;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.web.controller.BaseController;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;

/**
 * 
 * @Title: SysLookupController.java
 * @Description: 数据字典配置的controller
 * @author : Mr.Cai
 * @date : 2014年12月24日 下午2:09:33
 * @email: caiqing12110@vip.qq.com
 */
@Controller
@RequestMapping("/sysLookupController")
public class SysLookupController extends BaseController {
	/**
	 * 
	 * @Description: 根据数据字典类型查询当前数据类型的值
	 * @param lookupType
	 *            数据字典类型
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月2日 上午9:48:46
	 */
	@RequestMapping(value = "getSysLookupValByLookTypeThree")
	@ResponseBody
	public void getSysLookupValByLookTypeThree(String lookupType, HttpServletResponse response) {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			SysLookupVal s = new SysLookupVal();
			s.setPid(-1);
			s.setLookupVal("-1");
			s.setLookupDesc("--请选择--");
			list.add(s);
			list.addAll(client.getSysLookupValByLookType(lookupType));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(list, response);
	}
}