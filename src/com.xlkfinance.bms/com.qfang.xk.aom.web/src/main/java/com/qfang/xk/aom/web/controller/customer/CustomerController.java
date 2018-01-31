package com.qfang.xk.aom.web.controller.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.web.controller.BaseController;
import com.qfang.xk.aom.web.page.Json;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusAcctService;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusPerson;

/**
 * 客户管理
 * @author baogang
 *
 */
@Controller
@RequestMapping("/customerController")
public class CustomerController extends BaseController {
	
	private static final String PATH = "customer/per/";
	private Logger logger = LoggerFactory.getLogger(CustomerController.class);
	/**
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/index")
	public String index(String id) throws Exception {
		System.out.println("===========================");
		return null;
	}
	/**
	 * 个人客户查询调整
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/perList")
	public String perList(ModelMap model) throws Exception {
		return PATH + "per_list";
	}
	/**
	 * 个人客户查询数据
	 * 
	 * @param cusPerBaseDTO
	 * @param response
	 * @param request 
	 */
	@RequestMapping(value = "/perListUrl")
	@ResponseBody
	public void perListUrl(CusPerBaseDTO query, HttpServletResponse response, HttpServletRequest request) {
		int total = 0;
	    List<GridViewDTO> list = new ArrayList<GridViewDTO>();
	    BaseClientFactory factory = null;
	    try {
	    	
			  factory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_ORG_CUSTOMER, "CusPerService");
			  CusPerService.Client cusPerService = (CusPerService.Client) factory.getClient();
          	  list = cusPerService.getAomCusPerBases(query);	
	          total = cusPerService.getAomTotal(query);
			} catch (Exception e) {
				logger.error(ExceptionUtil.getExceptionMessage(e), e);
			}finally{
				destroyFactory(factory);
			}
	      
	      // 输出
	      outputPage(query.getRows(), response, list, total);
	}
	/**
	 * 个人客户基础信息
	 */
	@RequestMapping(value = "/editPerBase")
	public String editPerBase(@RequestParam(value = "acctId", required = false) Integer acctId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		BaseClientFactory factory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_ORG_CUSTOMER, "CusPerService");
		CusPerService.Client cusPerService = (CusPerService.Client) factory.getClient();
		String editType = request.getParameter("editType");
		//标志来自查看操作
		model.put("editType", editType);
		if(acctId != null){
			// 查询个人非配偶关系人
			model.put("acctIdd", acctId);
			List<CusPerson> cusPers = null;
			List<CusPerson> ccusPers = null;
			CusPerson cusPerson = new CusPerson();
			CusAcct cusAcct = new CusAcct();
			cusAcct.setPid(acctId);
			cusPerson.setCusAcct(cusAcct);
			//关系人信息除配偶
			cusPerson.setRelationType(5);
			cusPers = cusPerService.getRelationCusPerson(cusPerson);
			// 查询个人配偶关系人
			cusPerson.setRelationType(2);
			ccusPers = cusPerService.getRelationCusPerson(cusPerson);
			// 查询个人信息
			int perId = cusPerService.selectPerIdByAcctId(acctId);
			CusPerBaseDTO cusPerBaseDTO = cusPerService.getCusPerBase(perId);
			if(ccusPers != null && ccusPers.size() > 0){
				CusPerson spousePerson = ccusPers.get(0);
				spousePerson.setCusAcct(cusAcct);
				cusPerBaseDTO.setSpousePerson(spousePerson);
				model.put("spousePerson", spousePerson);
			}
			model.put("noSpouseList", cusPers);
			model.put("cusPerBaseDTO", cusPerBaseDTO);
		}
		return PATH + "edit_cus_per";
	}
	/**
	 * 保存个人客户基础信息
	 * 
	 * @param cusPerBaseDTO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCusPer", method = RequestMethod.POST)
	public void saveCusPer(CusPerBaseDTO cusPerBaseDTO, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_ORG_CUSTOMER, "CusPerService");
		int acctId = 0;
		try {
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			if(cusPerBaseDTO.getCusPerBase().getPid() > 0) {
				acctId = client.updateCusPerBase(cusPerBaseDTO);
			}else{
				acctId = client.addCusPerBase(cusPerBaseDTO);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		Json j = super.getSuccessObj();
		j.getHeader().put("acctId", acctId);
		outputJson(j, response);

	}
	/**
	 * 根据机构编码+身份证编码做唯一性检验
	 * 
	 * @param cusPerBaseDTO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCusExist", method = RequestMethod.POST)
	public void checkCusExist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_ORG_CUSTOMER, "CusPerService");
		CusPerson existCusPerson = new CusPerson();
		try {
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			Map<String, String> params = new HashMap< String, String>();
			String orgId = request.getParameter("orgId");
			String certNumber = request.getParameter("certNumber");
			params.put("orgId", orgId);
			params.put("certNumber", certNumber);
			//relationType=1 主类型
			params.put("relationType", "1");
			params.put("status", "1");
			List<CusPerson> cuspers = client.checkCusExist(params);
			if(cuspers != null && cuspers.size() > 0){
				existCusPerson = cuspers.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		Json j = super.getSuccessObj();
		j.getHeader().put("pid", existCusPerson.getPid());
		outputJson(j, response);
		
	}
	/**
	 * 删除个人客户信息
	 */
	@RequestMapping(value = "deleteCusAcct", method = RequestMethod.POST)
	public void deleteCusAcct(String pids,HttpServletRequest request,HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_ORG_CUSTOMER,"CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			client.deleteCusAcct(pids);
			outputJson("success", response);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 *保存关系人员信息
	 */
	@RequestMapping(value = "/saveCusPerson", method = RequestMethod.POST)
	@ResponseBody
	public void saveCusPerson(CusPerson cusPerson, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_ORG_CUSTOMER, "CusAcctService");
		
		int personId = 0;
		try {

			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			if (cusPerson.getPid() > 0) {
				personId = client.updateCusPerson(cusPerson);
			} else {
				personId = client.addCusPerson(cusPerson);
			}

		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		Json j = super.getSuccessObj();
		j.getHeader().put("personId", personId);
		outputJson(j, response);
	}
	/**
	 * 删除人员信息
	 */
	@RequestMapping(value = "deleteCusPerson", method = RequestMethod.POST)
	@ResponseBody
	public void deleteCusPerson(String pids, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_ORG_CUSTOMER,"CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			client.deleteCusPerson(pids);
			outputJson("success", response);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 客户关系人信息
	 */
	@RequestMapping(value = "/editCusPerson")
	@ResponseBody
	public void editCusPerson(@RequestParam(value = "pid", required = false) Integer pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = null;
		CusPerson cusPerson = null;
		try {
			if (pid != null) {
				clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_ORG_CUSTOMER, "CusAcctService");
				CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
				cusPerson = client.getCusPerson(pid);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		Json j = super.getSuccessObj();
		j.getHeader().put("cusPerson", cusPerson);
		outputJson(j, response);
	}
}
