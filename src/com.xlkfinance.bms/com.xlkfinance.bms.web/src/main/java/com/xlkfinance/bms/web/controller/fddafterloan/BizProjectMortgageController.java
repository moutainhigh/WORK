package com.xlkfinance.bms.web.controller.fddafterloan;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.MortgageDynamicConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgage;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgageIndexPage;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgageService;
import com.xlkfinance.bms.rpc.fddafterloan.BizStorageInfo;
import com.xlkfinance.bms.rpc.fddafterloan.BizStorageInfoService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月27日上午10:32:34 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 抵押管理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/bizProjectMortgageController")
public class BizProjectMortgageController extends BaseController {
   private static final String PAGE_PATH = "fddafterloan/";
   private Logger logger = LoggerFactory.getLogger(BizProjectMortgageController.class);
   private final String serviceName = "BizProjectMortgageService";

   /**
    * 首页跳转
    */
   @RequestMapping(value = "/toMortgageIndex")
   public String toMortgageIndex(ModelMap model) {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      return url.append("mortgage_index").toString();
   }
   /**
    * 跳转到抵押查看页面
    *@author:liangyanjun
    *@time:2017年12月21日上午10:33:44
    *@param model
    *@return
    * @throws TException 
    */
   @RequestMapping(value = "/toMortgageShow")
   public String toMortgageShow(ModelMap model,int pid) throws TException {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      
      BizProjectMortgageService.Client service = (BizProjectMortgageService.Client) getService(BusinessModule.MODUEL_FDD_AFTERLOAN,serviceName);
      BizProjectMortgage projectMortgage = service.getById(pid);
      model.put("projectMortgage", projectMortgage);
      return url.append("mortgage_show").toString();
   }

