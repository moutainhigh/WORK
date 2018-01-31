
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
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.system.SysAreaInfo;
import com.xlkfinance.bms.rpc.system.SysAreaInfoService;
/**
 * 系统地区Controller
 * @author chenzhuzhen
 * @date 2016年6月30日 下午6:57:19
 */
@Controller
@RequestMapping("/sysAreaInfoController")
public class SysAreaInfoController extends BaseController {
	/**
	 * 根据级别和地区编号查询数据
	 */
	@RequestMapping(value = "getSysAreaInfo")
	@ResponseBody
	public void getSysAreaInfo(String levelNo,String parentCode, HttpServletResponse response){
		List<SysAreaInfo> resultList = new ArrayList<SysAreaInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
			SysAreaInfoService.Client client = (SysAreaInfoService.Client) clientFactory.getClient();
			
			SysAreaInfo query  = new SysAreaInfo();
			
			if(StringUtil.isBlank(levelNo)){
				levelNo = "1";	//默认查询省份
			}
			if(!StringUtil.isBlank(parentCode)){
				query.setParentCode(parentCode);
			}
			query.setLevelNo(levelNo);
			resultList = client.getSysAreaInfo(query);
 
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
		outputJson(resultList, response);
	}
}
