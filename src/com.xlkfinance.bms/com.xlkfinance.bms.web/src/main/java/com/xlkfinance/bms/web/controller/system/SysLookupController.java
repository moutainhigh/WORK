/**
 * @Title: SysLookupController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * @Description: 系统管理
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月13日 上午10:50:14
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

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
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysLookup;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

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
	private Logger logger = LoggerFactory.getLogger(SysLookupController.class);

	/**
	 * 
	 * @Description: 中转方法
	 * @return 需要跳转的页面
	 * @author: Mr.Cai
	 * @date: 2014年12月25日 下午3:21:38
	 */
	@RequestMapping(value = "navigation")
	public String navigation() {
		return "system/index_lookup";
	}

	/**
	 * 
	 * @Description: 根据数据字典类型查询当前数据类型的值
	 * @param lookupType
	 *            数据字典类型
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2014年12月29日 上午11:17:29
	 */
	@RequestMapping(value = "getSysLookupValByLookType")
	@ResponseBody
	public void getSysLookupValByLookType(String lookupType, HttpServletResponse response) {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			SysLookupVal s = new SysLookupVal();
			s.setPid(-1);
			s.setLookupVal("");
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

	/**
	 * 
	 * @Description: 查询抵质押物所需查询的详细信息数据
	 * @param lookupType
	 *            抵质押物类型值
	 * @param response
	 *            HttpServletResponse对象
	 * @author: Cai.Qing
	 * @date: 2015年4月23日 上午1:59:24
	 */
	@RequestMapping(value = "getProjectAssDtlByLookType")
	public void getProjectAssDtlByLookType(String lookupType, HttpServletResponse response) {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			list = client.getProjectAssDtlByLookType(lookupType);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("抵质押物所需查询的详细信息数据:" + e.getMessage());
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
	 * @Description: 根据数据字典类型查询当前数据类型的值
	 * @param lookupType
	 *            数据字典类型
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年2月9日 上午11:17:29
	 */
	@RequestMapping(value = "getSysLookupValByLookTypeTwo")
	@ResponseBody
	public void getSysLookupValByLookTypeTwo(String lookupType, HttpServletResponse response) {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			list = client.getSysLookupValByLookType(lookupType);
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
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			SysLookupVal s = new SysLookupVal();
			s.setPid(0);
			s.setLookupVal("");
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

	/**
	 * 
	  * @Description: 通过id得到名称，财务放款环节需要
	  * @param pid
	  * @param response
	  * @author: Rain.Lv
	  * @date: 2015年5月20日 下午3:03:55
	 */
	@RequestMapping(value = "getSysLookupValInfo")
	@ResponseBody
	public void getSysLookupValInfo(String pid, HttpServletResponse response) {
		String lookUpName =null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			 lookUpName = client.getSysLookupValByName(Integer.parseInt(pid));
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
		outputJson(lookUpName, response);
	}
	
	/**
	 * 
	 * @Description: 条件查询数据字典配置
	 * @param sysLookup
	 *            数据字典配置对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月25日 下午3:22:17
	 */
	@RequestMapping(value = "getAllSysLookup", method = RequestMethod.POST)
	@ResponseBody
	public void getAllSysLookup(SysLookup sysLookup, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysLookup> listUser = new ArrayList<SysLookup>();
		int count = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			listUser = client.getAllSysLookup(sysLookup);
			count = client.getAllSysLookupSum(sysLookup);
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
		map.put("rows", listUser);
		map.put("total", count);
		// 输出
		outputJson(map, response);
	}

	/**
	 * @Description: 删除数据字典配置
	 * @param configPid
	 *            数据字典配置ID
	 * @param response
	 * @author: Mr.Cai
	 * @date: 2014年12月25日 下午3:24:10
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestParam(value = "pids[]") int[] pids, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			List<Integer> SysLookuPids = new ArrayList<Integer>();
			for (int i : pids) {
				SysLookuPids.add(i);
			}
			int rows = client.deleteLookup(SysLookuPids);
			// 判断是否成功
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "删除数据字典配置失败,请重新操作！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除数据字典配置失败,请重新操作！");
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
	 * 
	 * @Description: 新增数据字典配置对象
	 * @param sysLookup
	 *            数据字典配置对象对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月25日 下午3:34:10
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	public void insert(SysLookup sysLookup, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			// 调用新增方法
			int rows = client.addSysLookup(sysLookup);
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "添加数据字典配置失败,请重新操作！");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "添加数据字典配置失败,请重新操作！");
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
	 * 
	 * @Description: 修改数据字典配置对象信息
	 * @param sysLookup
	 *            数据字典配置对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月25日 下午3:39:10
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public void update(SysLookup sysLookup, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			// 调用修改方法
			int rows = client.updateSysLookup(sysLookup);
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改数据字典配置失败,请重新操作！");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改数据字典配置失败,请重新操作！");
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
	 * 
	 * @Description: 获取当前数据字典下面的值集合
	 * @param lookupId
	 *            数据字典配置项id
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:49:30
	 */
	@RequestMapping(value = "getSysLookupValByLookupId")
	@ResponseBody
	public void getSysLookupValByLookupId(int lookupId,int page,int rows, HttpServletResponse response) {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		Map<String,Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		int total = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			list = client.getSysLookupValByLookupId(lookupId,page,rows);
			total = client.getSysLookupValByLookupIdTotal(lookupId);
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
		map.put("rows", list);
		map.put("total", total);
		// 输出
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 删除数据字典值
	 * @param lookupValPids
	 *            数据字典值字符串
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:53:13
	 */
	@RequestMapping(value = "deleteLookupVal")
	@ResponseBody
	public void deleteLookupVal(String lookupValPids, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			int rows = client.deleteLookupVal(lookupValPids);
			// 判断是否成功
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "删除数据字典值失败,请重新操作！");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除数据字典值失败,请重新操作！");
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
	 * 
	 * @Description: 新增数据字典值
	 * @param sysLookupVal
	 *            数据字典值对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:54:11
	 */
	@RequestMapping(value = "insertLookupVal")
	@ResponseBody
	public void insertLookupVal(SysLookupVal sysLookupVal, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			// 调用新增方法
			int rows = client.addSysLookupVal(sysLookupVal);
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "添加数据字典值失败,请重新操作！");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "添加数据字典值失败,请重新操作！");
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
	 * 
	 * @Description: 更新数据字典值
	 * @param sysLookupVal
	 *            数据字典值对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:54:18
	 */
	@RequestMapping(value = "updateLookupVal")
	@ResponseBody
	public void updateLookupVal(SysLookupVal sysLookupVal, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			// 调用修改方法
			int rows = client.updateSysLookupVal(sysLookupVal);
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改数据字典值失败,请重新操作！");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改数据字典值失败,请重新操作！");
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
	 * 根据数据字典类型 和子类型 获取对象
	 * @param lookupType
	 * @param response
	 */
	@RequestMapping(value = "getSysLookupValByChildType")
	@ResponseBody
	public void getSysLookupValByChildType(String lookupType,String lookupVal, HttpServletResponse response) {
		 
		BaseClientFactory clientFactory = null;
		SysLookupVal  sysLookupVal = new SysLookupVal();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
 
			sysLookupVal =client.getSysLookupValByChildType(lookupType, lookupVal);
			
			if(sysLookupVal == null){
				sysLookupVal = new SysLookupVal();
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
		// 输出
		outputJson(sysLookupVal, response);
	}
	
	/**
	 * 
	  * @Description: 通过id得到名称，财务放款环节需要
	  * @param pid
	  * @param response
	  * @author: Rain.Lv
	  * @date: 2015年5月20日 下午3:03:55
	 */
	@RequestMapping(value = "getSysLookupValByPid")
	@ResponseBody
	public void getSysLookupValByPid(String pid, HttpServletResponse response) {
		String lookUpVal =null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			 lookUpVal = client.getSysLookupValByPid(Integer.parseInt(pid));
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
		outputJson(lookUpVal, response);
	}
	
}
