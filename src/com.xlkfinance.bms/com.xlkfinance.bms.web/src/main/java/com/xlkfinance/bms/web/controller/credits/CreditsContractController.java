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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.beforeloan.BeforloadOutputSerice;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfo;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfoImpl;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfoImplDTO;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.contract.Contract;
import com.xlkfinance.bms.rpc.contract.ContractService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/**
 * 
 * @ClassName: ContractController
 * @Description: 额度管理 Controller
 * @author: hezhiying
 * @date: 2015年3月17日 上午10:31:28
 */
@Controller
@RequestMapping("/creditsContractController")
public class CreditsContractController extends BaseController {
	private static final String credit = "credits/";
	private Logger logger = LoggerFactory
			.getLogger(CreditsContractController.class);

	/**
	 * 
	 * @Description: 新增页面跳转方法
	 * @return 新增页面
	 * @author: hezhiying
	 * @date: 2015年3月17日 上午11:35:45
	 */
	@RequestMapping(value = "addNavigation")
	public String addNavigation() {
		return credit + "loan_tab";
	}

	/**
	 * 
	 * @Description: 页面跳转
	 * @return 合同页面
	 * @author: hezhiying
	 * @date: 2015年3月17日 下午3:36:44
	 */
	@RequestMapping(value = "navigationContract")
	public String navigationContract(String contractCatelog, Integer projectId,
			ModelMap model) {
		model.put("contractCatelog", contractCatelog);
		model.put("projectId", projectId);
		return credit + "loan_add_contract";
	}

	@RequestMapping(value = "getContractListUrl")
	public void getContractListUrl(Contract contract,
			HttpServletResponse response) {
		// 创建集合
		List<Contract> list = new ArrayList<Contract>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractService");
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			list = client.getLoanContracts(contract);
			int count = client.getLoanContractsCount(contract);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", list);
			map.put("total", count);
			outputJson(map, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询贷款合同信息失败:" + e.getMessage());
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
	 * @author: hezhiying
	 * @param loanProject
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "LoadOutputinfo")
	public void getLoadOutput(Project loanProject,
			HttpServletResponse response, ModelMap model) {
		LoanOutputInfoImpl loan = new LoanOutputInfoImpl();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN,
					"BeforloadOutputSerice");
			BeforloadOutputSerice.Client client = (BeforloadOutputSerice.Client) clientFactory
					.getClient();
			loan = client.getLoadOutputinfo(loanProject);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询贷前列表:" + e.getMessage());
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
		outputJson(loan, response);

	}

	/**
	 * @author: hezhiying
	 * @param loanProject
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "LoadOutputinfoList")
	@ResponseBody
	public void getLoadOutputList(Project loanProject,
			HttpServletResponse response) {
		List<LoanOutputInfo> list = new ArrayList<LoanOutputInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN,
					"BeforloadOutputSerice");
			BeforloadOutputSerice.Client client = (BeforloadOutputSerice.Client) clientFactory
					.getClient();
			list = client.getLoadOutputinfoList(loanProject);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询贷前列表:" + e.getMessage());
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
	 * 类描述： 插入放款信息 hezhiying
	 *
	 * 修改时间：2015年3月21日 上午10:13:47 修改备注：
	 * 
	 * @version
	 * @param
	 * @return
	 */
	@RequestMapping(value = "insertLoadOutputinfo")
	@ResponseBody
	public void insertLoadOutputinfo(
			LoanOutputInfoImplDTO loanOutputInfoImplDTO,
			HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		int sta = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN,
					"BeforloadOutputSerice");
			BeforloadOutputSerice.Client client = (BeforloadOutputSerice.Client) clientFactory
					.getClient();
			ShiroUser s = getShiroUser();
			loanOutputInfoImplDTO.setStatus(1);
			loanOutputInfoImplDTO.setCreateDate(DateUtil.format(DateUtil.getToDay()));
			loanOutputInfoImplDTO.setCreateUser(s.getPid()+"");
			sta = client.insertLoadOutputinfo(loanOutputInfoImplDTO);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询贷前列表:" + e.getMessage());
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
		// 1代表成功0代表失败
		outputJson(sta, response);
	}

	/**
	 * 类描述： 插入放款信息 创建人：hezhiying
	 *
	 * 修改时间：2015年3月21日 上午10:13:47 修改备注：
	 * 
	 * @version
	 * @param
	 * @return
	 */
	@RequestMapping(value = "updateLoadOutputinfo")
	@ResponseBody
	public void updateLoadOutputinfo(
			LoanOutputInfoImplDTO loanOutputInfoImplDTO,
			HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		int sta = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN,
					"BeforloadOutputSerice");
			BeforloadOutputSerice.Client client = (BeforloadOutputSerice.Client) clientFactory
					.getClient();
			loanOutputInfoImplDTO.setStatus(1);
			sta = client.updateLoadOutputinfo(loanOutputInfoImplDTO);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询财务放款:" + e.getMessage());
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
		// 1代表成功0代表失败
		outputJson(loanOutputInfoImplDTO.getDifAmt(), response);
	}

	/**
	 * 
	 * @Description: 删除贷款申请列表
	 * @param pids
	 *            贷款申请对象id
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: hezhiying
	 * @date: 2015年3月21日 上午10:54:59
	 */
	@RequestMapping(value = "deleteLoadOutputinfo")
	public void deleteLoadOutputinfo(int pId, HttpServletResponse response)
			throws Exception {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN,
					"BeforloadOutputSerice");
			BeforloadOutputSerice.Client client = (BeforloadOutputSerice.Client) clientFactory
					.getClient();
			client.deleteLoadOutputinfo(pId);
		} catch (ThriftServiceException tse) {
			if (logger.isDebugEnabled()) {
				tse.printStackTrace();
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作！");
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

}
