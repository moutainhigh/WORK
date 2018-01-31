/**
 * @Title: MortgageController.java
 * @Package com.xlkfinance.bms.web.controller.mortgage
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:54:02
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.mortgage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBase;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBaseOperationService;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBaseService;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssDtl;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssExtraction;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssFile;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssKeeping;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssOwn;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

/**
 * 
 * @ClassName: MortgageController
 * @Description: 抵质押物Controllr
 * @author: Cai.Qing
 * @date: 2015年1月26日 下午3:02:56
 */
@Controller
@RequestMapping("/mortgageController")
public class MortgageController extends BaseController {
	// ProjectAssBaseServiceImpl
	private Logger logger = LoggerFactory.getLogger(MortgageController.class);

	/**
	 * 
	 * @Description: 页面跳转方法
	 * @return 抵质押物列表
	 * @author: Cai.Qing
	 * @date: 2015年1月6日 下午3:21:23
	 */
	@RequestMapping(value = "navigation")
	public String navigation() {
		return "mortgage/index";
	}

	/**
	 * 
	 * @Description: 跳转到保管页面
	 * @return 抵质押物保管列表
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午5:42:29
	 */
	@RequestMapping(value = "navigationKeeping")
	public String navigationKeeping() {
		return "mortgage/extraction_list";
	}

	/**
	 * 
	 * @Description: 跳转到提取页面
	 * @return 抵质押物提取列表
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午5:42:29
	 */
	@RequestMapping(value = "navigationExtraction")
	public String navigationExtraction() {
		return "";
	}

	/**
	 * 
	 * @Description: 跳转到处理页面
	 * @return 抵质押物处理列表
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午5:42:29
	 */
	@RequestMapping(value = "navigationHandle")
	public String navigationHandle() {
		return "";
	}

