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
import org.springframework.web.bind.annotation.ResponseBody;

import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferDTO;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferIndexDTO;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferService.Client;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年4月2日下午2:58:53 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/intermediateTransferController")
public class IntermediateTransferController extends BaseController {
   private static final String PAGE_PATH = "inloan/intermediateTransfer/";
   private Logger logger = LoggerFactory.getLogger(IntermediateTransferController.class);
   private final String serviceName = "IntermediateTransferService";

   /**
    * 
    *@author:liangyanjun
    *@time:2016年4月2日下午6:00:41
    *@param model
    *@return
    */
   @RequestMapping(value = "/intermediate_transfer_index")
   public String toIntermediateTransferIndex(ModelMap model) {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      url.append("intermediate_transfer_index");
      return url.toString();
   }
   /**
    * 
    *@author:liangyanjun
    *@time:2016年4月2日下午6:00:34
    *@param model
    *@return
    */
   @RequestMapping(value = "/intermediate_transfer_request_index")
   public String toIntermediateTransferRequestIndex(ModelMap model) {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      url.append("intermediate_transfer_request_index");
      return url.toString();
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年4月2日下午3:02:39
    *@param intermediateTransferIndexDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/intermediateTransferList")
   public void intermediateTransferList(IntermediateTransferIndexDTO intermediateTransferIndexDTO, HttpServletRequest request, HttpServletResponse response) {
      if (intermediateTransferIndexDTO==null)intermediateTransferIndexDTO=new IntermediateTransferIndexDTO();
      Map<String, Object> map = new HashMap<String, Object>();
      List<IntermediateTransferIndexDTO> intermediateTransferList = null;
      int total = 0;
      // 设置数据权限--用户编号list
      intermediateTransferIndexDTO.setUserIds(getUserIds(request));
      int type = intermediateTransferIndexDTO.getType();
      try {
         // 查询中途划转列表
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         if (type==2) {
            //中途划转列表
            intermediateTransferList = service.queryIntermediateTransferRequestIndex(intermediateTransferIndexDTO);
            total = service.getIntermediateTransferRequestIndexTotal(intermediateTransferIndexDTO);
         }else{
            //中途划转查询
            intermediateTransferList = service.queryIntermediateTransferIndex(intermediateTransferIndexDTO);
            total = service.getIntermediateTransferIndexTotal(intermediateTransferIndexDTO);
         }
         logger.info("中途划转查询列表查询成功：total：" + total + ",intermediateTransferIndexList:" + intermediateTransferList);
      } catch (Exception e) {
         logger.error("获取中途划转列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + intermediateTransferIndexDTO);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", intermediateTransferList);
      map.put("total", total);
      outputJson(map, response);
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年4月2日下午5:31:48
    *@param model
    *@param intermediateTransferDTO
    *@param request
    *@return
    */
   @RequestMapping(value = "/edit")
   public String edit(ModelMap model, IntermediateTransferDTO intermediateTransferDTO, HttpServletRequest request) {
      int projectId = intermediateTransferDTO.getProjectId();
      ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
      try {
         Project project = projectService.getProjectInfoById(projectId);
         String projectName = project.getProjectName();
         String projectNumber = project.getProjectNumber();
         model.put("projectId", projectId);
         model.put("projectName", projectName);
         model.put("projectNumber", projectNumber);
      }catch (TException e) {
         logger.error("获取中途划转失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + intermediateTransferDTO);
         e.printStackTrace();
      }
      return PAGE_PATH + "intermediate_transfer_edit";
   }
   /**
    * 跳转编辑页面
    *@author:liangyanjun
    *@time:2016年4月6日下午11:56:19
    *@param model
    *@param intermediateTransferDTO
    *@param request
    *@return
    */
   @RequestMapping(value = "/requestEdit")
   public String requestEdit(ModelMap model, IntermediateTransferDTO intermediateTransferDTO, HttpServletRequest request) {
      int projectId = intermediateTransferDTO.getProjectId();
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
      try {
         List<IntermediateTransferDTO> list = service.queryIntermediateTransfer(intermediateTransferDTO);
         if (list != null && !list.isEmpty()) {
            model.put("intermediateTransfer", list.get(0));
         }
         Project project = projectService.getProjectInfoById(projectId);
         String projectName = project.getProjectName();
         String projectNumber = project.getProjectNumber();
         model.put("projectId", projectId);
         model.put("projectName", projectName);
         model.put("projectNumber", projectNumber);
      } catch (TException e) {
         logger.error("获取中途划转失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + intermediateTransferDTO);
         e.printStackTrace();
      }
      return PAGE_PATH + "intermediate_transfer_edit";
   }
   /**
    * 在任务时进入编辑页面
    *@author:liangyanjun
    *@time:2016年4月22日上午10:20:20
    *@param model
    *@param intermediateTransferDTO
    *@param request
    *@return
    */
   @RequestMapping(value = "/editByProcess")
   public String editByProcess(ModelMap model, IntermediateTransferDTO intermediateTransferDTO, HttpServletRequest request) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
      try {
         IntermediateTransferDTO intermediateTransfer = service.getIntermediateTransferById(intermediateTransferDTO.getPid());
         Project project = projectService.getProjectInfoById(intermediateTransfer.getProjectId());
         String projectName = project.getProjectName();
         String projectNumber = project.getProjectNumber();
         model.put("intermediateTransfer", intermediateTransfer);
         model.put("projectId", intermediateTransfer.getProjectId());
         model.put("projectName", projectName);
         model.put("projectNumber", projectNumber);
      } catch (TException e) {
         logger.error("获取中途划转失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + intermediateTransferDTO);
         e.printStackTrace();
      }
      return PAGE_PATH + "intermediate_transfer_edit";
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年4月2日下午5:40:59
    *@param intermediateTransferDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
   public void addOrUpdate(IntermediateTransferDTO intermediateTransferDTO, HttpServletRequest request, HttpServletResponse response) {
      ShiroUser shiroUser = getShiroUser();
      int pid = intermediateTransferDTO.getPid();
      double recMoney = intermediateTransferDTO.getRecMoney();
      String recAccount = intermediateTransferDTO.getRecAccount();
      String recDate = intermediateTransferDTO.getRecDate();
      double transferMoney = intermediateTransferDTO.getTransferMoney();
      String transferAccount = intermediateTransferDTO.getTransferAccount();
      String transferDate = intermediateTransferDTO.getTransferDate();
      if (StringUtil.isBlank(recAccount, recDate, transferAccount, transferDate)) {
         logger.error("请求数据不合法：" + intermediateTransferDTO);
         fillReturnJson(response, false, "提交失败,请输入必填项,重新操作!");
         return;
      }
      if (recMoney < 0 || transferMoney < 0) {
         fillReturnJson(response, false, "提交失败,金额要大于0,请重新操作!");
         return;
      }
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         IntermediateTransferDTO query=new IntermediateTransferDTO();
         query.setPid(pid);
         List<IntermediateTransferDTO> transferDTOs = service.queryIntermediateTransfer(query);
         //不存在则新增
         if (pid<=0||transferDTOs==null||transferDTOs.isEmpty()) {
            intermediateTransferDTO.setCreaterId(shiroUser.getPid());
            intermediateTransferDTO.setUpdateId(shiroUser.getPid());
            intermediateTransferDTO.setApplyStatus(Constants.APPLY_STATUS_2);
            pid = service.addIntermediateTransfer(intermediateTransferDTO);
            outputJson(pid, response);
            return;
         }
         
         IntermediateTransferDTO updateIntermediateTransferDTO = transferDTOs.get(0);
         if (updateIntermediateTransferDTO.getApplyStatus()>Constants.APPLY_STATUS_3) {
            fillReturnJson(response, false, "提交失败,状态为待审核以后不可修改!");
            return;
         }
         if (updateIntermediateTransferDTO.getApplyStatus()==Constants.APPLY_STATUS_3&&updateIntermediateTransferDTO.getCreaterId()!=shiroUser.getPid()) {
            fillReturnJson(response, false, "提交失败,非申请者不可修改!");
            return;
         }
         
         //存在则更新
         updateIntermediateTransferDTO.setRecMoney(recMoney);
         updateIntermediateTransferDTO.setRecAccount(recAccount);
         updateIntermediateTransferDTO.setRecDate(recDate);
         updateIntermediateTransferDTO.setTransferMoney(transferMoney);
         updateIntermediateTransferDTO.setTransferAccount(transferAccount);
         updateIntermediateTransferDTO.setTransferDate(transferDate);
         updateIntermediateTransferDTO.setUpdateId(shiroUser.getPid());
         service.updateIntermediateTransfer(updateIntermediateTransferDTO);
         outputJson(updateIntermediateTransferDTO.getPid(), response);
      } catch (Exception e) {
         fillReturnJson(response, false, "提交失败,出现未知异常请联系管理员!");
         logger.error("保存途划转失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + intermediateTransferDTO);
         e.printStackTrace();
         return;
      }
   }

   /**
    * 根据项目id和状态（查询的条件为>=状态值） 检查是否有工作流在运行,返回值大于0则有工作流运行
    *@author:liangyanjun
    *@time:2016年3月3日下午11:56:04
    *@param refundFeeDTO
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/checkWorkFlowExist")
   @ResponseBody
   public int checkWorkFlowExist(IntermediateTransferDTO intermediateTransferDTO, HttpServletRequest request, HttpServletResponse response) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         int count = service.checkWorkFlowExist(intermediateTransferDTO);
         return count;
      } catch (TException e) {
         logger.error(ExceptionUtil.getExceptionMessage(e) + ",入参：" + intermediateTransferDTO);
         e.printStackTrace();
         return -1;
      }
   }
}