   /**
    * 抵押管理列表（分页查询）
    *@author:liangyanjun
    *@time:2017年12月19日上午11:25:37
    *@param query
    *@param request
    *@param response
    */
   @RequestMapping(value = "/queryMortgageIndexPage")
   @ResponseBody
   public void queryMortgageIndexPage(BizProjectMortgageIndexPage query, HttpServletRequest request, HttpServletResponse response) {
      Map<String, Object> map = new HashMap<String, Object>();
      List<BizProjectMortgageIndexPage> list = null;
      int total = 0;
      // 设置数据权限--用户编号list
      query.setUserIds(getUserIds(request));
      try {
         BizProjectMortgageService.Client service = (BizProjectMortgageService.Client) getService(BusinessModule.MODUEL_FDD_AFTERLOAN,
               serviceName);
         list = service.queryMortgageIndexPage(query);
         total = service.getMortgageIndexPageTotal(query);
      } catch (Exception e) {
         logger.error("获取抵押管理列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + query);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", list);
      map.put("total", total);
      outputJson(map, response);
   }

   /**
    * 入库材料清单（分页查询）
    *@author:liangyanjun
    *@time:2017年12月20日上午10:56:40
    *@param query
    *@param request
    *@param response
    */
   @RequestMapping(value = "/queryStorageInfo")
   @ResponseBody
   public void queryStorageInfo(BizStorageInfo query, HttpServletRequest request, HttpServletResponse response) {
      int projectId = query.getProjectId();
      if (projectId <= 0) {
         return;
      }
      Map<String, Object> map = new HashMap<String, Object>();
      List<BizStorageInfo> list = null;
      int total = 0;
      try {
         BizStorageInfoService.Client service = (BizStorageInfoService.Client) getService(BusinessModule.MODUEL_FDD_AFTERLOAN,
               "BizStorageInfoService");
         list = service.queryStorageInfo(query);
         total = service.getStorageInfoTotal(query);
      } catch (Exception e) {
         logger.error("获取入库材料清单失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + query);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", list);
      map.put("total", total);
      outputJson(map, response);
   }

   /**
    * 保存入库资料清单
    *@author:liangyanjun
    *@time:2017年12月20日上午11:10:30
    *@param project
    *@param request
    *@param response
    */
   @RequestMapping(value = "/saveStorageInfo")
   public void saveStorageInfo(BizStorageInfo storageInfo, HttpServletRequest request, HttpServletResponse response) {
      int projectId = storageInfo.getProjectId();//项目id
      String fileName = storageInfo.getFileName();//资料名
      String registerTime = storageInfo.getRegisterTime();//入库时间
      if (projectId <= 0 || StringUtil.isBlank(fileName, registerTime)) {
         fillReturnJson(response, false, "提交失败,数据不合法!");
         return;
      }
      ShiroUser shiroUser = getShiroUser();
      Integer userId = shiroUser.getPid();
      try {
         BizStorageInfoService.Client storageInfoService = (BizStorageInfoService.Client) getService(BusinessModule.MODUEL_FDD_AFTERLOAN,
               "BizStorageInfoService");
         BizProjectMortgageService.Client projectMortgageService = (BizProjectMortgageService.Client) getService(BusinessModule.MODUEL_FDD_AFTERLOAN,
               serviceName);
         BizProjectMortgage query=new BizProjectMortgage();
         query.setProjectId(projectId);
         List<BizProjectMortgage> list = projectMortgageService.getAll(query);
         if (list == null||list.isEmpty()) {
            fillReturnJson(response, false, "提交失败,数据不合法!");
            return;
         }
         BizProjectMortgage updateProjectMortgage = list.get(0);
         // 检查是否已经入库
         int mortgageStatus = updateProjectMortgage.getMortgageStatus();
         if (mortgageStatus > Constants.MORTGAGE_STATUS_2) {
            fillReturnJson(response, false, "提交失败,入库登记流程已过，不能再录入资料清单");
            return;
         }
         // 执行入库登记
         updateProjectMortgage.setRegisterTime(DateUtils.getCurrentDateTime());
         updateProjectMortgage.setRegisterId(userId);
         updateProjectMortgage.setMortgageStatus(Constants.MORTGAGE_STATUS_2);
         projectMortgageService.update(updateProjectMortgage);
         //保存入库资料清单
         storageInfo.setCreateId(userId);
         storageInfoService.insert(storageInfo);
         finishBizDynamicByMortgage(userId, projectId, MortgageDynamicConstant.MODUEL_NUMBER_MORTGAGE,MortgageDynamicConstant.MODUEL_NUMBER_MORTGAGE_1, "");
      } catch (Exception e) {
         logger.error("保存入库资料清单失败：入参：storageInfo" + storageInfo + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }

   /**
    * 根据handleType保存，抵押办理信息
    *@author:liangyanjun
    *@time:2017年12月21日上午10:27:43
    *@param projectMortgage
    *@param request
    *@param response
    */
   @RequestMapping(value = "/saveHandleInfo")
   public void saveHandleInfo(BizProjectMortgage projectMortgage, HttpServletRequest request, HttpServletResponse response) {
      int pid = projectMortgage.getPid();// id
      int handleType = projectMortgage.getHandleType();// 办理类型：2=待出库申请，3=待出具注销材料，4=待退证登记
      String signerUserName = projectMortgage.getSignerUserName();// 签收人
      String signerDate = projectMortgage.getSignerDate();// 签收日期
      String signerFile = projectMortgage.getSignerFile();// 签收材料
      if (pid <= 0 || handleType <= 0|| StringUtil.isBlank(signerUserName, signerDate,signerFile)) {
         fillReturnJson(response, false, "提交失败,数据不合法!");
         return;
      }
      try {
         BizProjectMortgageService.Client service = (BizProjectMortgageService.Client) getService(BusinessModule.MODUEL_FDD_AFTERLOAN,
               serviceName);
         BizProjectMortgage updateProjectMortgage = service.getById(pid);
         if (updateProjectMortgage == null) {
            fillReturnJson(response, false, "提交失败,数据不合法!");
            return;
         }
         ShiroUser shiroUser = getShiroUser();
         Integer userId = shiroUser.getPid();
         int projectId = updateProjectMortgage.getProjectId();
         int mortgageStatus = updateProjectMortgage.getMortgageStatus();
         // 待出库申请保存
         if (mortgageStatus == Constants.MORTGAGE_STATUS_2 && handleType == Constants.MORTGAGE_STATUS_2) {
            updateProjectMortgage.setIssueTime(signerDate);
            updateProjectMortgage.setIssueUserName(signerUserName);
            updateProjectMortgage.setIssueMaterial(signerFile);
            updateProjectMortgage.setMortgageStatus(Constants.MORTGAGE_STATUS_3);
            service.update(updateProjectMortgage);
            finishBizDynamicByMortgage(userId, projectId, MortgageDynamicConstant.MODUEL_NUMBER_MORTGAGE,MortgageDynamicConstant.MODUEL_NUMBER_MORTGAGE_2, "");
            // 待出具注销材料保存
         } else if (mortgageStatus == Constants.MORTGAGE_STATUS_3 && handleType == Constants.MORTGAGE_STATUS_3) {
            updateProjectMortgage.setCancelTime(signerDate);
            updateProjectMortgage.setCancelUserName(signerUserName);
            updateProjectMortgage.setCancelMaterial(signerFile);
            updateProjectMortgage.setMortgageStatus(Constants.MORTGAGE_STATUS_4);
            service.update(updateProjectMortgage);
            finishBizDynamicByMortgage(userId, projectId, MortgageDynamicConstant.MODUEL_NUMBER_MORTGAGE,MortgageDynamicConstant.MODUEL_NUMBER_MORTGAGE_3, "");
            // 待退证登记保存
         } else if (mortgageStatus == Constants.MORTGAGE_STATUS_4 && handleType == Constants.MORTGAGE_STATUS_4) {
            updateProjectMortgage.setReturnTime(signerDate);
            updateProjectMortgage.setReturnUserName(signerUserName);
            updateProjectMortgage.setReturnMaterial(signerFile);
            updateProjectMortgage.setMortgageStatus(Constants.MORTGAGE_STATUS_5);
            service.update(updateProjectMortgage);
            finishBizDynamicByMortgage(userId, projectId, MortgageDynamicConstant.MODUEL_NUMBER_MORTGAGE,MortgageDynamicConstant.MODUEL_NUMBER_MORTGAGE_4, "");
         } else {
            fillReturnJson(response, false, "提交失败,未到该流程办理!");
            return;
         }
      } catch (Exception e) {
         logger.error("保存抵押信息失败：入参：storageInfo" + projectMortgage + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }
   /**
    * 根据ids删除入库资料清单
    *@author:liangyanjun
    *@time:2018年1月3日上午9:37:49
    *@param ids
    *@param projectMortgageId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/deleteStorageInfoByIds")
   public void deleteStorageInfoByIds(String ids, int projectMortgageId,HttpServletRequest request, HttpServletResponse response) {
      if (StringUtil.isBlank(ids)||projectMortgageId<=0) {
         fillReturnJson(response, false, "请选择删除的数据");
         return;
      }
      BizProjectMortgageService.Client service = (BizProjectMortgageService.Client) getService(BusinessModule.MODUEL_FDD_AFTERLOAN,serviceName);
      BizStorageInfoService.Client storageInfoService = (BizStorageInfoService.Client) getService(BusinessModule.MODUEL_FDD_AFTERLOAN,"BizStorageInfoService");
      try {
         BizProjectMortgage projectMortgage = service.getById(projectMortgageId);
         int projectId = projectMortgage.getProjectId();
         int mortgageStatus = projectMortgage.getMortgageStatus();
         //检查是否已经办理出库申请
         if (mortgageStatus >= Constants.MORTGAGE_STATUS_3) {
            fillReturnJson(response, false, "已经出库，不能删除");
            return;
         }
         String[] idArray = ids.split(",");
         List<Integer> idList=new ArrayList<Integer>();
         for (String id : idArray) {
            idList.add(Integer.parseInt(id));
         }
         storageInfoService.deleteByIds(idList);
         //查询是否还有清单，没有清单则修改状态为待登记入库
         BizStorageInfo query=new BizStorageInfo();
         query.setProjectId(projectId);
         List<BizStorageInfo> storageInfos = storageInfoService.getAll(query);
         if (storageInfos.size()==0) {
            projectMortgage.setMortgageStatus(Constants.MORTGAGE_STATUS_1);
            service.update(projectMortgage);
         }
      } catch (TException e) {
         logger.error("删除入库资料清单失败：入参：ids" + ids + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "删除入库资料清单失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "删除成功");
   }
}
