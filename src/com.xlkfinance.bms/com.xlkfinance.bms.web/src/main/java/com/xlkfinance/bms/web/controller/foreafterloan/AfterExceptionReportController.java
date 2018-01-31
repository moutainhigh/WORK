package com.xlkfinance.bms.web.controller.foreafterloan;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.foreafterloan.AfterExceptionReport;
import com.xlkfinance.bms.rpc.foreafterloan.AfterExceptionReportService;
import com.xlkfinance.bms.rpc.foreafterloan.ExceptionMonitorIndex;
import com.xlkfinance.bms.rpc.foreafterloan.ForeAfterLoanService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017年1月11日下午2:28:47 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 赎楼贷后模块<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@Controller
@RequestMapping("/afterExceptionReportController")
public class AfterExceptionReportController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AfterExceptionReportController.class);

    /** 贷后异常上报列表(分页查询)
     *
     * @author:liangyanjun
     * @time:2017年1月16日上午11:36:26
     * @param query
     * @param request
     * @param response
     * @throws TException
     * @throws ThriftException */
    @RequestMapping(value = "/afterExceptionReportList")
    public void afterExceptionReportList(AfterExceptionReport query, HttpServletRequest request, HttpServletResponse response)
            throws TException, ThriftException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<AfterExceptionReport> list = null;
        int total = 0;
        BaseClientFactory factory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN, "AfterExceptionReportService");
        AfterExceptionReportService.Client client = (AfterExceptionReportService.Client) factory.getClient();
        list = client.queryAfterExceptionReportHisList(query);
        total = client.getAfterExceptionReportHisTotal(query);
        // 输出
        map.put("rows", list);
        map.put("total", total);
        outputJson(map, response);
        destroyFactory(factory);
    }
    
    /**
     * 保存异常上报信息
     *@author:liangyanjun
     *@time:2017年1月16日下午2:31:42
     *@param exceptionReport
     *@param request
     *@param response
     */
    @RequestMapping(value = "/saveAfterExceptionReport")
    public void saveAfterExceptionReport(AfterExceptionReport exceptionReport, HttpServletRequest request, HttpServletResponse response){
        int projectId = exceptionReport.getProjectId();//项目ID
        int exceptionType = exceptionReport.getExceptionType();//异常类型：1=展期，2=逾期，3=房产查封，4=新增诉讼，100=其他
        if (projectId<=0|exceptionType<=0) {
            logger.error("请求数据不合法：" + exceptionReport);
            fillReturnJson(response, false, "提交失败,输入必填项");
            return;
         }
        ShiroUser shiroUser = getShiroUser();
        Integer userId = shiroUser.getPid();
        try {
            BaseClientFactory factory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN, "AfterExceptionReportService");
            AfterExceptionReportService.Client client = (AfterExceptionReportService.Client) factory.getClient();
            BaseClientFactory projectFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
            ProjectService.Client projectClient = (ProjectService.Client) projectFactory.getClient();
            Project project = projectClient.getProjectInfoById(projectId);
            //保存异常上报信息
            exceptionReport.setMonitorDate(DateUtils.getCurrentDateTime());
            exceptionReport.setCreaterId(userId);
            exceptionReport.setUpdateId(userId);
            client.insert(exceptionReport);
            //更新项目为异常状态
            projectClient.updateForeAfterMonitorStatus(projectId, Constants.FORE_AFTER_MONITOR_STATUS_2);
            //发送通知
            sendNoticeMsg(exceptionReport,project);
            fillReturnJson(response, true, "提交成功");
            destroyFactory(factory,projectFactory);
        } catch (Exception e) {
            logger.error("保存异常上报信息失败：入参：exceptionReport" + exceptionReport + "。错误：" + ExceptionUtil.getExceptionMessage(e));
            fillReturnJson(response, false, "提交异常,请联系管理员!");
            return;
        }
    }
    /**
     * 查询贷后异常通知用户(发短信和发邮件)
     *@author:liangyanjun
     *@time:2017年1月17日上午10:00:42
     *@return
     *@throws ThriftException
     *@throws TException
     */
    private void sendNoticeMsg(AfterExceptionReport exceptionReport,Project project) throws ThriftException, TException {
        String projectName = project.getProjectName();
        int pmUserId = project.getPmUserId();
        String noticeWayStr = exceptionReport.getNoticeWay();
        String remark = exceptionReport.getRemark();
        int monitorUserId = exceptionReport.getMonitorUserId();
        if (StringUtil.isBlank(noticeWayStr)) {
            return;
        }
        List<String> noticeWays = Arrays.asList(noticeWayStr.split(","));
        //查询贷后异常通知用户
        List<SysLookupVal> lookupVals = getSysLookupValByLookType("AFTER_EXCEPTION_REPORT_NOTICE_ROLE");
        BaseClientFactory factory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
        SysUserService.Client client = (SysUserService.Client) factory.getClient();
        
        BaseClientFactory workflowServiceFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
        WorkflowService.Client workflowService = (WorkflowService.Client) workflowServiceFactory.getClient();
        
        SysUser pmUser = client.getUserDetailsByPid(pmUserId);
        SysUser monitorUser = client.getUserDetailsByPid(monitorUserId);
        Map<String, Object> map=new HashMap<String, Object>();
        Map<Integer, SysUser> sendMsgUserMap=new HashMap<Integer, SysUser>();
        map.put("projectName", projectName);
        map.put("remark", remark);
        map.put("monitorUserName", monitorUser.getRealName());
        String maillText = templateParsing(map, "afterExceptionReportEmail.ftl");
        String smsText = templateParsing(map, "afterExceptionReportSms.ftl");
        sendMsgUserMap.put(monitorUserId, monitorUser);
        sendMsgUserMap.put(pmUser.getPid(), pmUser);//添加发送给经办人
        //发送给需要通知的角色用户
        for (SysLookupVal v : lookupVals) {
            String roleCode = v.getLookupVal();
            System.out.println(roleCode);
            List<SysUser> users = workflowService.findBizUserByRoleCode(pmUser, roleCode);
            for (SysUser sysUser : users) {
                sendMsgUserMap.put(sysUser.getPid(), sysUser);
            }
        }
        //遍历用户发送信息
        Set<Integer> keySet = sendMsgUserMap.keySet();
        for (Integer userId : keySet) {
            sendMsg(noticeWays, maillText, smsText, sendMsgUserMap.get(userId));
        }
        destroyFactory(factory,workflowServiceFactory);
    }

    private void sendMsg(List<String> noticeWays, String maillText, String smsText, SysUser sysUser) {
        System.out.println(sysUser.getRealName());
        String phone = sysUser.getPhone();
        String mail = sysUser.getMail();
        if (!StringUtil.isBlank(phone)&&noticeWays.contains(Constants.FORE_AFTER_NOTICE_WAY_SMS)) {
           sendSms(phone, smsText);
        } 
        if(!StringUtil.isBlank(mail)&&noticeWays.contains(Constants.FORE_AFTER_NOTICE_WAY_MAIL)){
            sendMail(mail, "贷后异常监控", maillText);
        }
    }
    
    /** 异常监控列表（分页查询）
    *
    * @author:jony
    * @time:2017年1月12日下午2:17:43
    * @param query
    * @param request
    * @param response
    * @throws TException
    * @throws ThriftException */
   @RequestMapping(value = "/toExceptionTransitIndexList")
   public void toExceptionTransitIndexList(ExceptionMonitorIndex query, HttpServletRequest request, HttpServletResponse response)
           throws TException, ThriftException {
       Map<String, Object> map = new HashMap<String, Object>();
       List<ExceptionMonitorIndex> list = null;
       int total = 0;
       List<Integer> userIds = getUserIds(request);
       query.setUserIds(userIds);
       BaseClientFactory factory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN, "ForeAfterLoanService");
       ForeAfterLoanService.Client client = (ForeAfterLoanService.Client) factory.getClient();
       list = client.queryExceptionMonitorIndex(query);
       total = client.getExceptionMonitorIndexTotal(query);
       // 输出
       map.put("rows", list);
       map.put("total", total);
       outputJson(map, response);
       destroyFactory(factory);
   }
   
    
}
