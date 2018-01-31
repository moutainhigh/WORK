package com.qfang.xk.aom.web.controller.inloan;

import java.net.URLDecoder;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.web.controller.BaseController;
import com.qfang.xk.aom.web.util.FileUtil;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService.Client;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicFileDTO;
import com.xlkfinance.bms.rpc.inloan.HandleFlowDTO;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.OrgBizHandlePage;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年9月1日上午9:26:57 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务办理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/bizHandleController")
public class BizHandleController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(BizHandleController.class);
   private String PATH = "/org/inloan";

   @ExceptionHandler
   public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
      logger.error(ExceptionUtil.getExceptionMessage(e), e);
      fillReturnJson(response, false, "出现未知异常,请与系统管理员联系!");
      return null;
   }

   /**
    * 跳转到业务办理（保后监管）页面
    *@author:liangyanjun
    *@time:2016年8月31日上午11:27:50
    *@param model
    *@return
    */
   @RequestMapping("/toBizHandleIndex")
   public String toCustomerIndex(ModelMap model) {
      return PATH + "/biz_handle_index";
   }

   /**
    * 跳转到查询业务办理页面
    *@author:liangyanjun
    *@time:2016年8月31日下午2:53:48
    *@param model
    *@return
    * @throws ThriftException 
    * @throws TException 
    */
   @RequestMapping("/toShowBizHandle")
   public String toShowBizHandle(ModelMap model, int bizHandleId) throws ThriftException, TException {
      OrgUserInfo loginUser = getLoginUser();
      BaseClientFactory bizHandleFactory = getFactory(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_INLOAN, "BizHandleService");
      BaseClientFactory projectFactory = getFactory(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
      BizHandleService.Client bizHandleClient = (BizHandleService.Client) bizHandleFactory.getClient();
      ProjectService.Client projectClient = (ProjectService.Client) projectFactory.getClient();
      HandleInfoDTO handleInfo = bizHandleClient.getHandleInfoById(bizHandleId);
      if (handleInfo == null || handleInfo.getPid() <= 0) {
         model.put("errorMsg", "业务办理数据不存在，请联系管理员");
         return "error/error_page";
      }
      int projectId = handleInfo.getProjectId();
      Project project = projectClient.getProjectInfoById(projectId);
      // 检查查看用户是否有该数据的权限
      if (project.getOrgId() != loginUser.getOrgId()) {
         model.put("errorMsg", "对不起！你没有操作该数据的权限");
         return "error/error_page";
      }
      // 查询办理动态数据
      HandleDynamicDTO handleDynamicQuery = new HandleDynamicDTO();
      handleDynamicQuery.setHandleId(bizHandleId);
      List<HandleDynamicDTO> handleDynamicList = bizHandleClient.findAllHandleDynamic(handleDynamicQuery);
      Map<Long, HandleDynamicDTO> handleDynamicMap = new HashMap<Long, HandleDynamicDTO>();
      for (int i = 0; i < handleDynamicList.size(); i++) {
         HandleDynamicDTO dto = handleDynamicList.get(i);
         handleDynamicMap.put(new Long(dto.getHandleFlowId()), dto);
      }
      // 填充办理流程条目数据
      HandleFlowDTO handleFlowDTO = new HandleFlowDTO();
      List<HandleFlowDTO> handleFlowList = bizHandleClient.findAllHandleFlow(handleFlowDTO);

      model.put("handleId", bizHandleId);
      model.put("handleFlowList", handleFlowList);
      model.put("handleDynamicMap", handleDynamicMap);
      destroyFactory(bizHandleFactory);
      return PATH + "/biz_handle_show";
   }

   /**
    * 跳转到业务办理（保后监管）列表数据
    *@author:liangyanjun
    *@time:2016年8月31日下午2:14:52
    *@param query
    *@param response
    *@throws TException
    *@throws ThriftException
    */
   @RequestMapping("/bizHandlePageList")
   public void bizHandlePageList(OrgBizHandlePage query, HttpServletResponse response) throws TException, ThriftException {
      query.setApplyUserId(getDataScope());
      query.setOrgId(getLoginUser().getOrgId());
      BaseClientFactory bizHandleFactory = getFactory(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_INLOAN, "BizHandleService");
      BizHandleService.Client bizHandleClient = (Client) bizHandleFactory.getClient();
      List<OrgBizHandlePage> list = bizHandleClient.queryOrgBizHandlePage(query);
      int total = bizHandleClient.getOrgBizHandlePageTotal(query);
      outputPage(query.getRows(), response, list, total);
      destroyFactory(bizHandleFactory);
   }

   /**
    * 上传业务办理文件
    *@author:liangyanjun
    *@time:2016年8月31日下午4:36:20
    *@param request
    *@param response
    *@throws Exception
    */
   @RequestMapping("/uploadMultipartFile")
   public void uploadMultipartFile(int dynamicId, int bizHanldeId, String dynamicName, HttpServletRequest req, HttpServletResponse resp) throws Exception {
      if (!StringUtil.isBlank(dynamicName)) {
         dynamicName = URLDecoder.decode(dynamicName, "UTF-8");
      }
      Map<String, Object> resultMap = FileUtil.uploadFile(req, resp, "inloan", getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
      List<BizFile> bizFileList = (List<BizFile>) resultMap.get("bizFileList");
      if (bizFileList == null || bizFileList.isEmpty()) {
         return;
      }
      BaseClientFactory bizHandleFactory = getFactory(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_INLOAN, "BizHandleService");
      BizHandleService.Client bizHandleClient = (Client) bizHandleFactory.getClient();
      HandleDynamicFileDTO handleDynamicFileDTO=new HandleDynamicFileDTO();
      handleDynamicFileDTO.setHandleDynamicId(dynamicId);
      handleDynamicFileDTO.setRemark(dynamicName);
      for (BizFile bizFile : bizFileList) {
         bizFile.setRemark(dynamicName);
         bizHandleClient.addHandleDynamicFile(handleDynamicFileDTO, bizFile);
         recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPLOAN, "上传业务办理文件：参数"+bizFileList, req);
      }
      destroyFactory(bizHandleFactory);
   }
   /**
    * 查询办理动态文件列表
    *@author:liangyanjun
    *@time:2016年8月31日下午6:39:54
    *@param query
    *@param req
    *@param resp
    *@throws Exception
    */
   @RequestMapping("/handleDynamicFileList")
   public void handleDynamicFileList(HandleDynamicFileDTO query, HttpServletRequest req, HttpServletResponse resp) throws Exception {
      BaseClientFactory bizHandleFactory = getFactory(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_INLOAN, "BizHandleService");
      BizHandleService.Client bizHandleClient = (Client) bizHandleFactory.getClient();
      query.setStatus(Constants.STATUS_ENABLED);
      List<HandleDynamicFileDTO> list = bizHandleClient.findAllHandleDynamicFile(query);
      int total = bizHandleClient.getHandleDynamicFileTotal(query);
      outputPage(query.getRows(), resp, list, total);
      destroyFactory(bizHandleFactory);
   }
   /**
    * 删除业务办理资料文件
    *@author:liangyanjun
    *@time:2016年9月7日下午5:39:45
    *@param req
    *@param resp
    *@param fileIds
    */
   @RequestMapping("/deletehandleDynamicFile")
   public void deletehandleDynamicFile(HttpServletRequest req, HttpServletResponse resp,String fileIds) {
      if (StringUtil.isBlank(fileIds)) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }
      try {
         OrgUserInfo loginUser = getLoginUser();
         BizFileService.Client fileService = (BizFileService.Client) getService(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_SYSTEM, "BizFileService");
         BaseClientFactory bizHandleFactory = getFactory(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_INLOAN, "BizHandleService");
         BizHandleService.Client bizHandleClient = (Client) bizHandleFactory.getClient();
         String[] fileIdArr = fileIds.split(",");
         for (int i = 0; i < fileIdArr.length; i++) {
            int fileId=Integer.parseInt(fileIdArr[i]);
            //根据办理动态文件关联的fileID查询出办理动态id
            HandleDynamicFileDTO handleDynamicFileQeury=new HandleDynamicFileDTO();
            handleDynamicFileQeury.setFileId(fileId);
            handleDynamicFileQeury.setStatus(Constants.STATUS_ENABLED);
            List<HandleDynamicFileDTO> handleDynamicFileList = bizHandleClient.findAllHandleDynamicFile(handleDynamicFileQeury);
            if (handleDynamicFileList==null||handleDynamicFileList.isEmpty()) {
               fillReturnJson(resp, false, "删除失败，请重新操作!");
               return;
            }
            HandleDynamicFileDTO handleDynamicFileDTO = handleDynamicFileList.get(0);
            //根据办理动态id查询出办理动态信息
            HandleDynamicDTO handleDynamicQuery=new HandleDynamicDTO();
            handleDynamicQuery.setPid(handleDynamicFileDTO.getHandleDynamicId());
            HandleDynamicDTO handleDynamicDTO = bizHandleClient.findAllHandleDynamic(handleDynamicQuery).get(0);
            int handleId = handleDynamicDTO.getHandleId();
            int projectId = bizHandleClient.getProjectIdByHandleId(handleId);
            ProjectService.Client ProjectService = (ProjectService.Client) getService(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
            Project project = ProjectService.getProjectInfoById(projectId);
            if (project.getForeclosureStatus()==Constants.FORECLOSURE_STATUS_13) {
               fillReturnJson(resp, false, "删除失败，已归档（解保）项目!");
               return;
            }
            if (project.getOrgId()!=loginUser.getOrgId()) {
               fillReturnJson(resp, false, "删除失败，你没有权限删除!");
               return;
            }
            //查询文件主表
            BizFile bizFileQuery=new BizFile();
            bizFileQuery.setPid(fileId);
            bizFileQuery.setStatus(Constants.STATUS_ENABLED);
            List<BizFile> bizFileList = fileService.findAllBizFile(bizFileQuery);
            if (bizFileList==null||bizFileList.isEmpty()) {
               fillReturnJson(resp, false, "删除失败，请重新操作!");
               return;
            }
            //把文件的状态修改为“无效”
            BizFile updateBizFile = bizFileList.get(0);
            updateBizFile.setStatus(Constants.STATUS_DISABLED);
            //执行更新
            fileService.updateBizFile(updateBizFile);
            //记录日志
            recordLog(projectId,OrgSysLogTypeConstant.LOG_TYPE_DELETE, "删除业务办理文件：参数"+updateBizFile.fileName+"."+updateBizFile.fileType, req);
         }
         fillReturnJson(resp, true, "删除成功");
      } catch (Exception e) {
         logger.error("删除业务办理文件失败.fileIds=" + fileIds + "错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         fillReturnJson(resp, false, "删除失败，请重新操作!");
      }
   }
}
