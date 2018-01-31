/**
 * 
 */
package com.xlkfinance.bms.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.system.SysDataAuthView;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfo;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfoService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

import fr.opensagres.xdocreport.utils.StringUtils;

/**
 * 数据授权管理
 * @author huxinlong
 *
 */
@Controller
@RequestMapping("/sysDataAuthortityController")
public class SysDataAuthortityController extends BaseController {
	/**
	 * 数据权限列表页面初始化
	 * @return
	 */
	@RequestMapping(value="dataIndex")
	public String dataIndex(){
		return "system/data_authority_list";
	}
	
	/**
	 * 删除数据授权
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="delete")
	public void delete(String ids,HttpServletRequest request,HttpServletResponse response){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserOrgInfoService");
		List<Integer> dataIds = new ArrayList<Integer>();
		try {
			SysUserOrgInfoService.Client client = (SysUserOrgInfoService.Client) clientFactory.getClient();
			if(StringUtils.isNotEmpty(ids)){
				for(String id : ids.split(",")){
					dataIds.add(Integer.valueOf(id));
				}
			}
			client.batchDeleteDataAuth(dataIds);
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "删除成功!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败!");
		}
		// 输出
		outputJson(j, response);		
	}
	/**
	 * 修改数据授权
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="update")
	public void update(SysUserOrgInfo sysUserOrgInfo,HttpServletRequest request,HttpServletResponse response){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserOrgInfoService");
		try {
			SysUserOrgInfoService.Client client = (SysUserOrgInfoService.Client) clientFactory.getClient();
			client.updateUserOrgInfo(sysUserOrgInfo);
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "修改成功!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败!");
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 获取数据授权列表
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="queryDataAuthList")
	public void queryDataAuthList(SysDataAuthView dataAuthView,HttpServletRequest request,HttpServletResponse response){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserOrgInfoService");
		List<SysDataAuthView> dataList = null;
		Map<String, Object> map = new HashMap<String,Object>();
		int total = 0;
		try {
			SysUserOrgInfoService.Client client = (SysUserOrgInfoService.Client) clientFactory.getClient();
			dataList = client.listDataAuth(dataAuthView);
			total = client.listDataAuthCount(dataAuthView);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			
			if(clientFactory != null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		map.put("rows", dataList);
		map.put("total", total);
		// 输出
		outputJson(map, response);
	}
	
	
}
