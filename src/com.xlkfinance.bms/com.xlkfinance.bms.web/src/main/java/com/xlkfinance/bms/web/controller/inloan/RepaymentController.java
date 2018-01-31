package com.xlkfinance.bms.web.controller.inloan;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.MoneyFormatUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.OverdueFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentRecordDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentService.Client;
import com.xlkfinance.bms.rpc.project.ProjectInfoService;
import com.xlkfinance.bms.rpc.repayment.RepaymentDetailIndexDTO;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月13日下午8:33:02 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 回款管理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/repaymentController")
public class RepaymentController extends BaseController {
   private static final String PAGE_PATH = "inloan/repayment/";
   private Logger logger = LoggerFactory.getLogger(RepaymentController.class);
   private final String serviceName = "RepaymentService";

   /**
    * 首页跳转
    */
   @RequestMapping(value = "/repayment_index")
   public String toRepaymentIndex(ModelMap model) {
      return PAGE_PATH + "repayment_index";
   }
   /**
    * 从任务跳转到业务办理列表，并定位数据
    *@author:liangyanjun
    *@time:2016年5月23日上午10:07:17
    *@param model
    *@param projectId
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping(value = "/toIndexByTask")
   public String toIndexByTask(ModelMap model,int projectId) throws ThriftServiceException, TException {
      ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
      Project projectInfo = projectService.getProjectInfoById(projectId);
      model.put("projectName", projectInfo.getProjectName());
      return PAGE_PATH + "repayment_index";
   }
   /**
    * 
    *@author:liangyanjun
    *@time:2016年3月13日下午8:16:18
    *@param repaymentIndexDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/repaymentIndexList")
   public void repaymentIndexList(RepaymentIndexDTO repaymentIndexDTO, HttpServletRequest request, HttpServletResponse response) {
      if (repaymentIndexDTO==null)repaymentIndexDTO=new RepaymentIndexDTO();
      Map<String, Object> map = new HashMap<String, Object>();
      List<RepaymentIndexDTO> repaymentIndexList = null;
      int total = 0;
      // 设置数据权限--用户编号list
      repaymentIndexDTO.setUserIds(getUserIds(request));
      try {
         // 查询回款列表
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         repaymentIndexList = service.queryRepaymentIndex(repaymentIndexDTO);
         total = service.getRepaymentIndexTotal(repaymentIndexDTO);
         logger.info("回款查询列表查询成功：total：" + total);
      } catch (Exception e) {
         logger.error("获取回款列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + repaymentIndexDTO);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", repaymentIndexList);
      map.put("total", total);
      outputJson(map, response);
   }

   /**
    * 保存回款
    *@author:liangyanjun
    *@time:2016年3月12日下午6:55:35
    *@param collectFileDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/saveRepayment", method = RequestMethod.POST)
   public void saveRepayment(RepaymentRecordDTO repaymentRecordDTO, HttpServletRequest request, HttpServletResponse response) {
      double repaymentMoney = repaymentRecordDTO.getRepaymentMoney();
      String repaymentDate = repaymentRecordDTO.getRepaymentDate();
      String accountNo = repaymentRecordDTO.getAccountNo();
      int projectId = repaymentRecordDTO.getProjectId();
      if (repaymentMoney <= 0 || projectId <= 0 || StringUtil.isBlank(repaymentDate,accountNo)) {
         logger.error("请求数据不合法：" + repaymentRecordDTO);
         fillReturnJson(response, false, "回款提交失败,请输入必填项!");
         return;
      }
      ShiroUser shiroUser = getShiroUser();
      Integer userId = shiroUser.getPid();
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
      try {
         Project project = projectService.getProjectInfoById(projectId);
         if (project.getForeclosureStatus()==Constants.FORECLOSURE_STATUS_13) {
            fillReturnJson(response, false, "已归档，回款提交失败!");
            return;
         }
         repaymentRecordDTO.setCreaterId(userId);
         service.addRepaymentRecord(repaymentRecordDTO);
         
         //回款完成往业务动态插入一条记录
         RepaymentDTO repaymentQuery=new RepaymentDTO();
         repaymentQuery.setProjectId(projectId);
         RepaymentDTO repaymentDTO = service.queryRepayment(repaymentQuery).get(0);
         if (repaymentDTO.getStatus()==Constants.REPAYMENT_STATUS_2) {
            String bizDynamicRemark = "回款金额:" + MoneyFormatUtil.format(repaymentDTO.getRepaymentMoney());
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_15, bizDynamicRemark);
         }
         fillReturnJson(response, true, "回款提交成功!");
      } catch (Exception e) {
         fillReturnJson(response, false, "回款提交失败,请联系管理员!");
         logger.error("保存回款失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + repaymentRecordDTO);
         e.printStackTrace();
         return;
      }
   }

   /**
    * 保存逾期费
    *@author:liangyanjun
    *@time:2016年3月13日下午10:27:48
    *@param overdueFeeDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/saveOverdueFee", method = RequestMethod.POST)
   public void saveOverdueFee(OverdueFeeDTO overdueFeeDTO, HttpServletRequest request, HttpServletResponse response) {
      double overdueFee = overdueFeeDTO.getOverdueFee();// 逾期费
      int paymentWay = overdueFeeDTO.getPaymentWay();// 付款方式
      int overdueDay = overdueFeeDTO.getOverdueDay();//逾期天数
      int projectId = overdueFeeDTO.getProjectId();// 项目id
      // 检查数据是否合法
      if (overdueFee < 0 || projectId <= 0 || paymentWay <= 0) {
         logger.error("请求数据不合法：" + overdueFeeDTO);
         fillReturnJson(response, false, "逾期费提交失败,请输入必填项!");
         return;
      }
      // 获取当前登录用户
      ShiroUser shiroUser = getShiroUser();
      Integer userId = shiroUser.getPid();
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         
         //查询逾期费数据
         OverdueFeeDTO overdueFeeQuery=new OverdueFeeDTO();
         overdueFeeQuery.setProjectId(projectId);
         List<OverdueFeeDTO> queryOverdueFee = service.queryOverdueFee(overdueFeeQuery);
         OverdueFeeDTO overdueFeeDAO = queryOverdueFee.get(0);
         
         //检查逾期费是否已确认
         if (queryOverdueFee.size()>0&&overdueFeeDAO.getIsConfirm()==Constants.IS_CONFIRM_OVERDUE_FEE) {
            fillReturnJson(response, false, "逾期费提交失败,逾期费已确认!");
            return;
         }
         overdueFeeDTO.setUpdateId(userId);
         overdueFeeDTO.setCreaterId(userId);
         overdueFeeDTO.setOverdueDay(overdueDay);
         service.confirmOverdueFee(overdueFeeDTO);
         //往业务动态插入一条记录
         String bizDynamicRemark = "逾期费:" + MoneyFormatUtil.format(overdueFee)+",逾期天数:" + overdueDay;
         finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_16, bizDynamicRemark);
      } catch (Exception e) {
         fillReturnJson(response, false, "逾期费提交失败,请联系管理员!");
         logger.error("保存逾期费失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + overdueFeeDTO);
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "逾期费提交成功");
   }

   /**
    * 查询回款历史
    *@author:liangyanjun
    *@time:2016年3月13日下午8:20:16
    *@param checkDocumentId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getRepaymentInfo", method = RequestMethod.POST)
   public void getRepaymentInfo(int projectId, HttpServletRequest request, HttpServletResponse response) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         Map<String, Object> returnMap = new HashMap<String, Object>();
         RepaymentRecordDTO repaymentRecordQuery = new RepaymentRecordDTO();
         repaymentRecordQuery.setProjectId(projectId);
         repaymentRecordQuery.setPage(-1);
         //查询回款记录
         List<RepaymentRecordDTO> repaymentRecordList = service.queryRepaymentRecord(repaymentRecordQuery);
         returnMap.put("repaymentRecordList", repaymentRecordList);

         OverdueFeeDTO overdueFeeQuery=new OverdueFeeDTO();
         overdueFeeQuery.setProjectId(projectId);
         overdueFeeQuery.setRows(1);
         //查询逾期费数据
         List<OverdueFeeDTO> overdueFeeList = service.queryOverdueFee(overdueFeeQuery);
         if (overdueFeeList!=null&&!overdueFeeList.isEmpty()) {
            OverdueFeeDTO overdueFeeDTO = overdueFeeList.get(0);
            returnMap.put("overdueFee", overdueFeeDTO);
         }else{
            returnMap.put("overdueFee", "");
         }
         outputJson(returnMap, response);
      } catch (Exception e) {
         logger.error("根据projectId查询回款历史数据失败：入参：projectId" + projectId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }
   /**
    * 根据项目id获取逾期费数据
    *@author:liangyanjun
    *@time:2016年3月17日下午3:15:42
    *@param projectId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getOverdueFeeInfo", method = RequestMethod.POST)
   public void getOverdueFeeInfo(int projectId, HttpServletRequest request, HttpServletResponse response) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         Map<String, Object> returnMap = new HashMap<String, Object>();
         OverdueFeeDTO overdueFeeQuery=new OverdueFeeDTO();
         overdueFeeQuery.setProjectId(projectId);
         overdueFeeQuery.setRows(1);
         //查询逾期费数据
         List<OverdueFeeDTO> overdueFeeList = service.queryOverdueFee(overdueFeeQuery);
         if (overdueFeeList!=null&&!overdueFeeList.isEmpty()) {
            OverdueFeeDTO overdueFeeDTO = overdueFeeList.get(0);
            returnMap.put("overdueFee", overdueFeeDTO);
         }else{
            returnMap.put("overdueFee", "");
         }
         outputJson(returnMap, response);
      } catch (Exception e) {
         logger.error("根据项目id获取逾期费数据失败：入参：projectId" + projectId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }
   
   /**
    * 抵押贷业务还款列表跳转
    * @param model
    * @return
    */
   @RequestMapping(value = "/repayment_info")
   public String toRepaymentInfoIndex(ModelMap model) {
      return PAGE_PATH + "repayment_info_index";
   }
   /**
    * 抵押贷业务还款列表
    * @param query
    * @param request
    * @param response
    */
   @RequestMapping(value = "/repaymentInfoList")
   public void repaymentInfoList(RepaymentIndexDTO query, HttpServletRequest request, HttpServletResponse response) {
      if (query==null)query=new RepaymentIndexDTO();
      Map<String, Object> map = new HashMap<String, Object>();
      List<RepaymentIndexDTO> repaymentList = null;
      int total = 0;
      // 设置数据权限--用户编号list
      query.setUserIds(getUserIds(request));
      try {
         // 查询回款列表
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         repaymentList = service.queryRepaymentInfo(query);
         total = service.getRepaymentInfoTotal(query);
         logger.info("抵押贷业务还款列表查询成功：total：" + total);
      } catch (Exception e) {
         logger.error("获取抵押贷业务还款列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + query);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", repaymentList);
      map.put("total", total);
      outputJson(map, response);
   } 
   
	/**
	 * 获取还款信息以及逾期信息(抵押贷产品还款操作弹出页面信息展示)
	 * @param projectId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getRepaymentDetailInfo", method = RequestMethod.POST)
	public void getRepaymentDetailInfo(@RequestParam(value = "projectId", required = true) Integer projectId,HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		RepaymentDetailIndexDTO result = new RepaymentDetailIndexDTO();
		try {
			ShiroUser shiroUser = getShiroUser();
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			RepaymentDetailIndexDTO query = new RepaymentDetailIndexDTO();
			query.setProjectId(projectId);
			// 查询项目财务收取页面展示数据
			result = client.queryOverdueByProject(projectId);
			result.setCreaterUser(shiroUser.getRealName());
		} catch (Exception e) {
				logger.error("查询项目财务收取页面展示数据出错:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(result, response);
	}
	
	/**
	 * 抵押贷产品还款保存
	 * @param repaymentDetailIndexDTO
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "saveRepaymentDetailInfo", method = RequestMethod.POST)
	public void saveRepaymentDetailInfo(RepaymentDetailIndexDTO repaymentDetailIndexDTO, HttpServletRequest request, HttpServletResponse response){
		 double repaymentMoney = repaymentDetailIndexDTO.getRepaymentMoney();
	      String repaymentDate = repaymentDetailIndexDTO.getRepaymentDate();
	      String accountNo = repaymentDetailIndexDTO.getAccountNo();
	      int projectId = repaymentDetailIndexDTO.getProjectId();
	      if (repaymentMoney <= 0 || projectId <= 0 || StringUtil.isBlank(repaymentDate,accountNo)) {
	         logger.error("请求数据不合法：" + repaymentDetailIndexDTO);
	         fillReturnJson(response, false, "抵押贷产品还款保存失败,请输入必填项!");
	         return;
	      }
	      ShiroUser shiroUser = getShiroUser();
	      Integer userId = shiroUser.getPid();
	      BaseClientFactory clientFactory = null;
	      try {
	    	  clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
			  ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			  repaymentDetailIndexDTO.setCreaterId(userId);
			  int repaymentType = repaymentDetailIndexDTO.getRepaymentType();
			  //还款模式为提前还款时，收取提前还款费，利息费为0
			  if(repaymentType == Constants.REPAYMENT_TYPE_2){
				  double fine  = repaymentDetailIndexDTO.getActualInterest();
				  repaymentDetailIndexDTO.setFine(fine);
				  repaymentDetailIndexDTO.setActualInterest(0.00);
			  }
			  client.saveRepaymentDetailInfo(repaymentDetailIndexDTO);
			  fillReturnJson(response, true, "还款提交成功!");
	      } catch (Exception e) {
	         fillReturnJson(response, false, "抵押贷产品还款保存失败,请联系管理员!");
	         logger.error("保存回款失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + repaymentDetailIndexDTO);
	         e.printStackTrace();
	         return;
	      } finally {
			destroyFactory(clientFactory);
	      }
	}
}
