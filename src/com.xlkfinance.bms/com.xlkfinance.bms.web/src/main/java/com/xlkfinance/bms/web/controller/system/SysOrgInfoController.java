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

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;
import com.xlkfinance.bms.rpc.system.SysOrgInfoService;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.web.constant.Constants;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

/**
 * 系统机构管理(组织架构)
 * @author huxinlong
 *
 */
@Controller
@RequestMapping("/sysOrgInfoController")
public class SysOrgInfoController extends BaseController {

	/**
	 * 初始化树形机构页面
	 * @return
	 * @throws Exception 
	 * @throws ThriftException 
	 * @throws TException 
	 * @throws ThriftServiceException 
	 */
	@RequestMapping(value="initPage")
	public String initPage(Model model,HttpServletResponse response,
			HttpServletRequest request) throws Exception{
		model.addAttribute("checkbox", request.getParameter("checkbox")==null?false:true);
		request.setAttribute("checkbox", true);
		return "system/tree";
	}
	
	@RequestMapping(value="/treelist")
	@ResponseBody
	public void treeList(HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory
				.getClient();
		
		List<SysOrgInfo> orgList = null;
		
		SysOrgInfo orgs = new SysOrgInfo();
		
		orgList = client.listSysOrgByLayer(orgs);
		List<TreeMenuDTO> treeList = Lists.newArrayList();
		//获取机构树列表
		for (SysOrgInfo org : orgList) {
			TreeMenuDTO dto = new TreeMenuDTO();
			dto.setId(org.getId());
			dto.setpId(org.getParentId());
			dto.setName(org.getName());
			dto.setOpen(false);
			treeList.add(dto);
		}
		
		if (clientFactory != null) {
			clientFactory.destroy();
		}
		outputJson(treeList, response);
	}
	
	/**
	 * 新增机构
	 * @param sysOrgInfn
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="/insertOrg")
	@ResponseBody
	public void insertOrg(SysOrgInfo sysOrgInfo,HttpServletResponse response,
			HttpServletRequest request){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		
		try {
			SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();
			SysOrgInfo parentOrg = client.getSysOrgInfo(sysOrgInfo.getParentId());
			sysOrgInfo.setLayer(parentOrg.getLayer()+1);
			sysOrgInfo.setStatus(Constants.STATUS_ENABLED);
			// 调用保存方法
			client.saveSysOrgInfo(sysOrgInfo);
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "新增失成功！");
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "新增失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	/**
	 * 修改机构
	 * @param sysOrgInfn
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="/updateOrg")
	@ResponseBody
	public void updateOrg(SysOrgInfo sysOrgInfo,HttpServletResponse response,
			HttpServletRequest request){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		
		try {
			SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();
			// 调用修改方法
			client.updateSysOrgInfo(sysOrgInfo);
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "修改成功！");
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	
	/**
	 * 修改机构树
	 * @param sysOrgInfn
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="/updateApplicationTree")
	@ResponseBody
	public void updateApplicationTree(SysOrgInfo sysOrgInfo,HttpServletResponse response,
			HttpServletRequest request){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		
		try {
			SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();
			// 调用修改方法
			client.updateApplicationTree(sysOrgInfo);
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "修改成功！");
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	
	@RequestMapping(value="/getAllOrg")
	@ResponseBody
	public void getAllOrg(HttpServletResponse response,
			HttpServletRequest request) throws Exception{
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory
				.getClient();
		
		List<SysOrgInfo> orgList = null;
		
		SysOrgInfo orgs = new SysOrgInfo();
		
		orgList = client.listSysOrgByLayer(orgs);
		List<TreeMenuDTO> returnList = new ArrayList<TreeMenuDTO>();
		TreeMenuDTO temp = new TreeMenuDTO();
		temp.setId(-1);
		temp.setName("--请选择--");
		returnList.add(temp);
		//获取机构树列表
		for (SysOrgInfo org : orgList) {
			TreeMenuDTO dto = new TreeMenuDTO();
			dto.setId(org.getId());
			dto.setpId(org.getParentId());
			dto.setName(org.getName());
			returnList.add(dto);
		}
		outputJson(returnList, response);
	}
	/**
	 * 修改机构
	 * @param sysOrgInfn
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="/getSysOrgInfo")
	@ResponseBody
	public void getSysOrgInfo(Integer orgId,HttpServletResponse response,
			HttpServletRequest request){
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		SysOrgInfo org = null;
		try {
			SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();
			org = client.getSysOrgInfo(orgId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			
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
		outputJson(org, response);
	}
	
	/**
	 * @param sysOrgInfo
	 * @param response
	 * @param request
	 * @throws ThriftException 
	 */
	@RequestMapping(value="/checkSysOrgExists")
	@ResponseBody
	public void checkSysOrgExists(SysOrgInfo sysOrgInfo,HttpServletResponse response,
			HttpServletRequest request){
		
		boolean flag = false;
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		try {
			SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();
			Map<String,String> paras  = new HashMap<String,String>();
			paras.put("parentId",sysOrgInfo.getParentId()+"");
			paras.put("name",sysOrgInfo.getName());
			
			boolean isExist = client.isExistSysOrgInfoByName(paras);
			
			if(isExist){
				flag = true;
			}
			
		} catch (ThriftServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ThriftException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		outputJson(flag, response);
	}
	/**
	 * 删除机构
	 * @param orgId
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="/deleteOrg")
	@ResponseBody
	public void deleteOrg(Integer orgId,HttpServletResponse response,
			HttpServletRequest request){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		BaseClientFactory clientFactoryUser = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysUserService");
		
		try {
			SysUserService.Client clientUser = (SysUserService.Client) clientFactoryUser.getClient();
			SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();
			
			SysOrgInfo org = client.getSysOrgInfo(orgId);
			
			if(org == null){
				j.getHeader().put("success", false);
				j.getHeader().put("msg","此机构已被删除,请重新选择!");
			}
			//获取该机构下的子机构,有子机构时不能删除
			List<SysOrgInfo> subOrgList = client.listSysOrgInfo(orgId);
			if(subOrgList != null && !subOrgList.isEmpty()){
				j.getHeader().put("success", false);
				j.getHeader().put("msg","此机构已有子机构,不能删除!");
			}
			List<Integer> orgIds = new ArrayList<Integer>();
			orgIds.add(orgId);
			//根据该机构下的用户,已关联用户时不能删除
			List<Integer> userList = clientUser.getUsersByOrgId(orgIds);
			if(userList != null && !userList.isEmpty()){
				
				j.getHeader().put("success", false);
				j.getHeader().put("msg","此机构已关联用户,不能删除!");
			}
			//删除机构
			if(org != null && (userList == null || userList.isEmpty())
					&& (subOrgList == null || subOrgList.isEmpty())){
				
				client.delSysOrgInfo(orgId);
				
				j.getHeader().put("success", true);
				j.getHeader().put("msg","删除成功!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			
			if(clientFactoryUser != null){
				try {
					clientFactoryUser.destroy();
				} catch (ThriftException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
}
