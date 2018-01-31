/**
 * @Title: CreditsController.java
 * @Package com.xlkfinance.bms.web.controller.credits
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:52:31
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.credits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.rpc.beforeloan.CreditsDTO;
import com.xlkfinance.bms.rpc.beforeloan.CreditsLineDTO;
import com.xlkfinance.bms.rpc.beforeloan.CreditsService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

/**
 * 
 * @ClassName: CreditsController
 * @Description: 额度管理 Controller
 * @author: Cai.Qing
 * @date: 2015年3月17日 上午10:31:28
 */
@Controller
@RequestMapping("/creditsController")
public class CreditsController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(CreditsController.class);
	private static final String credit = "credits/";

	
	

	@RequestMapping(value = "getCreditsLineProjectId")
	public void getCreditsLineProjectId(int projectId, HttpServletResponse response) {
		int projectIds = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			projectIds = client.getCreditsLineProjectId(projectId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据ID查询额度列表ID:" + e.getMessage());
			}
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
		outputJson(projectIds, response);
	}
	
	/**
	 * 
	 * @Description: 额度查询跳转页面
	 * @return 额度查询列表JSP
	 * @author: Cai.Qing
	 * @date: 2015年4月1日 下午2:25:39
	 */
	@RequestMapping(value = "navigation")
	public String navigation() {
		return credit + "index";
	}

	/**
	 * 
	 * @Description: 跳转到额度提取的尽职调查页面
	 * @return 尽职调查JSP
	 * @author: Cai.Qing
	 * @date: 2015年4月1日 下午2:25:08
	 */
	@RequestMapping(value = "navigationInvestigation")
	public String navigationInvestigation() {
		return credit + "credits_add_investigation";
	}

	/**
	 * 
	 * @Description: 授信项目额度查询
	 * @param creditsDTO
	 *            授信DTO对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月19日 下午5:25:00
	 */
	@ResponseBody
	@RequestMapping(value = "getAllCredits", method = RequestMethod.POST)
	public void getAllCredits(CreditsDTO creditsDTO, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			List<CreditsDTO> list = new ArrayList<CreditsDTO>();
			// 设置当前登录的用户的ID,作为查询的条件
			creditsDTO.setPmUserId(getShiroUser().getPid());
			list = client.getAllCredits(creditsDTO);
			int count = client.getAllCreditsDTOCount(creditsDTO);
			map.put("rows", list);
			map.put("total", count);
		} catch (ThriftServiceException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("组织召开待审会:" + e.getMessage());
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("组织召开待审会:" + e.getMessage());
			}
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
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 额度提取申请列表
	 * @return
	 * @author: xuweihao
	 * @date: 2015年3月31日 上午10:04:42
	 */
	@RequestMapping(value = "listLoanProject")
	public String listLoanProject() {
		return credit + "list_request_credits";
	}

	@RequestMapping(value = "listLoanProjectUrl")
	@ResponseBody
	public void listLoanProjectUrl(CreditsLineDTO creditsLineDTO, HttpServletResponse response) {
		List<CreditsLineDTO> list = new ArrayList<CreditsLineDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			if(creditsLineDTO.getPmUserId()!=0){
				list = client.getCreditsLine(creditsLineDTO);
				int total = client.getCreditsLineTotal(creditsLineDTO);
				map.put("total", total);
				map.put("rows", list);
			}else{
				if(creditsLineDTO.getProjectId()!=1){//代表项目详情查看
					// 赋值当前登录的用户,只能查看当前用户的客户提取记录和下属的客户记录
					creditsLineDTO.setPmUserId(getShiroUser().getPid());
				}else{
					creditsLineDTO.setPmUserId(1);//代表项目详情查看不需要设置ID
				}
				
				list = client.getCreditsLine(creditsLineDTO);
				int total = client.getCreditsLineTotal(creditsLineDTO);
				map.put("total", total);
				map.put("rows", list);
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
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
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 额度提取删除功能
	 * @param pidArray
	 * @param response
	 * @author: xuweihao
	 * @date: 2015年4月7日 下午5:12:24
	 */
	@RequestMapping(value = "delLoanProjectUrl")
	public void delLoanProjectUrl(@RequestParam("pidArray") String pidArray, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
		Map<String, Object> map = new HashMap<String, Object>();
		boolean b = false;
		try {
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			b = client.deleteCreditsLine(pidArray);
			if (b == true) {
				map.put("delstatus", "delsuc");
			} else {
				map.put("delstatus", "delerr");
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
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
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 新增额度提取-贷款信息and项目详情
	 * @param project
	 *            项目对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 上午9:48:10
	 */
	@RequestMapping(value = "addInformation", method = RequestMethod.POST)
	public void addInformation(Project project, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			int rows = client.addCreditsInformation(project);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_CREDITS, SysLogTypeConstant.LOG_TYPE_ADD, "新增授信和额度详细信息,授信ID:" + rows);
				// 失败的话,做提示处理
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "新增失败,请重新操作!");
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("新增授信和额度详细信息:" + e.getMessage());
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "新增失败,请重新操作!");
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
		outputJson(j, response);
	}

	/**
	 * 
	 * @Description: 根据项目ID查询授信合同的信息和共同借款人信息
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月22日 下午2:21:49
	 */
	@RequestMapping(value = "getProjectAcctAndPublicManByProjectId")
	public void getProjectAcctAndPublicManByProjectId(int projectId, HttpServletResponse response) {
		List<CreditsDTO> list = new ArrayList<CreditsDTO>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			// 查询授信合同信息
			list = client.getProjectAcctAndPublicManByProjectId(projectId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据项目ID查询企业法人保证人信息:" + e.getMessage());
			}
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

	/**
	 * 
	 * @Description: 查询授信信息
	 * @param projectId
	 * @param response
	 * @author: Zhangyu.Hoo
	 * @date: 2015年4月13日 下午5:04:14
	 */
	@RequestMapping(value = "getCreditsInfo")
	public void getCreditsInfo(@RequestParam("projectId") int projectId, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
		CreditsDTO dto = null;
		try {
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			dto = new CreditsDTO();
			dto.setProjectId(projectId);
			List<CreditsDTO> result = client.getCreditsInfo(dto);
			if (null != result && result.size() > 0 && null != result.get(0)) {
				dto = result.get(0);
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
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
		outputJson(dto, response);
	}
}
