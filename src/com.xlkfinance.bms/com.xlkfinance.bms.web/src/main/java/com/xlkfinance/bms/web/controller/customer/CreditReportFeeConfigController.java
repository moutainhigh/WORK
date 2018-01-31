package com.xlkfinance.bms.web.controller.customer;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.customer.CreditReportFeeConfig;
import com.xlkfinance.bms.rpc.customer.CreditReportFeeConfigService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;

/**
 * 征信报告费用配置控制器
 * ClassName: CreditReportFeeConfigController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018年1月16日 上午9:56:57 <br/>
 *
 * @author czz
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/creditReportFeeConfigController")
public class CreditReportFeeConfigController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(CusCreditReportHisController.class);

	/**
	 * 列表页面
	 */
	@RequestMapping(value = "/index")
	public String index(){
		return "customer/credit/credit_report_fee_config_index";
	}
	
	
	/**
	 * 列表数据
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public void list(CreditReportFeeConfig creditReportFeeConfig, HttpServletRequest request, HttpServletResponse response){
		logger.info("查询征信报告费用配置列表，入参："+creditReportFeeConfig);
		List<CreditReportFeeConfig> result = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		BaseClientFactory clientFactory = null;
		try {
 
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CreditReportFeeConfigService");
			CreditReportFeeConfigService.Client client =(CreditReportFeeConfigService.Client) clientFactory.getClient();
			result = client.selectList(creditReportFeeConfig);
			total = client.selectTotal(creditReportFeeConfig);
		} catch (Exception e) {
			logger.error("查询征信报告费用配置列表失败：" + e.getMessage());
		}finally {
			destroyFactory(clientFactory);
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	
	/**
	 * 详情
	 */
	@RequestMapping(value = "/details")
	@ResponseBody
	public void details(Integer pid, HttpServletRequest request, HttpServletResponse response){
		logger.info("查询征信报告费用配置详情，入参："+pid);
		CreditReportFeeConfig creditReportFeeConfig = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CreditReportFeeConfigService");
			CreditReportFeeConfigService.Client client =(CreditReportFeeConfigService.Client) clientFactory.getClient();
			creditReportFeeConfig = client.getById(pid);
		} catch (Exception e) {
			logger.error("查询征信报告费用配置详情：" + e.getMessage());
		}finally {
			destroyFactory(clientFactory);
		}
		outputJson(creditReportFeeConfig, response);
	}
	
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public void details(CreditReportFeeConfig creditReportFeeConfig, HttpServletRequest request, HttpServletResponse response){
		logger.info("保存征信报告费用配置，入参："+creditReportFeeConfig);
		BaseClientFactory clientFactory = null;
		Map<String,Object> body =new HashMap<String,Object>();
		int pid = 0 ; 
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CreditReportFeeConfigService");
			CreditReportFeeConfigService.Client client =(CreditReportFeeConfigService.Client) clientFactory.getClient();
			
			//查询该数据源是否己经有费用配置记录
			CreditReportFeeConfig queryConfig = new CreditReportFeeConfig();
			
			queryConfig.setDataSource(creditReportFeeConfig.getDataSource());
			queryConfig.setReportType(creditReportFeeConfig.getReportType());
			
			queryConfig.setDataSource(creditReportFeeConfig.getDataSource());
			queryConfig.setDataSource(creditReportFeeConfig.getDataSource());
			if(creditReportFeeConfig.getPid() == 0 && client.selectTotal(queryConfig) > 0  ){
				fillReturnJson(response, false, "该报告费用已存在，请刷新重新操作!");
				return;
			}
			Timestamp nowTime = new Timestamp(new Date().getTime());
			//当前操作人员
			ShiroUser shiroUser = getShiroUser();
			
			creditReportFeeConfig.setUpdateTime(nowTime.toString());
			creditReportFeeConfig.setUpdator(shiroUser.getPid());
			
			if(creditReportFeeConfig.getPid() > 0 ){
				pid = creditReportFeeConfig.getPid();
				client.update(creditReportFeeConfig);
			}else{
				creditReportFeeConfig.setCreateTime(nowTime.toString());
				creditReportFeeConfig.setCreator(shiroUser.getPid());
				pid = client.insert(creditReportFeeConfig);
			}
			body.put("pid", pid);
			fillReturnJson(response, true, "操作成功!",body);
			return;
		} catch (Exception e) {
			logger.error("保存征信报告费用配置：" + e.getMessage());
			
			fillReturnJson(response, false, "操作失败!");
			return;
			
		}finally {
			destroyFactory(clientFactory);
		}
	}
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteBatch")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void deleteBatch(String pidListStr, HttpServletRequest request, HttpServletResponse response){
		logger.info("批量删除征信报告费用配置，入参："+pidListStr);
		BaseClientFactory clientFactory = null;
		Map<String,Object> body =new HashMap<String,Object>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CreditReportFeeConfigService");
			CreditReportFeeConfigService.Client client =(CreditReportFeeConfigService.Client) clientFactory.getClient();
			if(StringUtil.isBlank(pidListStr)){
				fillReturnJson(response, false, "参数不能为空!");
				return;
			}
			
			List<Integer> pidList = CommonUtil.getSepStrtoList(pidListStr, "Integer", ",");
			
			int count = client.deleteByIds(pidList);
			
			if(count <= 0 ){
				fillReturnJson(response, false, "删除失败!");
				return;
			}
			logger.info("批量删除征信报告费用配置，删除数量："+count);
			fillReturnJson(response, true, "操作成功!",body);
			return;
		} catch (Exception e) {
			logger.error("批量删除征信报告费用配置：" + e.getMessage());
			fillReturnJson(response, false, "操作失败!");
			return;
			
		}finally {
			destroyFactory(clientFactory);
		}
	}
 
	
	/**
	 * 获取费用配置信息
	 */
	@RequestMapping(value = "/getReportFeeConfig")
	@ResponseBody
	public void getReportFeeConfig(CreditReportFeeConfig query, HttpServletRequest request, HttpServletResponse response){
		logger.info("获取费用配置信息，入参："+query);
		List<CreditReportFeeConfig> result = null;
		BaseClientFactory clientFactory = null;
		Map<String,Object> body =new HashMap<String,Object>();
		try {
			if(query.getReportType() == 0 ){
				fillReturnJson(response, false, "报告类型不能为空!");
				return;
			}
			
			//不分页
			query.setPage(-1);
			
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CreditReportFeeConfigService");
			CreditReportFeeConfigService.Client client =(CreditReportFeeConfigService.Client) clientFactory.getClient();
			result = client.selectList(query);
			
			if(CollectionUtils.isEmpty(result)){
				fillReturnJson(response, false, "报告费用未配置!");
				return;
			}
			CreditReportFeeConfig feeConfig = result.get(0);
			body.put("feeConfig", feeConfig);
			fillReturnJson(response, true, "操作成功!",body);
			return ;
		} catch (Exception e) {
			logger.error("获取费用配置信息失败：" + e.getMessage());
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			destroyFactory(clientFactory);
		}
	}
	
	
	
	
	
	
	 
 
 
}