	/**
	 * 
	 * @Description: 条件查询抵质押物
	 * @param projectAssBase
	 *            抵质押物对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年1月26日 下午3:06:12
	 */
	@RequestMapping(value = "getAllProjectAssBase", method = RequestMethod.POST)
	public void getAllProjectAssBase(ProjectAssBase projectAssBase, HttpServletResponse response) {
		// 抵质押物列表集合
		List<ProjectAssBase> list = new ArrayList<ProjectAssBase>();
		// 抵质押物总数
		int count = 0;
		// 需要返回的map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			list = client.getAllProjectAssBase(projectAssBase);
			count = client.getAllProjectAssBaseCount(projectAssBase);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error("查询抵质押物列表:" + e.getMessage());
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
	 * @Description: 根据当前抵质押物ID获取抵质押物详情信息
	 * @param baseId
	 *            抵质押物ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月24日 上午11:20:15
	 */
	@RequestMapping(value = "getProjectAssDtlByBaseId", method = RequestMethod.POST)
	public void getProjectAssDtlByBaseId(Integer baseId, HttpServletResponse response) {
		// 抵质押物详情集合
		List<ProjectAssDtl> list = new ArrayList<ProjectAssDtl>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			// 获取数据
			list = client.getProjectAssDtlByBaseId(baseId);
		} catch (Exception e) {
			logger.error("根据当前抵质押物ID获取抵质押物详情信息:" + e.getMessage());
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
	 * @Description: 新增抵质押物信息
	 * @param projectAssBase
	 *            担保基本信息对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月21日 下午3:38:57
	 */
	@RequestMapping(value = "addProjectAssBase", method = RequestMethod.POST)
	public void addProjectAssBase(ProjectAssBase projectAssBase, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			int rows = client.addProjectAssBase(projectAssBase);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_ADD, "新增抵质押物信息");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "新增抵质押物信息成功!");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "新增失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("新增抵质押物信息:" + e.getMessage());
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
	 * @Description: 修改抵质押物信息
	 * @param projectAssBase
	 *            抵质押物对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月21日 下午5:42:03
	 */
	@RequestMapping(value = "updateProjectAssBase", method = RequestMethod.POST)
	public void updateProjectAssBase(ProjectAssBase projectAssBase, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			int rows = client.updateProjectAssBase(projectAssBase);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改抵质押物信息");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "修改抵质押物信息成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("修改抵质押物信息:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作!");
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
	 * @Description: 删除抵质押物信息
	 * @param pids
	 *            抵质押物信息主键集("1,2,3")
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月24日 下午4:02:34
	 */
	@RequestMapping(value = "deleteProjectAssBase", method = RequestMethod.POST)
	public void deleteProjectAssBase(String pids, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			// 调用批量删除的方法
			int rows = client.batchDelete(pids);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_DELETE, "删除抵质押物信息");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "删除抵质押物信息成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "删除失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("删除抵质押物信息:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作!");
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
	 * @Description: 查询抵质押物信息
	 * @param mortgageGuaranteeType
	 *            抵质押物类型
	 * @param projectId
	 *            新项目ID
	 * @param oldProject
	 *            旧项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月15日 上午3:40:53
	 */
	@RequestMapping(value = "getProjectAssBaseByMortgageGuaranteeType")
	public void getProjectAssBaseByMortgageGuaranteeType(Integer mortgageGuaranteeType, Integer projectId, String oldProject, HttpServletResponse response) {
		// 创建集合
		List<ProjectAssBase> list = new ArrayList<ProjectAssBase>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			// 拼接项目ID --
			// 1.如果没有传旧项目ID过来,就表示当前只需要查询当前项目的抵质押物;
			// 2.如果传了旧项目ID过来,就表示需要查询两个项目的抵质押物.需要拼接项目ID 如：(1,2)
			String projectIds = "";
			if (null == oldProject || "-1".equals(oldProject)) {
				// 等于 -1 表示没有旧项目ID,不需要拼接
				projectIds = projectId + "";
			} else {
				// 反之,表示有旧项目ID,需要拼接
				projectIds = projectId + "," + oldProject;
			}
			list = client.getProjectAssBaseByMortgageGuaranteeType(mortgageGuaranteeType, projectIds);
		} catch (Exception e) {
			logger.error("查询抵质押物列表:" + e.getMessage());
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
	 * @Description: 办理抵质押物登记
	 * @param projectAssBase
	 *            抵质押物对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午6:31:49
	 */
	@RequestMapping(value = "transactProjectAssBase", method = RequestMethod.POST)
	public void transactProjectAssBase(ProjectAssBase projectAssBase, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			int rows = client.transactProjectAssBase(projectAssBase);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_UPDATE, "办理抵质押物登记");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "办理抵质押物登记成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "办理抵质押物登记失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("办理抵质押物登记:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "办理抵质押物登记失败,请重新操作!");
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
	 * @Description: 抵质押物保管处理
	 * @param projectAssBase
	 *            抵质押物对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月10日 下午2:54:14
	 */
	@RequestMapping(value = "safekeepingProjectAssBase", method = RequestMethod.POST)
	public void safekeepingProjectAssBase(ProjectAssBase projectAssBase, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			// 设置申请人为当前登陆的用户
			projectAssBase.setProposer(getShiroUser().getPid());
			int rows = client.safekeepingProjectAssBase(projectAssBase);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_UPDATE, "抵质押物保管处理");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "抵质押物保管处理成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "抵质押物保管处理失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("抵质押物保管处理:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "抵质押物保管处理失败,请重新操作!");
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
	 * @Description: 查询当前抵质押物的最新的保管信息
	 * @param baseId
	 *            抵质押物ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午5:02:34
	 */
	@RequestMapping(value = "getProjectAssKeepingByBaseId", method = RequestMethod.POST)
	public void getProjectAssKeepingByBaseId(Integer baseId, HttpServletResponse response) {
		ProjectAssKeeping p = new ProjectAssKeeping();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseOperationService");
			ProjectAssBaseOperationService.Client client = (ProjectAssBaseOperationService.Client) clientFactory.getClient();
			// 获取最新的保管信息
			p = client.getProjectAssKeepingByBaseId(baseId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			logger.error("查询当前抵质押物的最新的保管信息:" + e.getMessage());
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
		outputJson(p, response);
	}

	/**
	 * 
	 * @Description: 查询当前抵质押物的所有保管信息
	 * @param baseId
	 *            抵质押物ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午10:56:01
	 */
	@RequestMapping(value = "getListProjectAssKeepingByBaseId", method = RequestMethod.POST)
	public void getListProjectAssKeepingByBaseId(Integer baseId, HttpServletResponse response) {
		List<ProjectAssKeeping> list = new ArrayList<ProjectAssKeeping>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseOperationService");
			ProjectAssBaseOperationService.Client client = (ProjectAssBaseOperationService.Client) clientFactory.getClient();
			// 获取当前抵质押物所有的保管信息
			list = client.getListProjectAssKeepingByBaseId(baseId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			logger.error("查询当前抵质押物的所有保管信息:" + e.getMessage());
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
	 * @Description: 查询当前抵质押物的所有提取信息
	 * @param baseId
	 *            抵质押物ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午10:57:58
	 */
	@RequestMapping(value = "getListProjectAssExtractionByBaseId", method = RequestMethod.POST)
	public void getListProjectAssExtractionByBaseId(Integer baseId, HttpServletResponse response) {
		List<ProjectAssExtraction> list = new ArrayList<ProjectAssExtraction>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseOperationService");
			ProjectAssBaseOperationService.Client client = (ProjectAssBaseOperationService.Client) clientFactory.getClient();
			// 获取所有提取信息
			list = client.getListProjectAssExtractionByBaseId(baseId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			logger.error("查询当前抵质押物的所有提取信息:" + e.getMessage());
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
	 * @Description: 根据抵质押物ID查询抵质押物详细信息
	 * @param baseId
	 *            抵质押物ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午5:04:50
	 */
	@RequestMapping(value = "getProjectAssBaseByPid", method = RequestMethod.POST)
	public void getProjectAssBaseByPid(Integer baseId, HttpServletResponse response) {
		logger.info("----------begin根据抵质押物ID查询抵质押物详细信息");
		ProjectAssBase p = new ProjectAssBase();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			// 获取抵质押物信息
			p = client.getProjectAssBaseByPid(baseId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			logger.error("根据抵质押物ID查询抵质押物详细信息:" + e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("----------end根据抵质押物ID查询抵质押物详细信息");
		// 输出
		outputJson(p, response);
	}

	/**
	 * 
	 * @Description: 抵质押物提取申请
	 * @param projectAssBase
	 *            抵质押物对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月10日 下午2:55:51
	 */
	@RequestMapping(value = "applyExtractionProjectAssBase", method = RequestMethod.POST)
	public void applyExtractionProjectAssBase(ProjectAssBase projectAssBase, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			// 设置申请人为当前登陆的用户
			projectAssBase.setProposer(getShiroUser().getPid());
			int rows = client.applyExtractionProjectAssBase(projectAssBase);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_UPDATE, "抵质押物提取申请");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "抵质押物提取申请成功！");
				j.getHeader().put("code", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "抵质押物提取申请失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("抵质押物提取申请:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "抵质押物提取申请失败,请重新操作!");
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
	 * @Description: 抵质押物提取处理
	 * @param projectAssBase
	 *            抵质押物对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月10日 下午2:56:25
	 */
	@RequestMapping(value = "applyManagetransactProjectAssBase", method = RequestMethod.POST)
	public void applyManagetransactProjectAssBase(ProjectAssExtraction projectAssExtraction, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			// 设置申请人为当前登陆的用户
			projectAssExtraction.setHandleUserId(getShiroUser().getPid());
			int rows = client.applyManagetransactProjectAssBase(projectAssExtraction);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_UPDATE, "抵质押物提取处理");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "抵质押物提取处理成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "抵质押物提取处理失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("抵质押物提取处理:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "抵质押物提取处理失败,请重新操作!");
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
	 * @Description: 解除抵质押物
	 * @param projectAssBase
	 *            抵质押物对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月10日 下午2:57:45
	 */
	@RequestMapping(value = "relieveProjectAssBase", method = RequestMethod.POST)
	public void relieveProjectAssBase(ProjectAssBase projectAssBase, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			// 设置申请人为当前登陆的用户
			projectAssBase.setProposer(getShiroUser().getPid());
			int rows = client.relieveProjectAssBase(projectAssBase);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_UPDATE, "解除抵质押物");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "解除抵质押物成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "解除抵质押物失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("解除抵质押物:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "解除抵质押物失败,请重新操作!");
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
	 * @Description: 查询抵质押物共有人-根据抵质押物ID
	 * @param baseId
	 *            抵质押物ID
	 * @param ownType
	 *            所有人类型
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午7:37:07
	 */
	@RequestMapping(value = "getProjectAssOwnByBaseId", method = RequestMethod.POST)
	public void getProjectAssOwnByBaseId(Integer baseId, Integer ownType, HttpServletResponse response) {
		// 创建集合
		List<ProjectAssOwn> list = new ArrayList<ProjectAssOwn>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			list = client.getProjectAssOwnByBaseId(baseId, ownType);
		} catch (Exception e) {
			logger.error("查询抵质押物共有人-根据抵质押物ID:" + e.getMessage());
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
	 * @Description: 查询抵质押物共有人-根据类型人ID和所有人类型查询
	 * @param relationIds
	 *            人员ID字符串(1,2,3)
	 * @param ownType
	 *            所有人类型
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月2日 下午11:59:58
	 */
	@RequestMapping(value = "getProjectAssOwnByRelationId", method = RequestMethod.POST)
	public void getProjectAssOwnByRelationId(String relationIds, Integer ownType, HttpServletResponse response) {
		// 创建集合
		List<ProjectAssOwn> list = new ArrayList<ProjectAssOwn>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			list = client.getProjectAssOwnByRelationId(relationIds, ownType);
		} catch (Exception e) {
			logger.error("查询抵质押物共有人-根据类型人ID和所有人类型查询:" + e.getMessage());
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
	 * @Description: 查询所有-抵质押物共有人列表
	 * @param projectAssOwn
	 *            条件对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月3日 上午12:43:50
	 */
	@RequestMapping(value = "getAllProjectAssOwnByOwnType", method = RequestMethod.POST)
	public void getAllProjectAssOwnByOwnType(ProjectAssOwn projectAssOwn, HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			// 获取集合
			List<ProjectAssOwn> list = client.getAllProjectAssOwnByOwnType(projectAssOwn);
			// 获取数量
			int count = client.getAllProjectAssOwnByOwnTypeCount(projectAssOwn);
			// Map赋值
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error("查询抵质押物共有人-根据类型人ID和所有人类型查询:" + e.getMessage());
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
	 * @Description: 新增抵质押物共有人
	 * @param projectAssOwn
	 *            抵质押物共有人对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午9:27:13
	 */
	@RequestMapping(value = "addProjectAssOwn", method = RequestMethod.POST)
	public void addProjectAssOwn(ProjectAssOwn projectAssOwn, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			int rows = client.addProjectAssOwn(projectAssOwn);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_ADD, "新增抵质押物共有人");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "新增抵质押物共有人成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "新增抵质押物共有人失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("新增抵质押物共有人:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "新增抵质押物共有人失败,请重新操作!");
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
	 * @Description: 批量删除抵质押物共有人-根据主键
	 * @param pids
	 *            主键(1,2,3)
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午10:49:10
	 */
	@RequestMapping(value = "batchDeleteProjectAssOwn", method = RequestMethod.POST)
	public void batchDeleteProjectAssOwn(String pids, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			int rows = client.batchDeleteProjectAssOwn(pids);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_DELETE, "批量删除抵质押物共有人");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "批量删除抵质押物共有人成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "批量删除抵质押物共有人失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("批量删除抵质押物共有人:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "批量删除抵质押物共有人失败,请重新操作!");
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
	 * @Description: 删除抵质押物-根据抵质押物ID
	 * @param baseId
	 *            抵质押物ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月3日 上午2:25:15
	 */
	@RequestMapping(value = "deleteProjectAssOwn", method = RequestMethod.POST)
	public void deleteProjectAssOwn(Integer baseId, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			int rows = client.deleteProjectAssOwn(baseId);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_DELETE, "删除抵质押物共有人");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "删除抵质押物共有人成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "删除抵质押物共有人失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("删除抵质押物共有人:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除抵质押物共有人失败,请重新操作!");
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
	 * @Description: 批量撤销抵质押物状态(把状态修改为 等待抵质押物办理 状态)
	 * @param pids
	 *            抵质押物ID(1,2,3)
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年7月3日 上午11:32:45
	 */
	@RequestMapping(value = "revokeProjectAssBase", method = RequestMethod.POST)
	public void revokeProjectAssBase(String pids, HttpServletResponse response) {
		logger.info("-------进入:批量撤销抵质押物状态(把状态修改为 等待抵质押物办理 状态),抵质押物ID:" + pids);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			int rows = client.revokeProjectAssBase(pids);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE, SysLogTypeConstant.LOG_TYPE_UPDATE, "批量撤销抵质押物状态");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "批量撤销抵质押物状态成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "批量撤销抵质押物状态失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("批量撤销抵质押物状态:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "批量撤销抵质押物状态失败,请重新操作!");
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

	/***
	 * 
	 * @Description: 查询所有有效的抵质押物提取信息
	 * @param projectAssExtraction
	 *            提取信息对象
	 * @param response
	 *            HttpServletResponse对象
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:50:01
	 */
	@RequestMapping(value = "getAllProjectAssExtraction", method = RequestMethod.POST)
	public void getAllProjectAssExtraction(ProjectAssExtraction projectAssExtraction, HttpServletResponse response) {
		// 抵质押物提取信息列表集合
		List<ProjectAssExtraction> list = new ArrayList<ProjectAssExtraction>();
		// 抵质押物提取信息总数
		int count = 0;
		// 需要返回的map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseOperationService");
			ProjectAssBaseOperationService.Client client = (ProjectAssBaseOperationService.Client) clientFactory.getClient();
			list = client.getAllProjectAssExtraction(projectAssExtraction);
			count = client.getAllProjectAssExtractionCount(projectAssExtraction);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error("查询所有有效的抵质押物提取信息:" + e.getMessage());
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

	@RequestMapping(value = "getProjectAssFile")
	public void getProjectAssFile(int baseId, int fileTyle, HttpServletResponse response) {
		BaseClientFactory c = null;
		List<ProjectAssFile> list = new ArrayList<ProjectAssFile>();
		Map<String, Object> map = new HashMap<String, Object>();
		String fileT = null;
		try {
			if (fileTyle == 1) {
				fileT = "抵质押物办理类型";
			} else if (fileTyle == 2) {
				fileT = "提取抵押申请类型";
			} else if (fileTyle == 3) {
				fileT = "抵质押解除类型";
			} else if (fileTyle == 4) {
				fileT = "抵质押处理类型";
			}
			c = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) c.getClient();
			list = client.getProjectAssFile(baseId, fileT);
			map.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(map, response);
	}

	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public void uploadFile(HttpServletResponse response, HttpServletRequest request) {
		int count = 0;
		int fileSize = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		ProjectAssFile conAcc = new ProjectAssFile();
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) c.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					logger.error("上传出错");
					tojson.put("uploadStatus", "文件上传失败or文件格式不合法");
				} else {
					// 拿到当前用户
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					bizFile.setUploadUserId(userId);
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						// 获取其他信息
						if (item.isFormField()) {
							if (item.getFieldName().equals("fileProperty")) {
								conAcc.setFileType(ParseDocAndDocxUtil.getStringCode(item));
								conAcc.setFileProperty(ParseDocAndDocxUtil.getStringCode(item));
							} else if (item.getFieldName().equals("fileDesc")) {
								conAcc.setFileDesc(ParseDocAndDocxUtil.getStringCode(item));
							} else if (item.getFieldName().equals("baseId")) {
								conAcc.setBaseId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}
						} else {
							if ("file2".equals(item.getFieldName())) {
								// 获取文件上传的信息
								bizFile.setUploadUserId(userId);
								fileSize = (int) item.getSize();
								bizFile.setFileSize(fileSize);
								String fileFullName = item.getName().toLowerCase();
								int dotLocation = fileFullName.lastIndexOf(".");
								fileName = fileFullName.substring(0, dotLocation);
								fileType = fileFullName.substring(dotLocation).substring(1);
								bizFile.setFileType(fileType);
								bizFile.setFileName(fileName);
							}
						}
					}
					String uploadDttm = DateUtil.format(new Date());
					bizFile.setUploadDttm(uploadDttm);
					String fileUrl = String.valueOf(map.get("path"));
					bizFile.setFileUrl(fileUrl);
					int uploadUserId = getShiroUser().getPid();
					bizFile.setUploadUserId(uploadUserId);
					bizFile.setStatus(1);
					if (fileSize == 0) {
						response.getWriter().write("fileSizeIsZero");
					}
					if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(fileType)) {
						response.getWriter().write("fileIsEmpty");
					}
					// 保存文件信息
					int fileId = bizFileClient.saveBizFile(bizFile);
					// 获取文件Id
					conAcc.setFileId(fileId);
					// 保存资料表信息
					count = client.saveProjectAssFile(conAcc);
					if (count == 0) {
						tojson.put("uploadStatus", "上传抵质押办理资料失败");
					} else {
						tojson.put("uploadStatus", "上传抵质押办理资料成功");
					}
				}
			}
		} catch (Exception e) {
			logger.error("上传抵质押办理资料出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				} finally {
					if (bizFileClientFactory != null) {
						try {
							bizFileClientFactory.destroy();
						} catch (ThriftException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		outputJson(tojson, response);
	}

	@RequestMapping(value = "reloadFile", method = RequestMethod.POST)
	public void reloadFile(HttpServletResponse response, HttpServletRequest request) {
		int count = 0;
		int fileSize = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		ProjectAssFile conAcc = new ProjectAssFile();
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) c.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					logger.error("上传出错");
					tojson.put("uploadStatus", "文件上传失败or文件格式不合法");
				} else {
					// 拿到当前用户
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					bizFile.setUploadUserId(userId);
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						// 获取其他信息
						if (item.isFormField()) {
							if (item.getFieldName().equals("pid")) {
								conAcc.setPid(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}
						} else {
							if ("file2".equals(item.getFieldName())) {
								// 获取文件上传的信息
								bizFile.setUploadUserId(userId);
								fileSize = (int) item.getSize();
								bizFile.setFileSize(fileSize);
								String fileFullName = item.getName().toLowerCase();
								int dotLocation = fileFullName.lastIndexOf(".");
								fileName = fileFullName.substring(0, dotLocation);
								fileType = fileFullName.substring(dotLocation).substring(1);
								bizFile.setFileType(fileType);
								bizFile.setFileName(fileName);
							}
						}
					}
					String uploadDttm = DateUtil.format(new Date());
					bizFile.setUploadDttm(uploadDttm);
					String fileUrl = String.valueOf(map.get("path"));
					bizFile.setFileUrl(fileUrl);
					int uploadUserId = getShiroUser().getPid();
					bizFile.setUploadUserId(uploadUserId);
					bizFile.setStatus(1);
					if (fileSize == 0) {
						response.getWriter().write("fileSizeIsZero");
					}
					if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(fileType)) {
						response.getWriter().write("fileIsEmpty");
					}
					// 保存文件信息
					int fileId = bizFileClient.saveBizFile(bizFile);
					// 获取文件Id
					conAcc.setFileId(fileId);
					// 保存资料表信息
					count = client.editProjectAssFile(conAcc);
					if (count == 0) {
						tojson.put("uploadStatus", "重新上传抵质押办理资料失败");
					} else {
						tojson.put("uploadStatus", "重新上传抵质押办理资料成功");
					}
				}
			}
		} catch (Exception e) {
			logger.error("重新上传抵质押办理资料出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				} finally {
					if (bizFileClientFactory != null) {
						try {
							bizFileClientFactory.destroy();
						} catch (ThriftException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		outputJson(tojson, response);
	}

	@RequestMapping(value = "delProjectAssFile")
	public void delProjectAssFile(String pids, HttpServletResponse response) {
		BaseClientFactory c = null;
		int count = 0;
		try {
			c = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) c.getClient();
			count = client.delProjectAssFile(pids);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(count, response);
	}

	@RequestMapping(value = "editDesc")
	public void editDesc(ProjectAssFile projectAssFile, HttpServletResponse response) {
		BaseClientFactory c = null;
		int count = 0;
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) c.getClient();
			count = client.editProjectAssFile(projectAssFile);
			if (count == 0) {
				tojson.put("uploadStatus", "编辑抵质押办理资料失败");
			} else {
				tojson.put("uploadStatus", "编辑抵质押办理资料成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(tojson, response);
	}
}
