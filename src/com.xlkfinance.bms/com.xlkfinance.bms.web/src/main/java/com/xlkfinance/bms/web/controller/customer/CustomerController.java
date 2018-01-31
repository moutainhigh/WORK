/**
 * @Title: CustomerController.java
 * @Package com.xlkfinance.bms.web.controller.customer
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:52:57
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.customer;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.BalanceSheetDTO;
import com.xlkfinance.bms.rpc.customer.BalanceSheetEditDTO;
import com.xlkfinance.bms.rpc.customer.CashFlowItem;
import com.xlkfinance.bms.rpc.customer.CashFlowReport;
import com.xlkfinance.bms.rpc.customer.CashFlowReportEditDTO;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusAcctBank;
import com.xlkfinance.bms.rpc.customer.CusAcctPotential;
import com.xlkfinance.bms.rpc.customer.CusAcctService;
import com.xlkfinance.bms.rpc.customer.CusBlacklistRefuse;
import com.xlkfinance.bms.rpc.customer.CusComAssure;
import com.xlkfinance.bms.rpc.customer.CusComBalanceSheet;
import com.xlkfinance.bms.rpc.customer.CusComBalanceSheetMeta;
import com.xlkfinance.bms.rpc.customer.CusComBase;
import com.xlkfinance.bms.rpc.customer.CusComBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusComContact;
import com.xlkfinance.bms.rpc.customer.CusComDebt;
import com.xlkfinance.bms.rpc.customer.CusComFinanceReport;
import com.xlkfinance.bms.rpc.customer.CusComFinanceReportOverviewDTO;
import com.xlkfinance.bms.rpc.customer.CusComGuaranteeType;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReport;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReportEditDTO;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReportMeta;
import com.xlkfinance.bms.rpc.customer.CusComInvest;
import com.xlkfinance.bms.rpc.customer.CusComReward;
import com.xlkfinance.bms.rpc.customer.CusComService;
import com.xlkfinance.bms.rpc.customer.CusComService.Client;
import com.xlkfinance.bms.rpc.customer.CusComShare;
import com.xlkfinance.bms.rpc.customer.CusComStaff;
import com.xlkfinance.bms.rpc.customer.CusComStaffDTO;
import com.xlkfinance.bms.rpc.customer.CusComTeam;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusEstDTO;
import com.xlkfinance.bms.rpc.customer.CusEstFactorWeights;
import com.xlkfinance.bms.rpc.customer.CusEstInfo;
import com.xlkfinance.bms.rpc.customer.CusEstInfoDTO;
import com.xlkfinance.bms.rpc.customer.CusEstQuota;
import com.xlkfinance.bms.rpc.customer.CusEstTemplate;
import com.xlkfinance.bms.rpc.customer.CusPerBase;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerCom;
import com.xlkfinance.bms.rpc.customer.CusPerCreditDTO;
import com.xlkfinance.bms.rpc.customer.CusPerCreditDef;
import com.xlkfinance.bms.rpc.customer.CusPerFamilyDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.customer.CusRelation;
import com.xlkfinance.bms.rpc.customer.FinacialDTO;
import com.xlkfinance.bms.rpc.customer.FinacialDTO1;
import com.xlkfinance.bms.rpc.customer.ProfitDTO;
import com.xlkfinance.bms.rpc.customer.QueryPersonDTO;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;
import com.xlkfinance.bms.web.util.FileUtil;

@Controller
@RequestMapping("/customerController")
public class CustomerController extends BaseController {
	private static final String serviceModuel = "customer";
	private static final String cus = "customer/";
	private static final String cusper = "customer/per/";
	private static final String est = "customer/est/";
	private static final String cuscom = "customer/com/";
	private static final String cuscomon = "customer/common/";
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
	 * 附件上传
	 */
	@RequestMapping(value = "/uploadFile")
	public String uploadFile(@RequestParam(value = "imgurl", required = false) String imgurl, ModelMap model) throws Exception {
		model.put("imgurl", imgurl);
		return cuscomon + "uploadFile";
	}

	/**
	 * 附件上传
	 */

	@RequestMapping(value = "/saveFile")
	public void saveFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Json j = super.getSuccessObj();
		Map<String, Object> map = FileUtil.processFileUpload(request, response, "customer", getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
		j.getHeader().put("success", map.get("flag"));
		j.getHeader().put("path", map.get("path"));
		outputJson(j, response);

	}

	/**
	 * 添加黑名单、拒贷信息
	 */
	@RequestMapping(value = "/editBlacklistRefuse")
	public String editBlacklistRefuse(@RequestParam(value = "acctIds", required = false) String acctIds, @RequestParam(value = "cusStatus", required = false) Integer cusStatus, ModelMap model) throws Exception {
		model.put("acctIds", acctIds);
		model.put("cusStatus", cusStatus);
		return cus + "edit_blacklist_refuse";
	}

	/**
	 * 保存黑名单、拒贷信息
	 */
	@RequestMapping(value = "saveBlacklistRefuse")
	public String saveBlacklistRefuse(CusBlacklistRefuse cusBlacklistRefuse, CusPerBase cusPerBase, String acctIds, String blackRefuseIds, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			if (blackRefuseIds != null && !"".equals(blackRefuseIds)) {
				cusBlacklistRefuse.setRevokeDttm(DateUtil.format(new Date()));
				client.updateCusBlacklistRefuse(cusBlacklistRefuse, cusPerBase, acctIds, blackRefuseIds);
			} else {
				client.addCusBlacklistRefuse(cusBlacklistRefuse, acctIds);
			}
			outputJson("success", response);
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
		return cus + "edit_blacklist_refuse";
	}

	/**
	 * 黑名单跳转页面
	 */
	@RequestMapping(value = "/listBlacklist")
	public String listBlacklist(ModelMap model) throws Exception {
		return cus + "list_blacklist";
	}

	/**
	 * 黑名单列表查询数据
	 * 
	 * @param cusPerBaseDTO
	 * @param response
	 * @param request 
	 */
	@RequestMapping(value = "/listBlacklistUrl")
	@ResponseBody
	public void listBlacklistUrl(CusPerBaseDTO cusPerBaseDTO, HttpServletResponse response, HttpServletRequest request) {
		List<GridViewDTO> blacklists = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			cusPerBaseDTO.setUserIds(getUserIds(request));
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			blacklists = client.getCusBlacklists(cusPerBaseDTO);
			int total = client.getBlackListCount(cusPerBaseDTO);
			map.put("total", total);
			map.put("rows", blacklists);
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
	 * 黑名单跳转页面
	 */
	@RequestMapping(value = "/listRefuse")
	public String listRefuse(ModelMap model) throws Exception {
		return cus + "list_refuse";
	}

	/**
	 * 拒贷客户列表查询数据
	 * 
	 * @param cusPerBaseDTO
	 * @param response
	 * @param request 
	 */
	@RequestMapping(value = "/listRefuseUrl")
	@ResponseBody
	public void listRefuseUrl(CusPerBaseDTO cusPerBaseDTO, HttpServletResponse response, HttpServletRequest request) {
		List<GridViewDTO> refuses = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/*if (cusPerBaseDTO.getCusName() != null) {
				cusPerBaseDTO.setCusName(new String(cusPerBaseDTO.getCusName().getBytes("ISO8859-1"), "UTF-8"));
			}*/
			cusPerBaseDTO.setUserIds(getUserIds(request));
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			refuses = client.getCusRefuses(cusPerBaseDTO);
			map.put("total", 0);
			map.put("rows", refuses);
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
	 * 添加黑名单、拒贷信息
	 */
	@RequestMapping(value = "/editRevokeReason")
	public String editRevokeReason(@RequestParam(value = "blacklistRefuseId", required = false) String blacklistRefuseId, @RequestParam(value = "acctIds", required = false) String acctIds, @RequestParam(value = "cusStatus", required = false) Integer cusStatus, ModelMap model) throws Exception {
		model.put("blacklistRefuseId", blacklistRefuseId);
		model.put("cusStatus", cusStatus);
		model.put("acctIds", acctIds);
		return cus + "edit_revoke_reason";
	}

	// 客户公共Controller

	// 资信评估

	/**
	 * 资信评估查询列表
	 */
	@RequestMapping(value = "/listEstTemplate")
	public String listEstTemplate(ModelMap model) throws Exception {
		return est + "list_est_template";
	}

	/**
	 * 资信评估列表数据查询
	 */
	@RequestMapping(value = "/listEstTemplateUrl")
	@ResponseBody
	public void listEstTemplateUrl(CusEstTemplate cusEstTemplate, HttpServletResponse response) throws Exception {
		List<GridViewDTO> templates = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		try {
			/*if (cusEstTemplate.getModelName() != null) {
				cusEstTemplate.setModelName(new String(cusEstTemplate.getModelName().getBytes("ISO8859-1"), "UTF-8"));
			}*/
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			templates = client.getCusEstTemplates(cusEstTemplate);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (TException e) {
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
		outputJson(templates, response);
	}

	/**
	 * 编辑资信评估
	 */
	@RequestMapping(value = "/editEstTemplate")
	public String editEstTemplate(@RequestParam(value = "pid", required = false) Integer pid, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			if (pid != null && pid > 0) {
				CusEstTemplate cusEstTemplate = client.getCusEstTemplate(pid);
				model.put("cusEstTemplate", cusEstTemplate);
			}
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
		return est + "edit_est_template";
	}

	/**
	 * 编辑资信评估指标
	 */
	@RequestMapping(value = "/editEstQuota")
	public String editEstQuota(@RequestParam(value = "factorId", required = false) Integer factorId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			CusEstDTO cusEstDTO = client.getCusEstQuotas(factorId);
			model.put("cusEstDTO", cusEstDTO);
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
		return est + "edit_est_quota";
	}

	/**
	 * 编辑资信评估指标选项
	 */
	@RequestMapping(value = "/editEstOption")
	public String editEstOption(@RequestParam(value = "quotaId", required = false) Integer quotaId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			CusEstDTO cusEstDTO = client.getCusEstOptions(quotaId);
			model.put("cusEstDTO", cusEstDTO);
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
		return est + "edit_est_option";
	}

	/**
	 * 保存资信评估模板
	 */
	@RequestMapping(value = "/saveEstTemplate")
	public void saveEstTemplate(CusEstTemplate cusEstTemplate, ModelMap model, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		int pid = 0;
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			if (cusEstTemplate.getPid() > 0) {
				pid = client.updateCusEstTemplate(cusEstTemplate);
			} else {
				pid = client.addCusEstTemplate(cusEstTemplate);
			}

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
		Json j = super.getSuccessObj();
		j.getHeader().put("templateId", pid);
		outputJson(j, response);
	}

	/**
	 * 保存资信评估指标信息
	 */
	@RequestMapping(value = "/saveEstQuota")
	public void saveEstQuota(CusEstFactorWeights cusEstFactorWeights, ModelMap model, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		int pid = 0;
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			pid = client.updateCusEstQuota(cusEstFactorWeights);

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
		Json j = super.getSuccessObj();
		j.getHeader().put("quotaId", pid);
		outputJson(j, response);
	}

	/**
	 * 保存资信评估选项信息
	 */
	@RequestMapping(value = "/saveEstOption")
	public void saveEstOption(CusEstQuota cusEstQuota, ModelMap model, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		int pid = 0;
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			pid = client.updateCusEstOption(cusEstQuota);

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
		Json j = super.getSuccessObj();
		j.getHeader().put("quotaId", pid);
		outputJson(j, response);
	}

	/**
	 * 编辑资信评估指标选项
	 */
	@RequestMapping(value = "/editEstInfo")
	public String editEstInfo(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "flag", required = false) Integer flag, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "cusType", required = false) Integer cusType, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			if (pid != null && pid > 0) {
				CusEstInfo cusEstInfo = client.getCusEstInfo(pid);
				model.put("cusEstInfo", cusEstInfo);
			}
			if (acctId != null && acctId > 0) {
				model.put("acctName", client.getAcctNameById(acctId));
			}

			model.put("templates", client.selectAllEstTemplateName(cusType));

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
		model.put("flag", flag);
		model.put("acctId", acctId);
		model.put("cusType", cusType);
		model.put("comId", comId);
		return est + "edit_est_info";
	}

	/**
	 * 保存资信评估信息
	 */
	@RequestMapping(value = "/saveEstInfo")
	public void saveEstInfo(CusEstInfo cusEstInfo, ModelMap model, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			if (cusEstInfo.getPid() > 0) {
				client.updateCusEstInfo(cusEstInfo);
			} else {
				client.addCusEstInfo(cusEstInfo);
			}

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
		Json j = super.getSuccessObj();
		outputJson(j, response);
	}

	/**
	 * 删除资信评估模板信息
	 */
	@RequestMapping(value = "deleteEstTemplate")
	public String deleteEstTemplate(String pids, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");

		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			client.deleteCusEstTemplate(pids);
			outputJson("success", response);
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
		return est + "list_est_template";
	}

	/**
	 * 删除资信评估信息
	 */
	@RequestMapping(value = "deleteEstInfo")
	public String deleteEstInfo(String pids, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			client.deleteCusEstInfos(pids);
			outputJson("success", response);
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
		return est + "list_est_info";
	}

	/**
	 * 模板下拉框
	 * 
	 * @param lookupType
	 * @param response
	 */
	@RequestMapping(value = "getTemplateName")
	@ResponseBody
	public void getTemplateName(Integer modelType, HttpServletResponse response) {
		List<CusEstTemplate> list = new ArrayList<CusEstTemplate>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			CusEstTemplate cet = new CusEstTemplate();
			cet.setPid(0);
			cet.setModelName("--请选择--");
			list.add(cet);
			modelType = modelType == null ? 0 : modelType;
			list.addAll(client.selectAllEstTemplateName(modelType));
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

	/**
	 * 编辑资信评估指标选项
	 */
	@RequestMapping(value = "/makeTemplate")
	public String makeTemplate(@RequestParam(value = "templateId", required = false) Integer templateId, @RequestParam(value = "estId", required = false) Integer estId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			CusEstTemplate cusEstTemplate = client.getMakeCusEstTemplate(templateId);
			if (estId != null && estId > 0) {
				CusEstInfo cusEstInfo = client.getCusEstInfo(estId);
				model.put("cusEstInfo", cusEstInfo);
			}
			model.put("cusEstTemplate", cusEstTemplate);
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
		return est + "make_template";
	}

	/**
	 * 资信评估查询列表
	 */
	@RequestMapping(value = "/listEstInfo")
	public String listEstInfo(ModelMap model) throws Exception {
		return est + "list_est_info";
	}

	/**
	 * 选择个人客户
	 */
	@RequestMapping(value = "/xzPerCus")
	public String xzPerCus(ModelMap model) throws Exception {
		return est + "xz_per_cus";
	}

	/**
	 * 选择企业客户
	 */
	@RequestMapping(value = "/xzComCus")
	public String xzComCus(ModelMap model) throws Exception {
		return est + "xz_com_cus";
	}

	/**
	 * 选择企业客户
	 */
	@RequestMapping(value = "/xzUnderCom")
	public String xzUnderCom(ModelMap model, @RequestParam(value = "acctId", required = false) Integer acctId) throws Exception {
		model.put("acctId", acctId);
		return est + "xz_Under_Com";
	}

	/**
	 * 资信评估列表数据查询
	 * @param request 
	 */
	@RequestMapping(value = "/listEstInfoUrl")
	@ResponseBody
	public void listEstInfoUrl(CusEstInfoDTO cusEstInfoDTO, HttpServletResponse response, String startScore, String endScore, HttpServletRequest request) throws Exception {
		List<GridViewDTO> estInfos = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/*if (cusEstInfoDTO.getCusName() != null) {
				cusEstInfoDTO.setCusName(new String(cusEstInfoDTO.getCusName().getBytes("ISO8859-1"), "UTF-8"));
			}*/
			if ("".equals(startScore) || null == startScore) {
				cusEstInfoDTO.setStartScore(-1);
			}
			if ("".equals(endScore) || null == endScore) {
				cusEstInfoDTO.setEndScore(-1);
			}
			cusEstInfoDTO.setUserIds(getUserIds(request));
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			
			estInfos = client.getCusEstInfos(cusEstInfoDTO);
			int total = client.getTotalEst(cusEstInfoDTO);
			map.put("total", total);
			map.put("rows", estInfos);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (TException e) {
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
	 * @Description: 查询pid的用户
	 * @param loanProject
	 *            查询pid的用户
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: hezhiying
	 * @date: 2015年3月7日 上午10:54:59
	 */
	@RequestMapping(value = "getCusPersonKeyPid")
	public void getCusPersonKeyPid(@RequestParam(value = "pid", required = false) Integer pid, HttpServletResponse response) {
		
		CusDTO cusBase = null;
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");

		BaseClientFactory factory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
		try {
			CusPerService.Client clientPer = (CusPerService.Client) factory.getClient();
			int type = clientPer.getByAcctTypeKeyPid(pid);
			if (type == 1) {
				cusBase = clientPer.getPersonalListKeyPid(pid);
			} else {
				CusComService.Client client = (CusComService.Client) clientFactory.getClient();
				cusBase = client.selectByPidPrimaryKey(pid);
			}
			if (cusBase != null) {
				cusBase.setCusStatus(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (clientFactory != null) {
					clientFactory.destroy();
				}
			} catch (ThriftException e) {
				e.printStackTrace();
			} finally {
				if (factory != null) {
					try {
						factory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// 输出
		outputJson(cusBase, response);
	}

	/**
	 * 
	 * @Description: 根据客户ID查询用户信息
	 * @param acctId
	 *            客户ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年5月25日 下午3:19:01
	 */
	@RequestMapping(value = "getCusPersonByAcctId")
	public void getCusPersonByAcctId(@RequestParam(value = "acctId", required = false) Integer acctId, HttpServletResponse response) {
		CusDTO cusBase = null;
		BaseClientFactory clientFactory = null;
		BaseClientFactory factory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
			factory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client clientPer = (CusPerService.Client) factory.getClient();
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			// 根据客户ID查询客户类型
			int type = client.searcherCusTypeByAcctId(acctId);
			// 客户类型判断
			if (type == 1) {
				// 如果是个人,就根据客户ID查询个人信息对象
				cusBase = clientPer.getPersonalListByAcctId(acctId);
			} else {
				// 如果是企业,就根据客户ID查询企业信息对象
				cusBase = client.getCusComByAcctId(acctId);
			}
			// 设置客户类型
			if (cusBase != null) {
				cusBase.setCusStatus(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (clientFactory != null) {
					clientFactory.destroy();
				}
			} catch (ThriftException e) {
				e.printStackTrace();
			} finally {
				if (factory != null) {
					try {
						factory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// 输出
		outputJson(cusBase, response);
	}

	/**
	 * 人员信息查询列表
	 * 
	 * @param acctId
	 * @param relationType
	 * @param response
	 * @param request 
	 */
	@RequestMapping(value = "/listPersonUrl")
	@ResponseBody
	public void listPersonUrl(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "relationType", required = false) Integer relationType, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, HttpServletResponse response, HttpServletRequest request) {
		List<GridViewDTO> cusPers = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			CusPerson cusPerson = new CusPerson();
			CusAcct cusAcct = new CusAcct();
			cusAcct.setPid(acctId);
			cusPerson.setCusAcct(cusAcct);
			cusPerson.setRelationType(relationType);
			cusPerson.setPage(page);
			cusPerson.setRows(rows);
			cusPers = client.getCusPersons(cusPerson);
			
			int total = client.getTotalCusPersons(cusPerson);
			map.put("total", total);
			map.put("rows", cusPers);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (TException e) {
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
	 * 删除客户信息
	 */
	@RequestMapping(value = "deleteCusAcct")
	public String deleteCusAcct(String pids, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			client.deleteCusAcct(pids);
			outputJson("success", response);
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
		return cusper + "per_list";
	}

	/**
	 * 删除人员信息
	 */
	@RequestMapping(value = "deleteCusPerson")
	public String deleteCusPerson(String pids, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			client.deleteCusPerson(pids);
			outputJson("success", response);
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
		return cusper + "per_list";
	}

	/**
	 * 客户银行信息
	 */
	@RequestMapping(value = "/editBankAcct")
	public String editBankAcct(@RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "cusType", required = false) String cusType, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		try {
			if (pid != null) {
				CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
				CusAcctBank cusAcctBank = client.getCusAcctBank(pid);
				model.put("cusAcctBank", cusAcctBank);
			}
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
		model.put("acctId", acctId);
		model.put("cusType", cusType);
		return cus + "edit_bank_acct";
	}

	/**
	 * 保存客户银行信息
	 */
	@RequestMapping(value = "saveBankAcct")
	public String saveBankAcct(CusAcctBank cusAcctBank, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			int pid = cusAcctBank.getPid();
			if (pid > 0) {
				client.updateCusAcctBanks(cusAcctBank);
			} else {
				client.addCusAcctBank(cusAcctBank);
			}
			outputJson("success", response);
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
		return cuscomon + "list_bank_acct";
	}

	/**
	 * 删除客户银行信息
	 */
	@RequestMapping(value = "deleteCusAcctBank")
	public String deleteCusAcctBank(String pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			client.deleteCusAcctBank(pid);
			outputJson("success", response);
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
		return cuscomon + "list_bank_acct";
	}

	/**
	 * 客户关系人信息
	 */
	@RequestMapping(value = "/editCusPerson")
	public String editCusPerson(@RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "relationType", required = false) Integer relationType, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		try {
			if (pid != null) {
				clientFactory = getFactory(serviceModuel, "CusAcctService");
				CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();

				CusPerson cusPerson = client.getCusPerson(pid);
				model.put("cusPerson", cusPerson);

			}
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
		model.put("acctId", acctId);
		model.put("relationType", relationType);
		return cus + "edit_cus_person";
	}

	@RequestMapping(value = "/saveCusPerson", method = RequestMethod.POST)
	@ResponseBody
	public void saveCusPerson(CusPerson cusPerson, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		int personId = 0;
		try {

			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			if (cusPerson.getPid() > 0) {
				personId = client.updateCusPerson(cusPerson);
			} else {
				personId = client.addCusPerson(cusPerson);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// logger.info(e.toString());
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

	// 个人客户Controller

	/**
	 * 个人客户征信记录列表
	 */
	@RequestMapping(value = "/listPerCredit")
	public String listPerCredit(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "isLook", required = false) Integer isLook, @RequestParam(value = "perType", required = false) Integer perType, ModelMap model) throws Exception {
		int perId = 0;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		try {
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			perId = client.selectPerIdByAcctId(acctId);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (TException e) {
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
		model.put("perId", perId);
		model.put("acctId", acctId);
		model.put("perType", perType);
		model.put("isLook", isLook);
		return cusper + "list_per_credit";
	}

	/**
	 * 删除个人客户征信记录信息
	 */
	@RequestMapping(value = "deletePerCredit")
	public String deletePerCredit(String pids, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
		try {
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			client.deleteCusPerCredit(pids);
			outputJson("success", response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
				e.getStackTrace();
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cusper + "list_per_credit";
	}

	@RequestMapping(value = "/listPerCreditUrl")
	@ResponseBody
	public void listPerCreditUrl(@RequestParam(value = "perId", required = false) Integer perId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, HttpServletResponse response) {
		List<GridViewDTO> cusPers = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		CusPerBase cusPerBase = new CusPerBase();
		CusAcct cusAcct = new CusAcct();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			cusAcct.setPid(perId);
			cusPerBase.setCusAcct(cusAcct);
			cusPerBase.setPage(page);
			cusPerBase.setRows(rows);
			cusPers = client.getCusPerCredits(cusPerBase);
			int total = client.getTotalCusPerBase(cusPerBase);
			map.put("total", total);
			map.put("rows", cusPers);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
	}

	/**
	 * 个人客户基础信息
	 */
	@RequestMapping(value = "/editPerBase")
	public String editPerBase(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "flag", required = false) Integer flag, @RequestParam(value = "isLook", required = false) Integer isLook, @RequestParam(value = "currUserPid", required = false) Integer currUserPid, @RequestParam(value = "type", required = false) Integer type,HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		int perId = 0;
		CusPerBaseDTO cusPerBaseDTO;
		try {
			if (acctId != null) {
				CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
				perId = client.selectPerIdByAcctId(acctId);
				cusPerBaseDTO = client.getCusPerBase(perId);
				int pmUserId = cusPerBaseDTO.getCusAcct().getPmUserId();
				model.put("pmUserId", pmUserId);
				model.put("isLook", isLook);
				model.put("serverUrl", getServerUrl(request));
				// 根据当前用户的pid,查看该用户下是否是客户经理的上级，返回的结果大于0：？是，否
				if (flag != 1) {
					//int num = client.selectIsAcctManager(currUserPid);
					model.put("num", 2);
					model.put("cusPerBaseDTO", cusPerBaseDTO);
					model.put("perId", perId);
					model.put("acctId", acctId);
					model.put("flag", flag);
					model.put("type", type);
				} else {
					model.put("num", 2);
					model.put("cusPerBaseDTO", cusPerBaseDTO);
					model.put("perId", perId);
					model.put("acctId", acctId);
					model.put("flag", flag);
					model.put("type", type);
				}
			} else {
				model.put("num", 2);
				model.put("perId", perId);
				model.put("acctId", acctId);
				model.put("flag", flag);
				model.put("type", type);
			}
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

		return cusper + "edit_cus_per";
	}

	/**
	 * 
	 * @Description: 根据项目ID的客名称去查客户
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @throws Exception
	 * @author: Cai.Qing
	 * @date: 2015年6月15日 下午2:11:56
	 */
	@RequestMapping(value = "/getCusManagerName")
	public void getCusManagerName(@RequestParam(value = "projectId", required = false) Integer projectId, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		List<CusAcct> list = new ArrayList<CusAcct>();
		try {
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			list.addAll(client.getUserName(projectId));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(list, response);
	}
	/**
	 * 
	 * @Description: 根据项目ID的客名称去查客户
	 * @param cusAcctManagerId
	 *            客户ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @throws Exception
	 * @author: wangheng
	 * @date: 2015年6月24日 
	 */
	@RequestMapping(value = "/getCusManagerNames")
	public void getCusManagerNames(@RequestParam(value = "cusAcctManagerId", required = false) Integer cusAcctManagerId, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		List<CusAcct> list = new ArrayList<CusAcct>();
		try {
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			list.addAll(client.getUserNames(cusAcctManagerId));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(list, response);
	}

	/**
	 * 个人客户家庭信息
	 */
	@RequestMapping(value = "/editPerFamily")
	public String editPerFamily(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "isLook", required = false) Integer isLook, @RequestParam(value = "perType", required = false) Integer perType, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		int perId = 0;
		String marrStatus = "";
		try {
			if (acctId != null) {
				CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
				perId = client.selectPerIdByAcctId(acctId);

				CusPerFamilyDTO cusPerFamilyDTO = client.getCusPerFamily(perId, acctId);
				// 查询婚姻状态，如果 已婚 配偶信息必填 否则 非必填
				marrStatus = client.selectMarrStatus(acctId);
				model.put("cusPerFamilyDTO", cusPerFamilyDTO);
				
			}
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
		model.put("perId", perId);
		model.put("acctId", acctId);
		model.put("marrStatus", marrStatus);
		model.put("perType", perType);
		model.put("isLook", isLook);
		return cusper + "edit_per_family";
	}

	@ResponseBody
	@RequestMapping(value = "/savePerFamily", method = RequestMethod.POST)
	public Map<String, Object> savePerFamily(CusPerFamilyDTO cusPerFamilyDTO) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {

			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			if (cusPerFamilyDTO.getCusPerFamily().getPid() > 0) {
				client.updateCusPerFamily(cusPerFamilyDTO);
			} else {
				client.addCusPerFamily(cusPerFamilyDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.info(e.toString());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		return modelMap;
	}

	/**
	 * 个人客户银行信息列表
	 */
	@RequestMapping(value = "/listPerBank")
	public String listPerBank(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "isLook", required = false) Integer isLook, @RequestParam(value = "perType", required = false) Integer perType, ModelMap model) throws Exception {
		model.put("acctId", acctId);
		model.put("cusType", "PER");
		model.put("perType", perType);
		model.put("isLook", isLook);
		return cusper + "list_per_bank";
	}

	/**
	 * 个人客户征信记录
	 */
	@RequestMapping(value = "/editPerCredit")
	public String editPerCredit(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "perId", required = false) Integer perId, @RequestParam(value = "pid", required = false) Integer pid, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		try {
			if (pid != null) {
				CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
				CusPerCreditDTO cusPerCreditDTO = client.getCusPerCredit(pid);
				model.put("cusPerCreditDTO", cusPerCreditDTO);

			} else {
				CusPerCreditDTO cusPerCreditDTO = new CusPerCreditDTO();
				List<CusPerCreditDef> list = Lists.newArrayList();
				List<CusPerCreditDef> list2 = Lists.newArrayList();

				for (int i = 0; i < 7; i++) {
					CusPerCreditDef def = new CusPerCreditDef();
					CusPerCreditDef def2 = new CusPerCreditDef();
					if (i == 6) {
						def.setDayNameDesc("最近24月透支180天以上次数");
						list.add(def);
						def2.setDayNameDesc("最近24月180天以上结清次数");
						list2.add(def2);
					} else {
						def.setDayNameDesc("最近24月透支" + (i * 30 + 1) + "-" + ((i + 1) * 30) + "天次数");
						list.add(def);
						def2.setDayNameDesc("最近24月" + (i * 30 + 1) + "-" + ((i + 1) * 30) + "天结清次数");
						list2.add(def2);
					}
				}

				cusPerCreditDTO.setDeficits(list);
				cusPerCreditDTO.setSettles(list2);
				model.put("cusPerCreditDTO", cusPerCreditDTO);
			}
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
		model.put("acctId", acctId);
		model.put("perId", perId);
		return cusper + "edit_per_credit";
	}

	/**
	 * 保存个人客户征信记录信息
	 * 
	 * @param cusPerCreditDTO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/savePerCredit", method = RequestMethod.POST)
	public Map<String, Object> saveCusPerCredit(CusPerCreditDTO cusPerCreditDTO) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {

			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			if (cusPerCreditDTO.getCusPerCredit().getPid() > 0) {
				client.updateCusPerCredit(cusPerCreditDTO);
			} else {
				client.addCusPerCredit(cusPerCreditDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.info(e.toString());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		return modelMap;
	}

	/**
	 * 个人客户旗下公司列表
	 */
	@RequestMapping(value = "/listUnderCom")
	public String listUnderCom(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "isLook", required = false) Integer isLook, @RequestParam(value = "perType", required = false) Integer perType, ModelMap model) throws Exception {
		model.put("acctId", acctId);
		model.put("perType", perType);
		model.put("isLook", isLook);
		return cusper + "list_under_com";
	}

	/**
	 * 个人客户旗下子公司
	 * 
	 * @param acctId
	 * @param response
	 */
	@RequestMapping(value = "/listUnderComUrl")
	@ResponseBody
	public void listUnderCom(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, HttpServletResponse response) {
		List<GridViewDTO> cusPers = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			CusPerBase cusPerBase = new CusPerBase();
			CusAcct cusAcct = new CusAcct();
			cusAcct.setPid(acctId);
			cusPerBase.setCusAcct(cusAcct);
			cusPerBase.setRows(rows);
			cusPerBase.setPage(page);
			cusPers = client.getCusUnderCom(cusPerBase);
			int total = client.getTotalUnderCom(cusPerBase);
			map.put("total", total);
			map.put("rows", cusPers);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (TException e) {
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
	 * 删除个人客户旗下子公司信息
	 */
	@RequestMapping(value = "deleteUnderCom")
	public String deleteUnderCom(String pids, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			client.deleteUnderCom(pids);
			outputJson("success", response);
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
		return cusper + "list_under_com";
	}

	/**
	 * 个人客户关系人列表
	 */
	@RequestMapping(value = "/listPerPerson")
	public String listPerPerson(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "isLook", required = false) Integer isLook, @RequestParam(value = "perType", required = false) Integer perType, ModelMap model) throws Exception {
		model.put("acctId", acctId);
		model.put("relationType", 5);
		model.put("perType", perType);
		model.put("isLook", isLook);
		return cusper + "list_per_person";
	}

	/**
	 * 个人客户资信评估列表
	 */
	@RequestMapping(value = "/listPerEst")
	public String listPerEst(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "isLook", required = false) Integer isLook, @RequestParam(value = "perType", required = false) Integer perType, ModelMap model) throws Exception {
		model.put("acctId", acctId);
		model.put("cusType", 1);
		model.put("perType", perType);
		model.put("isLook", isLook);
		return cusper + "list_per_est";
	}

	/**
	 * 个人客户业务往来列表
	 */
	@RequestMapping(value = "/listPerBusiness")
	public String listPerBusiness(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "isLook", required = false) Integer isLook, @RequestParam(value = "perType", required = false) Integer perType, ModelMap model) throws Exception {
		model.put("acctId", acctId);
		model.put("perType", perType);
		model.put("isLook", isLook);
		return cusper + "list_per_business";
	}

	/**
	 * 业务往来查询列表
	 */
	@RequestMapping(value = "/listPerAndCusBusiness")
	public String listPerAndCusBusiness(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "isLook", required = false) Integer isLook, ModelMap model) throws Exception {
		 model.put("is_complete", "1");
		 model.put("isLook", isLook);
		return cuscom + "list_perAndcom_business";
	}

	/**
	 * 查询个人客户和企业客户的业务往来列表
	 */
	@RequestMapping(value = "/getBusinessAllInfo")
	public void getBusinessAllInfo(QueryPersonDTO queryPersonDTO,
	 @RequestParam(value = "page", required = false) Integer page,
	 @RequestParam(value = "rows", required = false) Integer rows,
	// @RequestParam(value = "isC", required = false) Integer is_complete,
			HttpServletResponse response, ModelMap model) throws Exception {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		try {
//			if(StringUtil.isEmpty(queryPersonDTO.getIsComplete())){
//				queryPersonDTO.setIsComplete("0,1");
//			}
			// 获取当前登录人id
			int userId = getShiroUser().getPid();
			queryPersonDTO.setCurrLoginId(userId);
			 queryPersonDTO.setRows(rows);
			 queryPersonDTO.setPage(page);
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			list = client.getBusinessAllInfo(queryPersonDTO);
			 int total = client.getTotalCusAndPerBusiness(queryPersonDTO);
//			 model.put("page",page);
//			 model.put("total",total);
			 map.put("total", total);
			 map.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		outputJson(map, response);
	}

	/**
	 * 查询个人客户业务往来列表
	 */
	@RequestMapping(value = "/searcherPerBusiness")
	public void searcherPerBusiness(HttpServletResponse response, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		// 获取当前登录人id
		int userId = getShiroUser().getPid();
		QueryPersonDTO queryPersonDTO = new QueryPersonDTO();
		queryPersonDTO.setAcctId(acctId);
		queryPersonDTO.setRows(rows);
		queryPersonDTO.setPage(page);
		queryPersonDTO.setCurrLoginId(userId);
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
		try {
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			list = client.getPerBusiness(queryPersonDTO);
			// int total=client.getTotals(queryPersonDTO);
			// map.put("total", total);
			// map.put("rows", list);
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
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		int acctId = 0;
		try {
			//初始化客户关系中间信息
			CusRelation cusRelation = new CusRelation();
			SysUser sysUser = getSysUser();//登录用户信息
			cusRelation.setPmUserId(sysUser.getPid());//登录用户的Id
			cusRelation.setOrgType(1);
			int secondOrgId = getSecondOrgId(sysUser.getOrgId());
			cusRelation.setOrgId(secondOrgId);//登录用户的二级机构
			//设置用户的来源以及二级机构值
			CusAcct acct = cusPerBaseDTO.getCusAcct();
			acct.setCusSource(Constants.CUS_SOURCE_0);
			acct.setOrgId(secondOrgId);
			
			cusPerBaseDTO.setCusAcct(acct);
			
			cusPerBaseDTO.setCusRelation(cusRelation);
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			if (cusPerBaseDTO.getCusPerBase().getPid() > 0) {
				acctId = client.updateCusPerBase(cusPerBaseDTO);
			} else {
				acctId = client.addCusPerBase(cusPerBaseDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.info(e.toString());
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
	 * 个人客户查询调整
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/perList")
	public String cusList(ModelMap model) throws Exception {
		return cusper + "per_list";
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
	public void perListUrl(CusPerBaseDTO cusPerBaseDTO, HttpServletResponse response, HttpServletRequest request) {
		List<GridViewDTO> cusPers = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/*if (cusPerBaseDTO.getCusName() != null) {
				cusPerBaseDTO.setCusName(new String(cusPerBaseDTO.getCusName().getBytes("ISO8859-1"), "UTF-8"));
			}*/
			cusPerBaseDTO.setUserIds(getUserIds(request));
			// 获取当前登录人id
			//int userId = getShiroUser().getPid();
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			//cusPerBaseDTO.setUserId(userId);
			cusPers = client.getCusPerBases(cusPerBaseDTO);
			int total = client.getTotal(cusPerBaseDTO);
			map.put("total", total);
			map.put("rows", cusPers);
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
	 * @Description: 条件查询所有的人员pid
	 * @param pid
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: wangheng
	 * @date: 2015年4月23日
	 */
	@RequestMapping(value = "perExcelExportList")
	@ResponseBody
	public void perExcelExportList(String pids, HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		// CusPerBaseDTO cusPerBaseDTO = new CusPerBaseDTO();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			List<GridViewDTO> list = client.searchPerExportByPid(pids);
			map.put("bean", list);
			TemplateFile template = cl.getTemplateFile("PEROUT");

			if (!template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map, path, "个人客户导出" + "." + template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".") + 1), response, request);
			}
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
	}

	/**
	 * 
	 * @Description: 条件查询所有的人员pid
	 * @param pid
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: wangheng
	 * @date: 2015年4月27日
	 */
	@RequestMapping(value = "comExcelExportList")
	@ResponseBody
	public void comExcelExportList(String pids, HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			List<GridViewDTO> list = client.searchComExportByPid(pids);
			map.put("bean", list);
			TemplateFile template = cl.getTemplateFile("COMOUT");

			if (!template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map, path, "企业客户导出" + "." + template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".") + 1), response, request);
			}
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
	}

	/**
	 * 
	 * @Description: 个人旗下公司的导出
	 * @param pid
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: wangheng
	 * @date: 2015年4月27日
	 */
	@RequestMapping(value = "listUnderExcelExportList")
	@ResponseBody
	public void listUnderExcelExportList(String pids, HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			List<GridViewDTO> list = client.listUnderExcelExportList(pids);
			map.put("bean", list);
			TemplateFile template = cl.getTemplateFile("COMOUT");

			if (!template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map, path, "旗下公司导出.xls", response, request);
			}
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
	}

	/**
	 * 
	 * @Description: 条件查询所有的人员信息
	 * @param cusPerson
	 *            人员对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月9日 下午9:30:03
	 */
	@RequestMapping(value = "getAllCusPerson", method = RequestMethod.POST)
	public void getAllCusPerson(CusPerson cusPerson, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		// 需要返回的map
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			clientFactory = getFactory(serviceModuel, "CusPerService");
			List<CusPerson> list = new ArrayList<CusPerson>();
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			// 获取所有的人员信息
			list = client.getAllCusPerson(cusPerson);
			// 获取当前条件的人员总数
			int total = client.getAllCusPersonCount(cusPerson);
			// 赋值分页参数
			map.put("total", total);
			map.put("rows", list);
		} catch (ThriftServiceException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("条件查询所有人员信息:" + e.getMessage());
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("条件查询所有人员信息:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug("关闭资料出错:" + e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
	}

	// 企业客户Controller
	// 银行
	@RequestMapping(value = "listComBank")
	public String listComBank(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap model) throws Exception {
		model.put("acctId", acctId);
		model.put("comId", comId);
		model.put("comType", comType);
		model.put("cusType", "COM");
		return cuscom + "list_com_bank";
	}

	/**
	 * 查询企业客户银行信息列表
	 */
	@RequestMapping(value = "listBankUrl")
	public void listBankUrl(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "cusType", required = false) String cusType, @RequestParam(value = "rows", required = false) Integer rows, @RequestParam(value = "page", required = false) Integer page, HttpServletResponse response) throws Exception {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusAcctService.Client client = (com.xlkfinance.bms.rpc.customer.CusAcctService.Client) clientFactory.getClient();
			CusAcctBank bank = new CusAcctBank();
			CusAcct cusAcct = new CusAcct();
			cusAcct.setPid(acctId);
			bank.setCusAcct(cusAcct);
			bank.setRows(rows);
			bank.setPage(page);
			bank.setCusType("PER".equals(cusType) ? 1 : 2);
			list = client.getCusAcctBanks(bank);
			int total = client.getTotal(bank);
			map.put("total", total);
			map.put("rows", list);
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

	@RequestMapping(value = "/saveComBase", method = RequestMethod.POST)
	public String saveComBase(CusComBaseDTO cusComBaseDTO) throws Exception {
		return cus + "edit_cus_per";
	}

	/**
	 * 企业客户资信评估列表
	 */
	@RequestMapping(value = "/listComEst")
	public String listComEst(String id, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap modeld) throws Exception {
		modeld.put("acctId", acctId);
		modeld.put("comId", comId);
		modeld.put("cusType", 2);
		modeld.put("comType", comType);
		return cuscom + "list_com_est";
	}

	/**
	 * 企业客户业务往来列表
	 */
	@RequestMapping(value = "/listComBusiness")
	public String listComBusiness(String id, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap modeld) throws Exception {
		modeld.put("acctId", acctId);
		modeld.put("comId", comId);
		modeld.put("comType", comType);
		return cuscom + "list_com_business";
	}

	/**
	 * 企业客户企业控制人列表信息
	 */
	@RequestMapping(value = "/listComPerson")
	public String listComPerson(String id, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, @RequestParam(value = "relationType", required = false) Integer relationType, ModelMap modeld) throws Exception {
	   CusAcctService.Client service = (com.xlkfinance.bms.rpc.customer.CusAcctService.Client) getService(serviceModuel, "CusAcctService");
      CusAcct acct = service.getAcctById(acctId);
      //解决机构的附加信息中的“企业法人信息”列表查不出数据bug
      if (acct.getCusType()==3) {
         relationType=5;
      }
		modeld.put("acctId", acctId);
		modeld.put("comId", comId);
		modeld.put("relationType", relationType);
		modeld.put("comType", comType);
		return cuscom + "list_com_person";
	}

	/**
	 * 企业客户基础信息列表
	 */
	@RequestMapping(value = "listComBase")
	public String editComBase(String id) throws Exception {
		return cuscom + "searchComBaseInfo";
	}

	/**
	 * 跳转到企业客户基础信息页面
	 */
	@RequestMapping(value = "editComBases")
	public String addComBase(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "flag", required = false) Integer flag, @RequestParam(value = "type", required = false) Integer type, @RequestParam(value = "currUserPid", required = false) Integer currUserPid, @RequestParam(value = "comType", required = false) Integer comType,@RequestParam(value = "orgId", required = false) Integer orgId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		BaseClientFactory clientFactory2 = getFactory(serviceModuel, "CusAcctService");
		BaseClientFactory clientFactory3 = getFactory(serviceModuel, "CusPerService");
		CusPerService.Client client3 = (CusPerService.Client) clientFactory3.getClient();
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			CusAcctService.Client client2 = (CusAcctService.Client) clientFactory2.getClient();
			if (orgId!=null&&orgId>0) {
			   CusComBase comBase = client.getComBaseByOrgId(orgId);
			   comId = comBase.getPid();
			   acctId = comBase.getCusAcct().getPid();
			}
			if (comId != null) {
				CusComBaseDTO cusComBaseDTO = client.getCusComBase(comId);
			   orgId = cusComBaseDTO.getCusComBase().getOrgId();
				model.put("acctId", cusComBaseDTO.getCusAcct().getPid());
				model.put("cusComBaseDTO", cusComBaseDTO);
				if (cusComBaseDTO.getCusComBase().getComOwnId() > 0) {
					CusPerson cusPerson = client2.getCusPerson(cusComBaseDTO.getCusComBase().getComOwnId());
					model.put("cusPerson", cusPerson);
				}
				if (acctId != null && acctId != 0) {
					acctId = cusComBaseDTO.getCusAcct().getPid();
					cusComBaseDTO = client.selectPmUserIdByAcctId(acctId);
					int pmUserId = cusComBaseDTO.getCusAcct().getPmUserId();
					model.put("pmUserId", pmUserId);
				}

			}
			if (flag != 1) {
				int num = client3.selectIsAcctManager(currUserPid);
				model.put("num", num);

			} else {
				model.put("num", 2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				} finally {
					if (clientFactory2 != null) {
						try {
							clientFactory2.destroy();
						} catch (ThriftException e) {
							if (logger.isDebugEnabled()) {
								logger.debug(e.getMessage());
							}
						} finally {
							if (clientFactory3 != null) {
								try {
									clientFactory3.destroy();
								} catch (ThriftException e) {
									if (logger.isDebugEnabled()) {
										logger.debug(e.getMessage());
									}
								}
							}
						}
					}
				}
			}
		}
		model.put("type", type);
		model.put("flag", flag);

		model.put("comId", comId);
		model.put("comType", comType);
		if (orgId!=null&&orgId>0) {
		   return "aom/org/com/edit_com_base";
      }else{
         return cuscom + "edit_com_base";
      }
	}

	/**
	 * 企业客户财务信息
	 */
	@RequestMapping(value = "/editComFinance")
	public String editComFinance(String id) throws Exception {
		return cuscom + "edit_com_finance";
	}

	/**
	 * 企业客户员工结构信息
	 */
	@RequestMapping(value = "editComStaff")
	public String editComStaff(String id, CusComStaff cusComStaff, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap model, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			List<CusComStaff> list = client.getCusComStaff(comId);
			CusComStaffDTO cusComStaffDTO = new CusComStaffDTO(list);
			model.put("cusComStaffDTO", cusComStaffDTO);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}

		model.put("comId", comId);
		model.put("acctId", acctId);
		model.put("comType", comType);
		return cuscom + "edit_com_staff";
	}

	/**
	 * 保存企业客户员工结构信息
	 */
	@RequestMapping(value = "saveComStaff")
	public String saveComStaff(CusComStaffDTO cusComStaffDTO, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		CusComService.Client client = (CusComService.Client) clientFactory.getClient();
		int pid = 0;
		try {
			for (CusComStaff cusComStaffs : cusComStaffDTO.getStaffs()) {
				pid = cusComStaffs.getPid();
			}
			if (pid > 0) {
				client.updateCusComStaffs(cusComStaffDTO);
			} else {
				client.addCusComStaff(cusComStaffDTO.getStaffs());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "edit_com_staff";
	}

	/**
	 * 企业客户企业联系人列表
	 */
	@RequestMapping(value = "listComContact")
	public String listComContact(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap modeld) throws Exception {
		modeld.put("acctId", acctId);
		modeld.put("comId", comId);
		modeld.put("comType", comType);
		return cuscom + "list_com_contact";
	}

	/**
	 * 企业客户企业联系人列表页面
	 */
	@RequestMapping(value = "saveComContact")
	public String saveComContacts(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, ModelMap model) throws Exception {
		model.put("comId", comId);
		model.put("acctId", acctId);
		return cuscom + "edit_com_contact";
	}

	/**
	 * 查询企业联系人信息列表
	 */
	@RequestMapping(value = "searchComContact")
	public void searchComContacts(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "rows", required = false) Integer rows, @RequestParam(value = "page", required = false) Integer page, HttpServletResponse response) throws Exception {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		CusComContact comContact = new CusComContact();
		CusComBase base = new CusComBase();
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			base.setPid(comId);
			comContact.setPage(page);
			comContact.setRows(rows);
			comContact.setCusComBase(base);
			CusComService.Client client = (com.xlkfinance.bms.rpc.customer.CusComService.Client) clientFactory.getClient();
			list = client.getCusComContacts(comContact);

			int total = client.getTotal(comContact);
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
	}

	/**
	 * 新增企业客户企业联系人列表
	 */
	@RequestMapping(value = "/addCpyContact")
	public String addComContact(CusComContact cusComContact, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			int pid = cusComContact.getPid();
			if (pid > 0) {
				cusComContact.setStatus(1);
				client.updateCusComContacts(cusComContact);

			} else {
				cusComContact.setStatus(1);
				client.addCusComContact(cusComContact);

			}
			outputJson("success", response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "list_com_contact";
	}

	/**
	 * 
	 * @Description: 删除企业联系人信息
	 * @return 删除企业联系人信息
	 * @author: wangheng
	 * @date: 2014年12月25日
	 */
	@RequestMapping(value = "deleteComContact")
	@ResponseBody
	public String deleteComContacts(String pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			client.deleteCusComContact(pid);
			outputJson("success", response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "list_com_contact";
	}

	//

	/**
	 * 企业客户企业联系人信息
	 */
	@RequestMapping(value = "editComContact")
	public String editComContact(@RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comId", required = false) Integer comId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		try {
			if (pid != null) {
				CusComService.Client client = (CusComService.Client) clientFactory.getClient();
				CusComContact cusComContact = client.getCusComContact(pid);
				model.put("cusComContact", cusComContact);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		model.put("acctId", acctId);
		model.put("comId", comId);
		return cuscom + "edit_com_contact";
	}

	/**
	 * 企业客户管理团队列表
	 */
	@RequestMapping(value = "listComTeam")
	public String listComTeam(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap modeld) throws Exception {
		modeld.put("acctId", acctId);
		modeld.put("comId", comId);
		modeld.put("comType", comType);
		return cuscom + "list_com_team";
	}

	/**
	 * 添加企业客户管理团队列表
	 */
	@RequestMapping(value = "saveComTeam")
	public String saveComTeams(CusComTeam cusComTeam, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			int pid = cusComTeam.getPid();
			if (pid > 0) {
				cusComTeam.setStatus(1);
				client.updateCusComTeam(cusComTeam);
			} else {
				cusComTeam.setStatus(1);
				client.addCusComTeam(cusComTeam);
			}
			outputJson("success", response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "list_com_team";
	}

	/**
	 * 查询管理团队信息列表
	 */
	@RequestMapping(value = "searchComTeam")
	public void searchComTeams(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, HttpServletResponse response) throws Exception {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusComService.Client client = (com.xlkfinance.bms.rpc.customer.CusComService.Client) clientFactory.getClient();
			CusComTeam cusComTeam = new CusComTeam();
			cusComTeam.setPage(page);
			cusComTeam.setRows(rows);
			CusComBase comBase = new CusComBase();
			comBase.setPid(comId);
			cusComTeam.setCusComBase(comBase);
			list = client.getCusComTeams(cusComTeam);
			int total = client.getTotals(cusComTeam);
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
	}

	/**
	 * 企业客户管理团队信息
	 */
	@RequestMapping(value = "/editComTeam")
	public String editComTeam(@RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comId", required = false) Integer comId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		try {
			if (pid != null) {
				CusComService.Client client = (CusComService.Client) clientFactory.getClient();
				CusComTeam cusComTeam = client.getCusComTeam(pid);
				model.put("cusComTeam", cusComTeam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		model.put("acctId", acctId);
		model.put("comId", comId);
		return cuscom + "edit_com_team";
	}

	/**
	 * 企业客户团队管理删除
	 */
	@RequestMapping(value = "deleteCusComTeam")
	public String deleteCusComTeams(String pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			client.deleteCusComTeam(pid);
			outputJson("success", response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "edit_com_team";
	}

	/**
	 * 跳转页面企业客户债务信息
	 */
	@RequestMapping(value = "/listComDebt")
	public String listComDebts(String type, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap model) throws Exception {
		model.put("debtType", 1);
		model.put("comId", comId);
		model.put("acctId", acctId);
		model.put("comType", comType);
		return cuscom + "list_debt_right";// list_com_debt
	}

	/**
	 * 企业客户债务信息
	 */
	@RequestMapping(value = "editComDebt")
	public String editComDebt(@RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "debtType", required = false) Integer debtType, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		try {
			if (pid != null) {
				CusComService.Client client = (CusComService.Client) clientFactory.getClient();
				CusComDebt cusComDebt = client.getCusComDebt(pid);
				model.put("cusComDebt", cusComDebt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}

		}
		model.put("comId", comId);
		model.put("acctId", acctId);
		model.put("debtType", debtType);
		return cuscom + "edit_debt_right";// 债务
	}

	/**
	 * 查询企业客户债务列表
	 */
	@RequestMapping(value = "searchlistComDebt")
	public void searchComDebt(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "debtType", required = false) Integer debtType, @RequestParam(value = "rows", required = false) Integer rows, @RequestParam(value = "page", required = false) Integer page, HttpServletResponse response) throws Exception {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusComService.Client client = (com.xlkfinance.bms.rpc.customer.CusComService.Client) clientFactory.getClient();
			CusComDebt cusComDebt = new CusComDebt();
			CusComBase comBase = new CusComBase();
			comBase.setPid(comId);
			cusComDebt.setCusComBase(comBase);
			cusComDebt.setDebtType(debtType.toString());
			cusComDebt.setRows(rows);
			cusComDebt.setPage(page);
			list = client.getCusComDebts(cusComDebt);
			int total = client.getTotalDept(cusComDebt);
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
	}

	/**
	 * 查询企业客户债权列表
	 */
	@RequestMapping(value = "searchlistComDebtRight")
	public void searchlistComDebtRight(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "debtType", required = false) Integer debtType, @RequestParam(value = "rows", required = false) Integer rows, @RequestParam(value = "page", required = false) Integer page, HttpServletResponse response) throws Exception {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		debtType = 2;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusComService.Client client = (com.xlkfinance.bms.rpc.customer.CusComService.Client) clientFactory.getClient();
			CusComDebt cusComDebt = new CusComDebt();
			CusComBase comBase = new CusComBase();
			comBase.setPid(comId);
			cusComDebt.setCusComBase(comBase);
			cusComDebt.setDebtType(debtType.toString());
			cusComDebt.setRows(rows);
			cusComDebt.setPage(page);
			list = client.getCusComDebtss(cusComDebt);
			int total = client.getTotalDepts(cusComDebt);
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
	}

	/**
	 * 添加企业客户债务列表
	 */
	@RequestMapping(value = "saveComDebt")
	public String saveComDebt(CusComDebt cusComDebt, HttpServletResponse response, ModelMap modelMap) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			int pid = cusComDebt.getPid();
			String type = "1";
			if (pid > 0) {
				cusComDebt.setDebtType(type);
				client.updateCusComDebt(cusComDebt);
			} else {
				client.addCusComDebt(cusComDebt);
			}
			outputJson("success", response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		int cusComBasePid = cusComDebt.getPid();
		modelMap.put("cusComBasePid", cusComBasePid);
		return cuscom + "list_debt_right";
	}

	/**
	 * 删除客户债务信息
	 */
	@RequestMapping(value = "deleteCusComDebt")
	public String deleteCusComDebt(String pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			client.deleteCusComDebt(pid);
			outputJson("success", response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "list_com_debt";
	}

	// 债权
	@RequestMapping(value = "listDebtRight")
	public String listDebtRight(String id, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap model) throws Exception {
		model.put("debtType", 2);
		model.put("comId", comId);
		model.put("acctId", acctId);
		model.put("comType", comType);
		return cuscom + "list_com_debt";
	}

	/**
	 * 企业客户债权信息
	 */
	@RequestMapping(value = "editDebtRight")
	public String editDebtRight(String id, @RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		try {
			if (pid != null) {
				CusComService.Client client = (CusComService.Client) clientFactory.getClient();
				CusComDebt cusComDebt = client.getCusComDebtRight(pid);
				model.put("cusComDebt", cusComDebt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}

		}
		model.put("comId", comId);
		model.put("acctId", acctId);
		return cuscom + "edit_com_debt";
	}

	/**
	 * 添加企业客户债权列表
	 */
	@RequestMapping(value = "saveComDebtRight")
	public String saveComDebtRight(CusComDebt cusComDebt, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			int pid = cusComDebt.getPid();
			String type = "2";

			if (pid > 0) {
				cusComDebt.setDebtType(type);
				client.updateCusComDebt(cusComDebt);
			} else {
				cusComDebt.setDebtType(type);
				client.addCusComDebt(cusComDebt);
			}
			outputJson("success", response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "list_com_debt";
	}

	/**
	 * 企业客户对外担保列表
	 */
	@RequestMapping(value = "listComAssure")
	public String listComAssure(String id, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap model) throws Exception {
		model.put("comId", comId);
		model.put("acctId", acctId);
		model.put("comType", comType);
		return cuscom + "list_com_assure";
	}

	/**
	 * 查询企业客户对外担保列表
	 */
	@RequestMapping(value = "searchComAssure")
	public void searchComAssure(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "rows", required = false) Integer rows, @RequestParam(value = "page", required = false) Integer page, HttpServletResponse response) throws Exception {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusComService.Client client = (com.xlkfinance.bms.rpc.customer.CusComService.Client) clientFactory.getClient();
			CusComAssure comAssure = new CusComAssure();
			CusComBase comBase = new CusComBase();
			comBase.setPid(comId);
			comAssure.setCusComBase(comBase);
			comAssure.setPage(page);
			comAssure.setRows(rows);
			list = client.getCusComAssures(comAssure);
			int total = client.getTotalAssure(comAssure);
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
	}

	/**
	 * 编辑企业客户对外担保信息
	 */
	@RequestMapping(value = "editComAssure")
	public String editComAssure(@RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		try {
			if (pid != null) {
				CusComService.Client client = (CusComService.Client) clientFactory.getClient();
				CusComAssure cusComAssure = client.getCusComAssure(pid);
				model.put("cusComAssure", cusComAssure);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}

		}
		model.put("comId", comId);
		model.put("acctId", acctId);
		return cuscom + "edit_com_assure";
	}

	/**
	 * 保存企业客户对外担保信息
	 */
	@RequestMapping(value = "saveComAssure")
	public String editComAssure(CusComAssure cusComAssure, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			int pid = cusComAssure.getPid();
			if (pid > 0) {
				cusComAssure.setStatus(1);
				client.updateCusComAssure(cusComAssure);
			} else {
				cusComAssure.setStatus(1);
				client.addCusComAssure(cusComAssure);
			}
			outputJson("success", response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}

		}
		return cuscom + "list_com_assure";
	}

	/**
	 * 删除客户对外担保信息
	 */
	@RequestMapping(value = "deleteComAssure")
	public String deleteComAssure(String pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			client.deleteCusComAssure(pid);
			outputJson("success", response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}

		}
		return cuscom + "list_com_assure";
	}

	/**
	 * 查询企业客户对外投资列表
	 */
	@RequestMapping(value = "searchComInvest")
	public String searchComInvests(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, HttpServletResponse response) throws Exception {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusComService.Client client = (com.xlkfinance.bms.rpc.customer.CusComService.Client) clientFactory.getClient();
			CusComInvest cusComInvest = new CusComInvest();
			CusComBase comBase = new CusComBase();
			comBase.setPid(comId);
			cusComInvest.setCusComBase(comBase);
			cusComInvest.setRows(rows);
			cusComInvest.setPage(page);
			list = client.getCusComInvests(cusComInvest);
			int total = client.getTotalInvest(cusComInvest);
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
		return cuscom + "list_com_invest";
	}

	/**
	 * 保存企业客户对外担保信息
	 */
	@RequestMapping(value = "saveComInvest")
	public String saveComInvests(CusComInvest cusComInvest, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			int pid = cusComInvest.getPid();
			if (pid > 0) {
				client.updateCusComInvest(cusComInvest);
			} else {
				client.addCusComInvest(cusComInvest);
			}
			outputJson("success", response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}

		return cuscom + "list_com_invest";
	}

	/**
	 * 删除客户对外投资信息
	 */
	@RequestMapping(value = "deleteComInvest")
	public String deleteComInvest(String pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			client.deleteCusComInvest(pid);
			outputJson("success", response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "list_com_assure";
	}

	/**
	 * 企业客户对外投资列表
	 */
	@RequestMapping(value = "/listComInvest")
	public String listComInvest(String id, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap model) throws Exception {
		model.put("comId", comId);
		model.put("acctId", acctId);
		model.put("comType", comType);
		return cuscom + "list_com_invest";
	}

	/**
	 * 企业客户对外投资信息
	 */
	@RequestMapping(value = "editComInvest")
	public String editComInvest(@RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		try {
			if (pid != null) {
				CusComService.Client client = (CusComService.Client) clientFactory.getClient();
				CusComInvest cusComInvest = client.getCusComInvest(pid);
				model.put("cusComInvest", cusComInvest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		model.put("comId", comId);
		model.put("acctId", acctId);
		return cuscom + "edit_com_invest";
	}

	/**
	 * 企业客户获得奖励信息列表
	 */
	@RequestMapping(value = "listComReward")
	public String listComReward(String id, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap model) throws Exception {
		model.put("comId", comId);
		model.put("acctId", acctId);
		model.put("comType", comType);
		return cuscom + "list_com_reward";
	}

	/**
	 * 查询企业客户获奖情况
	 */
	@RequestMapping(value = "searchComReward")
	public String searchComReward(@RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, HttpServletResponse response) throws Exception {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusComService.Client client = (com.xlkfinance.bms.rpc.customer.CusComService.Client) clientFactory.getClient();
			CusComReward comReward = new CusComReward();
			CusComBase comBase = new CusComBase();
			comBase.setPid(comId);
			comReward.setCusComBase(comBase);
			comReward.setRows(rows);
			comReward.setPage(page);
			list = client.getCusComRewards(comReward);
			int total = client.getTotalReward(comReward);
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
		return cuscom + "list_com_invest";
	}

	/**
	 * 保存企业客户获奖信息
	 */
	@RequestMapping(value = "saveComReward")
	public String saveComReward(CusComReward cusComReward, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			int pid = cusComReward.getPid();
			if (pid > 0) {
				client.updateCusComReward(cusComReward);
			} else {
				client.addCusComReward(cusComReward);
			}
			outputJson("success", response);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "list_com_reward";
	}

	/**
	 * 删除客户获奖信息
	 */
	@RequestMapping(value = "deleteComReward")
	public String deleteComReward(String pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			client.deleteCusComReward(pid);
			outputJson("success", response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}

		return cuscom + "list_com_reward";
	}

	/**
	 * 企业客户获得奖励信息
	 */
	@RequestMapping(value = "/editComReward")
	public String editComReward(@RequestParam(value = "pid", required = false) Integer pid, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		try {
			if (pid != null) {
				CusComService.Client client = (CusComService.Client) clientFactory.getClient();
				CusComReward cusComReward = client.getCusComReward(pid);
				model.put("cusComReward", cusComReward);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		model.put("comId", comId);
		model.put("acctId", acctId);
		return cuscom + "edit_com_reward";
	}

	/**
	 * 查询企业客户获得奖励信息
	 */
	@RequestMapping(value = "/searchComBaseInfo")
	public String searchComBase(String id) throws Exception {
		return cuscom + "searchComBaseInfo";
	}

	/**
	 * 
	 * @Description: 查询企业客户信息
	 * @param user
	 *            企业客户
	 * @return 企业客户信息查询页面
	 * @author: wangheng
	 * @date: 2014年12月20日
	 */
	@RequestMapping(value = "getComBaseInfo")
	@ResponseBody
	public void getCusComBases(CusComBase cusComBase, HttpServletResponse response) {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusComService.Client client = (Client) clientFactory.getClient();
			// 获取当前登录人id
			int userId = getShiroUser().getPid();
			cusComBase.setUserId(userId);
			list = client.getCusComBases(cusComBase);
			int total = client.getTotalCusComBases(cusComBase);
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 查询所有企业的法人相关信息
	 * @param cusDTO
	 *            客户信息对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 下午5:15:15
	 */
	@RequestMapping(value = "getEnterpriseLegalPersonList", method = RequestMethod.POST)
	public void getEnterpriseLegalPersonList(CusDTO cusDTO, HttpServletResponse response) {
		// 返回到前端的HashMap转换JSON
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
			CusComService.Client client = (Client) clientFactory.getClient();
			int ecoTrade = cusDTO.getEcoTrade();
			if (ecoTrade == 0) {
				cusDTO.setEcoTrade(-1);
			}
			// 获取所有客户对象
			List<CusDTO> list = client.getEnterpriseLegalPersonList(cusDTO);
			// 获取所有客户的总数
			int total = client.getEnterpriseLegalPersonCount(cusDTO);
			// 设置分页属性
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询所有企业的法人相关信息:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 删除企业客户信息
	 * @return 删除企业客户信息页面
	 * @author: wangheng
	 * @date: 2014年12月25日
	 */
	@RequestMapping(value = "deleteComBaseInfo")
	@ResponseBody
	public String deleteComBaseInfo(String pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			client.deleteCusComBase(pid);
			outputJson("success", response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscomon + "list_bank_acct";
	}

	@ResponseBody
	@RequestMapping(value = "saveComBaseInfo", method = RequestMethod.POST)
	public Map<String, Object> saveComBaseInfo(CusComBaseDTO cusComBaseDTO, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Map<String, Integer> modelMaps = new HashMap<String, Integer>();
		int comId = 0;
		int acctId = 0;
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			if (cusComBaseDTO.getCusComBase().getPid() > 0) {
				modelMaps = client.updateCusComBase(cusComBaseDTO);
			} else {
				modelMaps = client.addCusComBase(cusComBaseDTO);
			}
			modelMap.put("comId", cusComBaseDTO.getCusComBase().getPid());
		} catch (Exception e) {
			e.printStackTrace();
			// logger.info(e.toString());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		acctId = modelMaps.get("acctId");
		comId = modelMaps.get("comId");
		Json j = super.getSuccessObj();
		j.getHeader().put("comId", comId);
		j.getHeader().put("acctId", acctId);
		outputJson(j, response);
		return modelMap;
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/gotoComFinancialStatusByAnnualPage")
	public String gotoComFinancialStatusByAnnualPage(int startYear, int endYear, boolean isDelete, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "comType", required = false) Integer comType, ModelMap modeld) throws Exception {
		modeld.put("acctId", acctId);
		modeld.put("comId", comId);
		modeld.put("startYear", startYear);
		modeld.put("endYear", endYear);
		modeld.put("isDelete", isDelete);
		modeld.put("comType", comType);

		return cuscom + "list_com_financialStatusOverview";
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ,点击查询按钮调用该方法
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/searchCusComFinanceReportByAnnual")
	@ResponseBody
	private void searchCusComFinanceReportByAnnual(HttpServletRequest request, HttpServletResponse response, int comId, @RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows) throws Exception {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (Client) clientFactory.getClient();
			String startYearStr = request.getParameter("startYear");
			String endYearStr = request.getParameter("endYear");

			// startYearStr = "2013";
			// endYearStr = "2016";

			if (StringUtils.isEmpty(startYearStr) || StringUtils.isEmpty(endYearStr)) {
				outputJson(new HashMap<String, Object>(), response);
				return;
			}

			String startYearSub = startYearStr.substring(0, 4);
			String endYearSub = endYearStr.substring(0, 4);

			int startYear = Integer.parseInt(startYearSub);
			int endYear = Integer.parseInt(endYearSub);

			// 还需要通过企业Id
			// pct,当前页
			int intPage = Integer.parseInt((StringUtils.isEmpty(page) || "0".equals(page) || "NaN".equals(page)) ? "1" : page);
			// 每页显示条数
			int number = Integer.parseInt((StringUtils.isEmpty(rows) || "0".equals(rows) || "NaN".equals(rows)) ? "10" : rows);
			// 每页的开始记录 第一页为1 第二页为number +1
			int start = ((intPage - 1) * number) + 1;
			int numberAll = intPage * number;

			Map<String, Integer> myMap = new HashMap<String, Integer>();
			myMap.put("startYear", startYear);
			myMap.put("endYear", endYear);
			myMap.put("comId", comId);
			myMap.put("start", start);
			myMap.put("number", numberAll);
			List<CusComFinanceReportOverviewDTO> cusComFinanceReportList = client.selectCusComFinanceReportOverviewDTOByAnnual(myMap);

			int total = client.obtainCusComFinanceReportOverviewCount(myMap);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", cusComFinanceReportList);
			map.put("total", total);
			// map.put("count", "40");// 总共有多少条记录

			outputJson(map, response);
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
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ,点击查询按钮JSP调用该方法 ,异步调用，因多态问题写2个方法
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/searchCusComFinanceReportByAnnualFromJsp")
	@ResponseBody
	public void searchCusComFinanceReportByAnnualFromJsp(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows, ModelMap modeld) throws Exception {

		modeld.put("acctId", acctId);
		modeld.put("comId", comId);

		searchCusComFinanceReportByAnnual(request, response, comId, page, rows);
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ,customer.js初始化调用该方法 ,异步调用，因多态问题写2个方法
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/searchCusComFinanceReportByAnnualFromJS")
	@ResponseBody
	public void searchCusComFinanceReportByAnnualFromJS(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows, ModelMap modeld) throws Exception {

		modeld.put("acctId", acctId);
		modeld.put("comId", comId);

		searchCusComFinanceReportByAnnual(request, response, comId, page, rows);
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ,删除所有已经选择的报表
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/deleteAllSelectedFinancialReport", method = RequestMethod.POST)
	@ResponseBody
	public void deleteAllSelectedFinancialReport(HttpServletRequest request, HttpServletResponse response, @RequestBody List<Object> array) throws IOException {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {

			List<CusComFinanceReportOverviewDTO> financialReportList = new ArrayList<CusComFinanceReportOverviewDTO>();
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(array);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject h = (JSONObject) JSONObject.toJSON(jsonArray.get(i));
				CusComFinanceReportOverviewDTO ccfrDTO = JSONArray.toJavaObject(h, CusComFinanceReportOverviewDTO.class);
				financialReportList.add(ccfrDTO);
			}

			CusComService.Client client = (Client) clientFactory.getClient();
			// 批量删除企业财务报表
			client.deleteCusComFinanceReportByPIDUseBatch(financialReportList);
			// 批量删除企业财务报表-资产负债表记录
			client.deleteCusComBalanceSheetByReportIDUseBatch(financialReportList);
			// 批量删除利润表记录
			client.deleteCusComIncomeReportByReportIDUseBatch(financialReportList);
			// 批量删除现金流量表记录
			client.deleteCusComCashFlowReportByReportIDUseBatch(financialReportList);

			response.getWriter().write("succDelete");
		} catch (Exception e) {
			response.getWriter().write("failDelete");
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
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ,单行删除已经选择的报表
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/deleteSingleSelectedFinancialReport", method = RequestMethod.POST)
	@ResponseBody
	public void deleteSingleSelectedFinancialReport(HttpServletRequest request, HttpServletResponse response) throws IOException {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {

			String reportIDStr = request.getParameter("reportId");
			if (StringUtils.isEmpty(reportIDStr)) {
				response.getWriter().write("failDelete");
				return;
			}

			int reportID = Integer.parseInt(reportIDStr);

			CusComService.Client client = (Client) clientFactory.getClient();

			// 删除企业财务报表
			client.deleteCusComFinanceReportByPID(reportID);

			// 删除资产负债表
			client.deleteCusComBalanceSheetByReportID(reportID);

			// 删除利润表
			client.deleteCusComIncomeReportByReportID(reportID);

			// 删除现金流量表
			client.deleteCusComCashFlowReportByReportID(reportID);

			response.getWriter().write("succDelete");
		} catch (Exception e) {
			response.getWriter().write("failDelete");
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
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ,单行编辑已经选择的报表
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/initEditSingleSelectedFinancialReport", method = RequestMethod.POST)
	@ResponseBody
	public void initEditSingleSelectedFinancialReport(HttpServletRequest request, HttpServletResponse response) throws IOException {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {

			// 初始化左边的负债表initLeftCusComBalanceSheet

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
	}

	/**
	 * 
	 * @Description: 财务信息录入录入
	 * @return 财务信息录入录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/initFinancialInfo")
	public String initFinancialInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "reportId", required = false) String reportId, @RequestParam(value = "isEditReport", required = false) String isEditReport, @RequestParam(value = "reportPeriod", required = false) String reportPeriod, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, ModelMap modeld) throws Exception {
		modeld.put("reportId", reportId == null ? "" : reportId);
		modeld.put("isEditReport", isEditReport == null ? "" : isEditReport);
		reportPeriod = reportPeriod == null ? "" : reportPeriod;
		modeld.put("reportPeriod", reportPeriod);
		modeld.put("acctId", acctId);
		modeld.put("comId", comId);

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (Client) clientFactory.getClient();

			if (StringUtils.isEmpty(reportId)) {
				modeld.put("hasEditLeftBalanceSheetData", "noData");
				modeld.put("hasEditRightBalanceSheetData", "noData");
				modeld.put("hasEditIncomeReportData", "noData");
				modeld.put("hasEditCaseFlowData", "noData");
				modeld.put("hasEditCaseFlowITData", "noData");
			} else {
				// 1,判断负债表左边的资产类是否有历史数据
				List<BalanceSheetEditDTO> leftList = client.initLeftEditCusComBalanceSheetByReportId(Integer.parseInt(reportId));
				if (leftList.isEmpty()) {
					modeld.put("hasEditLeftBalanceSheetData", "noData");
				}

				// 2,判断负债表右边的负债类是否有历史数据
				List<BalanceSheetEditDTO> rightList = client.initRightEditCusComBalanceSheetByReportId(Integer.parseInt(reportId));
				if (rightList.isEmpty()) {
					modeld.put("hasEditRightBalanceSheetData", "noData");
				}

				// 3,判断利润表是否有原始数据
				List<CusComIncomeReportEditDTO> cusComIncomeReportEditDTOList = client.initCusComIncomeReportByReportId(Integer.parseInt(reportId));
				if (cusComIncomeReportEditDTOList.isEmpty()) {
					modeld.put("hasEditIncomeReportData", "noData");
				}

				// 4,判断现金流是否有原始数据
				List<CashFlowReportEditDTO> cashFlowReportEditDTOList = client.initCashFlowReportByReportId(Integer.parseInt(reportId));
				// 如果有reportId对应的列，则显示所有数据。没有reportId数据，则只显示项目名
				if (cashFlowReportEditDTOList.isEmpty()) {
					modeld.put("hasEditCaseFlowData", "noData");
				}

				// 5,判断现金流补充材料是否有原始数据
				List<CashFlowReportEditDTO> cashFlowReportEditDTOList2 = client.initCashFlowReportSupplementByReportId(Integer.parseInt(reportId));
				// 如果有reportId对应的列，则显示所有数据。没有reportId数据，则只显示项目名
				if (cashFlowReportEditDTOList2.isEmpty()) {
					modeld.put("hasEditCaseFlowITData", "noData");
				}
			}
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

		return cuscom + "save_com_financialInfo";
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/listProfits")
	@ResponseBody
	public String listProfits(HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		try {
			CusComService.Client client = (Client) clientFactory.getClient();
			List<CusComIncomeReportMeta> cusComIncomeReportMetaList = client.selectAllCusComIncomeReportMeta();

			// 输出
			outputJson(cusComIncomeReportMetaList, response);
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

		return cuscom + "list_com_financialStatusOverview";
	}

	/**
	 * 
	 * @Description: 初始化利润表的序号与项目
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/initCusComIncomeReport")
	@ResponseBody
	public void initCusComIncomeReport(HttpServletRequest request, HttpServletResponse response, String isEditReport, String reportId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		CusComService.Client client = (Client) clientFactory.getClient();
		try {

			if (StringUtils.isEmpty(reportId)) {
				List<CusComIncomeReportMeta> cusComIncomeReportMetaList = client.selectAllCusComIncomeReportMeta();
				outputJson(cusComIncomeReportMetaList, response);
			} else {
				List<CusComIncomeReportEditDTO> cusComIncomeReportEditDTOList = client.initCusComIncomeReportByReportId(Integer.parseInt(reportId));
				// 如果有reportId对应的列，则显示所有数据。没有reportId数据，则只显示项目名
				if (!cusComIncomeReportEditDTOList.isEmpty()) {
					outputJson(cusComIncomeReportEditDTOList, response);
				} else {
					List<CusComIncomeReportMeta> cusComIncomeReportMetaList = client.selectAllCusComIncomeReportMeta();
					outputJson(cusComIncomeReportMetaList, response);
				}
			}
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

		// return cuscom + "list_com_financialStatusOverview";
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ，保存利润表数据
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCusComIncomeReport")
	public void saveCusComIncomeReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// response.setContentType("text/html;charset=UTF-8");
		// request.setCharacterEncoding("UTF-8");
		// response.setHeader("Cache-Control", "no-cache");

		String saveCusComIncomeReportResult = "";

		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");

		String content = request.getParameter("content");
		content = URLDecoder.decode(content, "utf-8");

		List<ProfitDTO> profitDTOList = JSON.parseObject(content, new TypeToken<List<ProfitDTO>>() {
			private static final long serialVersionUID = -6822918771393132681L;
		}.getType());

		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();

			// 没有Pid则初始化 ，否则直接获取主键（企业财务报表CUS_COM_FINANCE_REPORT）
			int reportId = obtainReportId(client, request);

			// 如果利润表(CUS_COM_INCOME_REPORT)里面已经存在该年月份的利润报表，则不允许再插入
			List<CusComIncomeReport> cusComIncomeReportList = client.selectCusComIncomeReportByReportId(reportId);
			if (!cusComIncomeReportList.isEmpty()) {
				saveCusComIncomeReportResult = "hasExistRecord";
				// outputJson(saveCusComIncomeReportResult, response);
				response.getWriter().write(saveCusComIncomeReportResult);
				return;
			}

			for (ProfitDTO profitDTO : profitDTOList) {
				double thisMonthVal = profitDTO.getThisMonthVal();
				double thisYearVal = profitDTO.getThisYearVal();

				CusComIncomeReport cusComIncomeReport = new CusComIncomeReport();
				cusComIncomeReport.setReportId(reportId);

				// 根据lineNum获得利润表项目表的PID
				// int lineNum = profitDTO.getLineNum();
				// CusComIncomeReportMeta cusComIncomeReportMeta =
				// client.selectCusComIncomeReportMetaByLineNum(lineNum);
				// cusComIncomeReport.setIncomeItemId(cusComIncomeReportMeta.getPid());
				cusComIncomeReport.setIncomeItemId(profitDTO.getPid());
				cusComIncomeReport.setThisMonthVal(thisMonthVal);
				cusComIncomeReport.setThisYearVal(thisYearVal);
				cusComIncomeReport.setStatus(1);

				client.saveCusComIncomeReport(cusComIncomeReport);
			}

			saveCusComIncomeReportResult = "saveSucc";
		} catch (Exception e) {
			saveCusComIncomeReportResult = "saveFail";
			e.printStackTrace();
			// logger.info(e.toString());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		// outputJson(saveCusComIncomeReportResult, response);
		response.getWriter().write(saveCusComIncomeReportResult);

		// return modelMap;
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ，编辑保存利润表数据
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCusComIncomeReport")
	public void updateCusComIncomeReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String saveCusComIncomeReportResult = "";

		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");

		String content = request.getParameter("content");
		content = URLDecoder.decode(content, "utf-8");

		try {

			List<CusComIncomeReportEditDTO> cusComIncomeReportEditDTOList = JSON.parseObject(content, new TypeToken<List<CusComIncomeReportEditDTO>>() {
				private static final long serialVersionUID = 1L;
			}.getType());

			CusComService.Client client = (CusComService.Client) clientFactory.getClient();

			for (CusComIncomeReportEditDTO cusComIncomeReportEditDTO : cusComIncomeReportEditDTOList) {
				CusComIncomeReport cusComIncomeReport = new CusComIncomeReport();
				cusComIncomeReport.setPid(cusComIncomeReportEditDTO.getIrPid());
				cusComIncomeReport.setThisYearVal(cusComIncomeReportEditDTO.getThisYearVal());
				cusComIncomeReport.setThisMonthVal(cusComIncomeReportEditDTO.getThisMonthVal());

				client.updateCusComIncomeReport(cusComIncomeReport);
			}

			saveCusComIncomeReportResult = "editSucc";
		} catch (Exception e) {
			saveCusComIncomeReportResult = "editFail";
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

		response.getWriter().write(saveCusComIncomeReportResult);

	}

	/**
	 * 
	 * @Description: 查询企业信息
	 * @param cusDto
	 *            企业信息DTO
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年1月15日 上午10:42:10
	 */
	@RequestMapping(value = "getEnterpriseList", method = RequestMethod.POST)
	public void getEnterpriseList(CusDTO cusDto, HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			// 判断赋值
			if (cusDto.getEcoTrade() == 0) {
				cusDto.setEcoTrade(-1);
			}
			// 获取当前登录人id
			int userId = getShiroUser().getPid();
			cusDto.setUserId(userId);
			// 获取所有企业客户集合
			List<CusDTO> list = client.getEnterpriseList(cusDto);
			// 获取所有企业客户总数
			int count = client.getEnterpriseListCount(cusDto);
			// 赋值Map
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询企业信息列表-贷前:" + e.getMessage());
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
	 * @Description: 查询个人信息
	 * @param cusDto
	 *            个人信息DTO
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @param request 
	 * @date: 2015年1月15日 上午10:44:23
	 */
	@RequestMapping(value = "getPersonalList", method = RequestMethod.POST)
	public void getPersonalList(CusDTO cusDto, HttpServletResponse response, HttpServletRequest request) {
		// 需要返回的个人信息
		List<CusDTO> list = new ArrayList<CusDTO>();
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			cusDto.setUserIds(getUserIds(request));
			// 获取个人信息集合
			list = client.getPersonalList(cusDto);
			// 获取所有客户总数
			int count = client.getPersonalListCount(cusDto);
			map.put("rows", list);
			map.put("total", count);
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
	 * @Description: 查询个人信息-贷前
	 * @param cusDto
	 *            个人信息DTO
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @param request 
	 * @date: 2015年3月2日 上午11:28:02
	 */
	@RequestMapping(value = "getPersonalListTwo", method = RequestMethod.POST)
	public void getPersonalListTwo(CusDTO cusDto, HttpServletResponse response, HttpServletRequest request) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			cusDto.setUserIds(getUserIds(request));
			// 获取所有数据
			List<CusDTO> list = client.getPersonalListTwo(cusDto);
			// 获取总数
			int count = client.getPersonalListTwoCount(cusDto);
			// 赋值Map
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询个人信息列表-贷前:" + e.getMessage());
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
	 * @Description: 查询企业股东信息列表
	 * @param cusComId
	 *            企业ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年1月15日 上午11:19:02
	 */
	@RequestMapping(value = "getShareList", method = RequestMethod.POST)
	public void getShareList(int cusComId, HttpServletResponse response) {
		List<CusComShare> list = new ArrayList<CusComShare>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			list = client.getShareList(cusComId);
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

	/**
	 * 
	 * @Description: 查询个人非配偶关系人
	 * @param acctId
	 *            客户ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年1月15日 上午11:21:55
	 * */
	@RequestMapping(value = "getNoSpouseList", method = RequestMethod.POST)
	public void getNoSpouseList(int acctId, HttpServletResponse response) {
		List<CusDTO> list = new ArrayList<CusDTO>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			list = client.getNoSpouseList(acctId);
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

	/**
	 * 
	 * @Description: 查询个人非配偶关系人
	 * @param acctId
	 *            客户ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年1月15日 上午11:21:55
	 * */
	@RequestMapping(value = "getNoSpouseLists", method = RequestMethod.POST)
	public void getNoSpouseLists(int projectId, HttpServletResponse response) {
		List<CusDTO> list = new ArrayList<CusDTO>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			list = client.getNoSpouseLists(projectId);
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

	private int obtainReportId(CusComService.Client client, HttpServletRequest request) {

		int reportId = 0;
		try {

			int comId = Integer.parseInt(request.getParameter("comId"));
			String reportName = request.getParameter("reportName");
			int accountingYear = Integer.parseInt(request.getParameter("accountingYear"));
			int accountingMonth = Integer.parseInt(request.getParameter("accountingMonth"));

			// 根据企业ID，年，月获得主键(企业财务报表ID)
			CusComFinanceReport cusComFinanceReportForPID = client.selectCusComFinanceReportByComIdAndYearMonth(comId, accountingYear, accountingMonth);

			reportId = cusComFinanceReportForPID.getPid();
			if (reportId <= 0) {
				// 如果CUS_COM_FINANCE_REPORT没有记录，则插入
				String reportType = "M";

				String uploadDttm = DateUtil.format(new Date());
				String dateStr = uploadDttm;

				ShiroUser reportUserId = getShiroUser();// 系统使用者

				CusComFinanceReport cusComFinanceReportIN = new CusComFinanceReport();
				cusComFinanceReportIN.setComId(comId);
				cusComFinanceReportIN.setAccountingYear(accountingYear);
				cusComFinanceReportIN.setAccountingMonth(accountingMonth);
				cusComFinanceReportIN.setReportType(reportType);
				cusComFinanceReportIN.setReportDttm(dateStr);
				cusComFinanceReportIN.setReportUserId(reportUserId.getPid());
				cusComFinanceReportIN.setReportName(reportName);
				cusComFinanceReportIN.setStatus(1);
				client.saveCusComFinanceReport(cusComFinanceReportIN);

				// 插入完毕后再查询出来PID
				CusComFinanceReport cusComFinanceReportForPID2 = client.selectCusComFinanceReportByComIdAndYearMonth(comId, accountingYear, accountingMonth);
				reportId = cusComFinanceReportForPID2.getPid();
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}

		return reportId;
	}

	/**
	 * 
	 * @Description: 初始化现金流量表的序号与项目
	 * @return
	 * @author: wangheng
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "searcherCusComCaseFlowTable")
	@ResponseBody
	public void searcherCusComCaseFlowTable(CashFlowItem cashFlowItem, HttpServletRequest request, HttpServletResponse response, String isEditReport, String reportId) throws Exception {
		List<FinacialDTO> list = new ArrayList<FinacialDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		CusComService.Client client = (Client) clientFactory.getClient();
		try {

			if (StringUtils.isEmpty(reportId)) {
				list = client.getAllCusComCaseFlow(cashFlowItem);
				outputJson(list, response);
			} else {
				List<CashFlowReportEditDTO> cashFlowReportEditDTOList = client.initCashFlowReportByReportId(Integer.parseInt(reportId));
				// 如果有reportId对应的列，则显示所有数据。没有reportId数据，则只显示项目名
				if (!cashFlowReportEditDTOList.isEmpty()) {
					outputJson(cashFlowReportEditDTOList, response);
				} else {
					list = client.getAllCusComCaseFlow(cashFlowItem);
					outputJson(list, response);
				}
			}

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
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ，保存现金流量表数据
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCusComCaseFlow")
	public void saveCusComCaseFlow(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");

		String content = request.getParameter("content");
		content = URLDecoder.decode(content, "utf-8");

		String saveCusComCaseFlowResult = "";

		List<FinacialDTO1> finacialDTOList = JSON.parseObject(content, new TypeToken<List<FinacialDTO1>>() {
			private static final long serialVersionUID = 1L;
		}.getType());

		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();

			// 没有Pid则初始化 ，否则直接获取主键
			int reportId = obtainReportId(client, request);

			// 如果现金流量表(CUS_COM_CASH_FLOW)里面已经存在该年月份的现金流量报表，则不允许再插入
			List<CashFlowReport> cashFlowReportList = client.selectCashFlowReportByReportId(reportId);
			if (!cashFlowReportList.isEmpty()) {
				saveCusComCaseFlowResult = "hasExistRecord";
				response.getWriter().write(saveCusComCaseFlowResult);
				return;
			}

			for (FinacialDTO1 finacialDTOs : finacialDTOList) {
				double thisMonthVal = finacialDTOs.getThisMonthVal();
				double thisYearVal = finacialDTOs.getThisYearVal();
				int cashFlowItemId = finacialDTOs.getPid();// 现金流量项目表的ID
				CashFlowReport cashFlowReport = new CashFlowReport();
				cashFlowReport.setReportId(reportId);
				cashFlowReport.setThisMonthVal(thisMonthVal);
				cashFlowReport.setThisYearVal(thisYearVal);
				cashFlowReport.setCashFlowItemId(cashFlowItemId);// 将现金流量项目表的ID设置到现金流量表
				// cashFlowReport.set
				cashFlowReport.setStatus(1);
				client.addCusComCashFlowReport(cashFlowReport);
			}

			saveCusComCaseFlowResult = "saveSucc";
		} catch (Exception e) {
			saveCusComCaseFlowResult = "saveFail";
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
		response.getWriter().write(saveCusComCaseFlowResult);
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ，编辑保存现金流量表数据
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCusComCaseFlow")
	public void updateCusComCaseFlow(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String saveCusComIncomeReportResult = "";

		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");

		String content = request.getParameter("content");
		content = URLDecoder.decode(content, "utf-8");

		try {

			List<CashFlowReportEditDTO> cashFlowReportEditDTOList = JSON.parseObject(content, new TypeToken<List<CashFlowReportEditDTO>>() {
				private static final long serialVersionUID = 1L;
			}.getType());

			CusComService.Client client = (CusComService.Client) clientFactory.getClient();

			for (CashFlowReportEditDTO cashFlowReportEditDTO : cashFlowReportEditDTOList) {
				CashFlowReport cashFlowReport = new CashFlowReport();
				cashFlowReport.setPid(cashFlowReportEditDTO.getCfPid());
				cashFlowReport.setThisYearVal(cashFlowReportEditDTO.getThisYearVal());
				cashFlowReport.setThisMonthVal(cashFlowReportEditDTO.getThisMonthVal());

				client.updateCusComCashFlowReport(cashFlowReport);
			}

			saveCusComIncomeReportResult = "editSucc";
		} catch (Exception e) {
			saveCusComIncomeReportResult = "editFail";
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

		response.getWriter().write(saveCusComIncomeReportResult);

	}

	/**
	 * 
	 * @Description: 初始化资产负债表的序号与项目
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/initCusComBalanceSheet")
	@ResponseBody
	public void initCusComBalanceSheet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		CusComService.Client client = (Client) clientFactory.getClient();
		try {
			List<CusComBalanceSheetMeta> CusComBalanceSheetMetaList = client.selectAllCusComBalanceSheetMeta();
			outputJson(CusComBalanceSheetMetaList, response);
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

	}

	/**
	 * 
	 * @Description: 初始化资产负债表的左面的序号与项目
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/initLeftCusComBalanceSheet")
	@ResponseBody
	public void initLeftCusComBalanceSheet(HttpServletRequest request, HttpServletResponse response, String isEditReport, String reportId) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		CusComService.Client client = (Client) clientFactory.getClient();
		try {
			// 如果是新增
			if (StringUtils.isEmpty(reportId)) {
				List<CusComBalanceSheetMeta> CusComBalanceSheetMetaList = client.selectLeftCusComBalanceSheetMeta();
				outputJson(CusComBalanceSheetMetaList, response);
			} else {// 编辑
				List<BalanceSheetEditDTO> leftList = client.initLeftEditCusComBalanceSheetByReportId(Integer.parseInt(reportId));

				// 如果有reportId对应的列，则显示所有数据。没有reportId数据，则只显示项目名
				if (!leftList.isEmpty()) {
					outputJson(leftList, response);
				} else {
					List<CusComBalanceSheetMeta> CusComBalanceSheetMetaList = client.selectLeftCusComBalanceSheetMeta();
					outputJson(CusComBalanceSheetMetaList, response);
				}
			}

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
	}

	/**
	 * 
	 * @Description: 初始化资产负债表的左面的序号与项目
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/initRightCusComBalanceSheet")
	@ResponseBody
	public void initRightCusComBalanceSheet(HttpServletRequest request, HttpServletResponse response, String isEditReport, String reportId) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		CusComService.Client client = (Client) clientFactory.getClient();
		try {
			if (StringUtils.isEmpty(reportId)) {
				List<CusComBalanceSheetMeta> CusComBalanceSheetMetaList = client.selectRightCusComBalanceSheetMeta();
				outputJson(CusComBalanceSheetMetaList, response);
			} else {
				List<BalanceSheetEditDTO> rightList = client.initRightEditCusComBalanceSheetByReportId(Integer.parseInt(reportId));
				// 如果有reportId对应的列，则显示所有数据。没有reportId数据，则只显示项目名
				if (!rightList.isEmpty()) {
					outputJson(rightList, response);
				} else {
					List<CusComBalanceSheetMeta> CusComBalanceSheetMetaList = client.selectRightCusComBalanceSheetMeta();
					outputJson(CusComBalanceSheetMetaList, response);
				}
			}

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
	}

	/**
	 * 
	 * @Description: 企业财务报表-资产负债表录入 ，保存数据
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCusComBalanceSheet")
	public void saveCusComBalanceSheet(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String saveCusComBalanceSheetResult = "";

		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");

		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();

			// 没有Pid则初始化 ，否则直接获取主键（企业财务报表CUS_COM_FINANCE_REPORT）
			int reportId = obtainReportId(client, request);

			// 如果企业财务报表-资产负债表里面已经存在该年月份的报表，则不允许再插入
			List<CusComBalanceSheet> cusComBalanceSheetList = client.selectCusComBalanceSheetByReportId(reportId);
			if (!cusComBalanceSheetList.isEmpty()) {
				saveCusComBalanceSheetResult = "hasExistRecord";
				// outputJson(saveCusComIncomeReportResult, response);
				response.getWriter().write(saveCusComBalanceSheetResult);
				return;
			}

			String leftCusComBalanceSheetContent = request.getParameter("leftCusComBalanceSheetContent");
			leftCusComBalanceSheetContent = URLDecoder.decode(leftCusComBalanceSheetContent, "utf-8");

			String rightCusComBalanceSheetContent = request.getParameter("rightCusComBalanceSheetContent");
			rightCusComBalanceSheetContent = URLDecoder.decode(rightCusComBalanceSheetContent, "utf-8");

			List<BalanceSheetDTO> leftBalanceSheetDTOList = JSON.parseObject(leftCusComBalanceSheetContent, new TypeToken<List<BalanceSheetDTO>>() {
				private static final long serialVersionUID = 1L;
			}.getType());
			List<BalanceSheetDTO> rightBalanceSheetDTOList = JSON.parseObject(rightCusComBalanceSheetContent, new TypeToken<List<BalanceSheetDTO>>() {
				private static final long serialVersionUID = 1L;
			}.getType());

			// 保存界面左边的数据
			for (BalanceSheetDTO balanceSheetDTO : leftBalanceSheetDTOList) {

				CusComBalanceSheet cusComBalanceSheet = new CusComBalanceSheet();
				cusComBalanceSheet.setReportId(reportId);
				cusComBalanceSheet.setAccountsId(balanceSheetDTO.getPid());// 会计科目ID,界面隐藏的pid
				cusComBalanceSheet.setBeginVal(balanceSheetDTO.getBeginVal());
				cusComBalanceSheet.setEndVal(balanceSheetDTO.getEndVal());
				cusComBalanceSheet.setStatus(1);

				client.saveCusComBalanceSheet(cusComBalanceSheet);
			}

			// 保存界面右边的数据
			for (BalanceSheetDTO balanceSheetDTO : rightBalanceSheetDTOList) {

				CusComBalanceSheet cusComBalanceSheet = new CusComBalanceSheet();
				cusComBalanceSheet.setReportId(reportId);
				cusComBalanceSheet.setAccountsId(balanceSheetDTO.getPid());// 会计科目ID,界面隐藏的pid
				cusComBalanceSheet.setBeginVal(balanceSheetDTO.getBeginVal());
				cusComBalanceSheet.setEndVal(balanceSheetDTO.getEndVal());
				cusComBalanceSheet.setStatus(1);

				client.saveCusComBalanceSheet(cusComBalanceSheet);
			}
			model.put("reportId", reportId);
			saveCusComBalanceSheetResult = "saveSucc";
		} catch (Exception e) {
			saveCusComBalanceSheetResult = "saveFail";
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

		response.getWriter().write(saveCusComBalanceSheetResult);
	}

	/**
	 * 
	 * @Description: 企业财务报表-资产负债表编辑 ，保存数据
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCusComBalanceSheet")
	public void updateCusComBalanceSheet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String saveCusComBalanceSheetResult = "";
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		try {

			CusComService.Client client = (CusComService.Client) clientFactory.getClient();

			String leftCusComBalanceSheetContent = request.getParameter("leftCusComBalanceSheetContent");
			leftCusComBalanceSheetContent = URLDecoder.decode(leftCusComBalanceSheetContent, "utf-8");

			String rightCusComBalanceSheetContent = request.getParameter("rightCusComBalanceSheetContent");
			rightCusComBalanceSheetContent = URLDecoder.decode(rightCusComBalanceSheetContent, "utf-8");

			List<BalanceSheetEditDTO> leftBalanceSheetEditDTOList = JSON.parseObject(leftCusComBalanceSheetContent, new TypeToken<List<BalanceSheetEditDTO>>() {
				private static final long serialVersionUID = 1L;
			}.getType());
			List<BalanceSheetEditDTO> rightBalanceSheetEditDTOList = JSON.parseObject(rightCusComBalanceSheetContent, new TypeToken<List<BalanceSheetEditDTO>>() {
				private static final long serialVersionUID = 1L;
			}.getType());

			// 更新左边的数据
			for (BalanceSheetEditDTO balanceSheetEditDTO : leftBalanceSheetEditDTOList) {
				CusComBalanceSheet cusComBalanceSheet = new CusComBalanceSheet();
				cusComBalanceSheet.setPid(balanceSheetEditDTO.getBsPid());
				cusComBalanceSheet.setBeginVal(balanceSheetEditDTO.getBeginVal());
				cusComBalanceSheet.setEndVal(balanceSheetEditDTO.getEndVal());

				client.updateCusComBalanceSheet(cusComBalanceSheet);
			}

			// 更新左边的数据
			for (BalanceSheetEditDTO balanceSheetEditDTO : rightBalanceSheetEditDTOList) {
				CusComBalanceSheet cusComBalanceSheet = new CusComBalanceSheet();
				cusComBalanceSheet.setPid(balanceSheetEditDTO.getBsPid());
				cusComBalanceSheet.setBeginVal(balanceSheetEditDTO.getBeginVal());
				cusComBalanceSheet.setEndVal(balanceSheetEditDTO.getEndVal());

				client.updateCusComBalanceSheet(cusComBalanceSheet);
			}

			saveCusComBalanceSheetResult = "editSucc";
		} catch (Exception e) {
			saveCusComBalanceSheetResult = "editFail";
			e.printStackTrace();
			// logger.info(e.toString());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		response.getWriter().write(saveCusComBalanceSheetResult);
	}

	/**
	 * 
	 * @Description: 企业客户财务状况查询/录入 ，保存现金流量表补充材料数据
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCusComCaseFlowMeta")
	public void saveCusComCaseFlowMeta(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");

		String content = request.getParameter("content");
		content = URLDecoder.decode(content, "utf-8");

		String saveCusComCaseFlowMetaResult = "";

		List<FinacialDTO1> finacialDTOList = JSON.parseObject(content, new TypeToken<List<FinacialDTO1>>() {
			private static final long serialVersionUID = 1L;
		}.getType());

		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();

			// 没有Pid则初始化 ，否则直接获取主键
			int reportId = obtainReportId(client, request);

			// 如果现金流量表(CUS_COM_CASH_FLOW)里面已经存在该年月份的现金流量报表，则不允许再插入
			List<CashFlowReport> cashFlowReportList = client.selectCashFlowReportSupplementByReportId(reportId);
			if (!cashFlowReportList.isEmpty()) {
				saveCusComCaseFlowMetaResult = "hasExistRecord";
				response.getWriter().write(saveCusComCaseFlowMetaResult);
				return;
			}

			for (FinacialDTO1 finacialDTOs : finacialDTOList) {
				double thisMonthVal = finacialDTOs.getThisMonthVal();
				double thisYearVal = finacialDTOs.getThisYearVal();
				int cashFlowItemId = finacialDTOs.getPid();// 现金流量项目表的ID
				CashFlowReport cashFlowReport = new CashFlowReport();
				cashFlowReport.setReportId(reportId);
				cashFlowReport.setThisMonthVal(thisMonthVal);
				cashFlowReport.setThisYearVal(thisYearVal);
				cashFlowReport.setCashFlowItemId(cashFlowItemId);// 将现金流量项目表的ID设置到现金流量表
				cashFlowReport.setIsMain(1);
				cashFlowReport.setStatus(1);
				client.addCusComCashFlowMetaReport(cashFlowReport);
			}

			saveCusComCaseFlowMetaResult = "saveSucc";
		} catch (Exception e) {
			saveCusComCaseFlowMetaResult = "saveFail";
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
		response.getWriter().write(saveCusComCaseFlowMetaResult);
	}

	/**
	 * 因为现金流量表与现金流量补充材料表更新都是一样的,该方法没有使用
	 * 
	 * @Description: 企业客户财务状况查询/录入 ，编辑保存现金流量补充材料表数据
	 * @return 企业客户财务状况查询/录入页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCusComCaseFlowMeta")
	public void updateCusComCaseFlowMeta(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String saveCusComIncomeReportResult = "";

		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");

		String content = request.getParameter("content");
		content = URLDecoder.decode(content, "utf-8");

		try {

			List<CashFlowReportEditDTO> cashFlowReportEditDTOList = JSON.parseObject(content, new TypeToken<List<CashFlowReportEditDTO>>() {
				private static final long serialVersionUID = 1L;
			}.getType());

			CusComService.Client client = (CusComService.Client) clientFactory.getClient();

			for (CashFlowReportEditDTO cashFlowReportEditDTO : cashFlowReportEditDTOList) {
				CashFlowReport cashFlowReport = new CashFlowReport();
				cashFlowReport.setPid(cashFlowReportEditDTO.getCfPid());
				cashFlowReport.setThisYearVal(cashFlowReportEditDTO.getThisYearVal());
				cashFlowReport.setThisMonthVal(cashFlowReportEditDTO.getThisMonthVal());

				client.updateCusComCashFlowReport(cashFlowReport);
			}

			saveCusComIncomeReportResult = "editSucc";
		} catch (Exception e) {
			saveCusComIncomeReportResult = "editFail";
			e.printStackTrace();
			// logger.info(e.toString());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		response.getWriter().write(saveCusComIncomeReportResult);
	}

	/**
	 * 
	 * @Description: 初始化现金流量项目表的序号与项目
	 * @return
	 * @author: wangheng
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "searcherCusComCaseFlowMetaTable")
	@ResponseBody
	public void searcherCusComCaseFlowMetaTable(CashFlowItem cashFlowItem, HttpServletRequest request, HttpServletResponse response, String isEditReport, String reportId) throws Exception {
		List<FinacialDTO> list = new ArrayList<FinacialDTO>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		CusComService.Client client = (Client) clientFactory.getClient();
		try {

			if (StringUtils.isEmpty(reportId)) {
				list = client.getAllCusComCaseFlowMeta(cashFlowItem);
				outputJson(list, response);
			} else {
				List<CashFlowReportEditDTO> cashFlowReportEditDTOList = client.initCashFlowReportSupplementByReportId(Integer.parseInt(reportId));
				// 如果有reportId对应的列，则显示所有数据。没有reportId数据，则只显示项目名
				if (!cashFlowReportEditDTOList.isEmpty()) {
					outputJson(cashFlowReportEditDTOList, response);
				} else {
					list = client.getAllCusComCaseFlowMeta(cashFlowItem);
					outputJson(list, response);
				}
			}
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
	}

	/**
	 * 
	 * @Description: 校验证件类型，证件号码是否存在
	 * @return 企业客户新增页面
	 * @author: wangheng
	 * @date: 2015年3月05日
	 */
	@RequestMapping(value = "/validateCeryNumber")
	@ResponseBody
	public void validateCeryNumber(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "certNumber", required = false) String certNumber, @RequestParam(value = "personId", required = false) String personId) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		CusComService.Client client = (Client) clientFactory.getClient();
		Map<String, String> map = new HashMap<String, String>();
		int flags = 0;
		Json j = super.getSuccessObj();
		int blackFlags = 0;
		BaseClientFactory actFactory = getFactory(serviceModuel, "CusAcctService");
		try {
			int orgId = getSecondOrgId(getSysUser().getOrgId());
			certNumber = certNumber.trim();
			map.put("certNumber", certNumber);
			map.put("personId", personId);
			map.put("orgId", String.valueOf(orgId));
			map.put("cusSource", String.valueOf(Constants.CUS_SOURCE_0));
			
			flags = client.validateCeryNumber(map);
			if(flags >0){//表示已存在此号码
				//根据证件号码查询该客户是否为黑名单
				CusAcctService.Client actClient = (CusAcctService.Client) actFactory.getClient();
				blackFlags = actClient.getBlackByCertNum(certNumber);
				
				CusPerService.Client perClient = (CusPerService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
				CusPerson query = new CusPerson();
				query.setCertNumber(certNumber);
				query.setOrgId(orgId);
				query.setCusSource(Constants.CUS_SOURCE_0);
				List<CusPerson> result = perClient.getCusPersonByNumber(query);
				//查询cus_acct表pid
				int acctId = result.get(0).getCusAcct().getPid();
				//查询个人客户主表CUS_PER_BASE的pid
				int perId = perClient.selectPerIdByAcctId(acctId);
				//查询个人客户信息
				CusPerBaseDTO cusPerBaseDTO = perClient.getCusPerBase(perId);
				int pmUserId = cusPerBaseDTO.getCusAcct().getPmUserId();
				j.getHeader().put("cusPerBaseDTO", cusPerBaseDTO);
				j.getHeader().put("pmUserId", pmUserId);
			}
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
		
		j.getHeader().put("flags", flags);
		j.getHeader().put("blackFlags", blackFlags);
		outputJson(j, response);
	}

	
	/**
	 * 
	 * @Description: 批量选择旗下子公司
	 * @return 批量选择旗下子公司页面
	 * @author: wangheng
	 * @date: 2015年03月10日
	 */
	@RequestMapping(value = "inUnderAddCusCom")
	@ResponseBody
	public String inUnderAddCusCom(String pid, int acctId, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		// List<String> lists = new ArrayList<String>();
		try {
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			// 根据acctId查找个人客户Id
			int percusId = client.selectPersonIdByAcctId(acctId);
			List<CusPerCom> list = Lists.newArrayList();
			String[] s = pid.split(",");
			CusPerCom cp;
			for (String comId : s) {
				cp = new CusPerCom();
				cp.setCusPerId(percusId);
				cp.setComId(Integer.parseInt(comId));
				cp.setStatus(1);
				list.add(cp);
			}
			client.addUnderCom(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cusper + "list_under_com";
	}

	/**
	 * 
	 * @Description: 根据项目ID查询额度表是否有该项目
	 * @return 如果有该项目，返回额度贷款申请，否则返回到贷款申请页面。
	 * @author: wangheng
	 * @date: 2015年03月16日
	 */
	@RequestMapping(value = "selectCredit")
	@ResponseBody
	public void selectCredit(int projectId, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
		CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
		int flag = 0;
		try {
			flag = client.selectCredit(projectId);
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
		outputJson(flag, response);
	}

	// 跳转到潜在客户页面
	@RequestMapping(value = "searchlistComPotential")
	public String searchlistComPotential(HttpServletResponse response, @RequestParam(value = "pid", required = false) Integer pid, ModelMap modelMap) {
		modelMap.put("pid", pid);
		return cuscom + "list_com_potential";
	}

	// 查询潜在客户列表
	@RequestMapping(value = "/listComPotential")
	@ResponseBody
	public void listComPotential(CusAcctPotential cusAcctPotential, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, HttpServletResponse response) {
		List<GridViewDTO> cusPers = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			cusAcctPotential.setPage(page);
			cusAcctPotential.setRows(rows);
			cusPers = client.getCusAcctPotentials(cusAcctPotential);
			int total = client.getTotalPotential(cusAcctPotential);
			map.put("total", total);
			map.put("rows", cusPers);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		// 输出
		outputJson(map, response);
	}

	// 跳转到潜在客户编辑页面
	@RequestMapping(value = "editPotential")
	public String editPotential(HttpServletResponse response, @RequestParam(value = "pid", required = false) Integer pid, ModelMap modelMap) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusAcctService");
		CusAcctService.Client client;
		try {
			if (pid != null) {
				client = (CusAcctService.Client) clientFactory.getClient();
				CusAcctPotential cusAcctPotential = client.getCusAcctPotential(pid);
				modelMap.put("cusAcctPotential", cusAcctPotential);
			}
		} catch (ThriftException e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "edit_com_potential";
	}

	// 保存潜在客户资料
	@RequestMapping(value = "/savePotential")
	public String savePotential(CusAcctPotential cusAcctPotential, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			cusAcctPotential.setStatus(1);
			if (cusAcctPotential.getPid() > 0) {
				client.updateCusAcctPotential(cusAcctPotential);
			} else {
				// 获取当前登录人id
				int userId = getShiroUser().getPid();
				cusAcctPotential.setCreateUserId(userId);
				client.addCusAcctPotential(cusAcctPotential);
			}
			outputJson("success", response);
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
		return cuscom + "list_com_potential";
	}

	/**
	 * 删除潜在客户信息
	 */
	@RequestMapping(value = "deleteCusComPotential")
	public String deleteCusComPotential(String pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			client.deleteCusAcctPotential(pid);
			outputJson("success", response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return cuscom + "list_com_potential";
	}

	/**
	 * 
	 * @Description: 校验营业执照号码
	 * @return 企业客户新增页面
	 * @author: wangheng
	 * @date: 2015年3月05日
	 */
	@RequestMapping(value = "/validateBusLicCert")
	@ResponseBody
	public void validateBusLicCert(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "busLicCert", required = false) String busLicCert, @RequestParam(value = "cusComId", required = false) String cusComId) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		CusComService.Client client = (Client) clientFactory.getClient();
		Map<String, String> map = new HashMap<String, String>();
		int flags = 0;
		try {
			map.put("busLicCert", busLicCert);
			map.put("cusComId", cusComId);
			flags = client.validateBusLicCert(map);

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
		Json j = super.getSuccessObj();
		j.getHeader().put("flags", flags);
		outputJson(j, response);
	}
	
	/**
	 * 
	 * @Description: 查看借贷客户中项目是否删除
	 * @return个人客户查询页面
	 * @author: wangheng
	 * @date: 2015年7月13日
	 */
	@RequestMapping(value = "/selectProjectStatus")
	@ResponseBody
	public void selectProjectStatus(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "acctID", required = false) int  acctID) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusPerService");
		CusPerService.Client client = (com.xlkfinance.bms.rpc.customer.CusPerService.Client) clientFactory.getClient();
		int flags = 0;
		try {
			flags = client.selectProjectStatus(acctID);

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
		Json j = super.getSuccessObj();
		j.getHeader().put("flags", flags);
		outputJson(j, response);
	}

	/**
	 * 
	 * @Description: 校验黑名单用户是否有权限查看详情权限
	 * @return 黑名单查看详情页面
	 * @author: wangheng
	 * @date: 2015年4月15日
	 */
	@RequestMapping(value = "/validateIsPermissions")
	@ResponseBody
	public void validateIsPermissions(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "spid", required = false) int spid, @RequestParam(value = "currUserId", required = false) int currUserId) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		CusComService.Client client = (Client) clientFactory.getClient();
		Map<String, Integer> map = new HashMap<String, Integer>();
		int flags = 0;
		try {
			map.put("currUserId", currUserId);
			map.put("spid", spid);
			flags = client.validateIsPermissions(map);

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
		Json j = super.getSuccessObj();
		j.getHeader().put("flags", flags);
		outputJson(j, response);
	}

	/**
	 * 
	 * @Description: 根据acctId查询当前客户的担保方式
	 * @param cusComBasePid
	 *            企业客户ID
	 * @param response
	 *            担保方式集合
	 * @author: wangheng
	 * @date: 2015年4月17日 下午
	 */
	@RequestMapping(value = "getGuaranteeTypeBycusComBasePid")
	public void getGuaranteeTypeBycusComBasePid(int cusComBasePid, HttpServletResponse response) {
		List<CusComGuaranteeType> list = new ArrayList<CusComGuaranteeType>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			list = client.getGuaranteeTypeBycusComBasePid(cusComBasePid);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据acctId查询当前项目的担保方式:" + e.getMessage());
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
	 * @Description: 查询所有有效的客户信息
	 * @param cusAcct
	 *            客户对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @param request 
	 * @date: 2015年4月20日 下午3:46:42
	 */
	@RequestMapping(value = "getAllAcct", method = RequestMethod.POST)
	public void getAllAcct(CusAcct cusAcct, HttpServletResponse response, HttpServletRequest request) {
		// 返回到前端的HashMap转换JSON
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			cusAcct.setUserIds(getUserIds(request));
			// 调用方法获取结果集
			List<CusAcct> list = client.getAllAcct(cusAcct);
			// 获取总数
			int count = client.getAllAcctCount(cusAcct);
			// 赋值Map
			map.put("rows", list);
			map.put("total", count);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询有效客户信息:" + e.getMessage());
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
	 * @Description: 查出黑名单，拒贷客户不能新增贷款的原因
	 * @return 个人/企业客户查询页面
	 * @author: wangheng
	 * @date: 2015年5月11日
	 */
	@RequestMapping(value = "/searcherBlackListRefuse")
	@ResponseBody
	public void searcherBlackListRefuse(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "pid", required = false) int pid) throws Exception {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		CusComService.Client client = (Client) clientFactory.getClient();
		String tip = "";
		try {
			// map.put("certNumber", certNumber);
			// map.put("personId", personId);
			tip = client.searcherBlackListRefuse(pid);

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
		Json j = super.getSuccessObj();
		j.getHeader().put("tip", tip);
		outputJson(j, response);
	}

	/**
	 * 
	 * @Description: 查询个人非配偶关系人
	 * @param acctId
	 *            客户ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年1月15日 上午11:21:55
	 * */
	@RequestMapping(value = "getNoSpouseListByPid", method = RequestMethod.POST)
	public void getNoSpouseListByPid(String userIds, HttpServletResponse response) {
		List<CusDTO> list = new ArrayList<CusDTO>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			list = client.getNoSpouseListByPid(userIds);
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
	
	/**
	 * 保存共同借款人信息
	  * @param cusAcct
	  * @param response
	  * @param request
	  * @author: baogang
	  * @date: 2016年5月10日 下午2:55:40
	 */
	@RequestMapping(value = "saveSpouseList", method = RequestMethod.POST)
	public void saveSpouseList(CusAcct cusAcct, HttpServletResponse response, HttpServletRequest request) {
		BaseClientFactory clientFactory = null;
		Json j = super.getSuccessObj();
		String result = "";
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			int acctId = cusAcct.getPid();
			if(cusAcct.getCustomerList() == null){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "保存成功！");
			}else{
				if(acctId >0){
					result = client.saveSpouseList(cusAcct);
					// 成功的话,就做业务日志记录
					recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "保存共同借款人信息");
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "保存成功！");
				}else{
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "选择失败,请重新操作!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "选择失败,请重新操作!");
		}finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		j.getHeader().put("result", result);
		outputJson(j, response);
	}
}
