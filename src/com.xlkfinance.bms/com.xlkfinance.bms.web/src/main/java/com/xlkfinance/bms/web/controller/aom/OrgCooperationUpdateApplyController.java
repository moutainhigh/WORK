package com.xlkfinance.bms.web.controller.aom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApply;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApplyService;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017年1月9日上午10:49:34 <br>
 * ★☆ @version： 修改机构合作信息工作流申请<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@Controller
@RequestMapping("/orgCooperationUpdateApplyController")
public class OrgCooperationUpdateApplyController extends BaseController {

    /** 跳转到修改机构合作信息工作流申请页面
     *
     * @author:liangyanjun
     * @time:2017年1月9日上午9:40:11
     * @return
     * @throws TException
     * @throws ThriftServiceException
     * @throws ThriftException */
    @RequestMapping(value = "toOrgCooperationUpdateApply")
    public String toOrgCooperationUpdateApply(Integer openType,Integer pid,Integer orgId, ModelMap model) throws ThriftServiceException, TException,
            ThriftException {
        OrgCooperatCompanyApplyInf orgCooperateInfo = new OrgCooperatCompanyApplyInf();
        OrgAssetsCooperationInfo orgAssetsInfo = new OrgAssetsCooperationInfo();
        BaseClientFactory orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
        BaseClientFactory clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
        OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) orgFactory.getClient();
        OrgAssetsCooperationService.Client assetsClient = (OrgAssetsCooperationService.Client) clientFactory.getClient();
        if (pid != null && pid > 0) {
            //申请信息
            orgCooperateInfo = client.getByPid(pid);
            orgAssetsInfo = orgCooperateInfo.getOrgAssetsInfo();
        }
        if (orgId != null && orgId > 0) {
            //机构信息
            orgAssetsInfo = assetsClient.getById(orgId);
        }
        BaseClientFactory orgCooperationUpdateApplyFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperationUpdateApplyService");
        OrgCooperationUpdateApplyService.Client orgCooperationUpdateApplyclient = (OrgCooperationUpdateApplyService.Client) orgCooperationUpdateApplyFactory
                .getClient();
        //查询正在申请的信息
        List<Integer> applyStatusList = new ArrayList<Integer>();
        applyStatusList.add(Constants.APPLY_STATUS_2);
        applyStatusList.add(Constants.APPLY_STATUS_3);
        applyStatusList.add(Constants.APPLY_STATUS_4);
        List<OrgCooperationUpdateApply> list = orgCooperationUpdateApplyclient.getByOrgIdAndApplyStatus(orgId, applyStatusList);
        if (!list.isEmpty()) {
            OrgCooperationUpdateApply cooperationUpdateApply = list.get(0);
            model.put("cooperationUpdateApply", cooperationUpdateApply);
        }
        model.put("openType", openType);
        model.put("orgCooperateInfo", orgCooperateInfo);
        model.put("orgAssetsInfo", orgAssetsInfo);
        destroyFactory(clientFactory, orgFactory);
        return "aom/org/edit_cooperate_update_apply";
    }

    @RequestMapping(value = "toOrgCooperationUpdateApplyWorkFolw")
    public String toOrgCooperationUpdateApplyWorkFolw(Integer openType,Integer pid, ModelMap model)
            throws ThriftServiceException, TException, ThriftException {
        OrgAssetsCooperationInfo orgAssetsInfo = new OrgAssetsCooperationInfo();
        BaseClientFactory orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
        BaseClientFactory clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
        OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) orgFactory.getClient();
        BaseClientFactory orgCooperationUpdateApplyFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperationUpdateApplyService");
        OrgCooperationUpdateApplyService.Client orgCooperationUpdateApplyclient = (OrgCooperationUpdateApplyService.Client) orgCooperationUpdateApplyFactory
                .getClient();
        //查询正在申请的信息
        OrgCooperationUpdateApply curentApply = orgCooperationUpdateApplyclient.getById(pid);
        int orgId = curentApply.getOrgId();
        //申请信息
        OrgCooperatCompanyApplyInf orgCooperateInfo = client.getByOrgId(orgId);
        //机构信息
        orgAssetsInfo = orgCooperateInfo.getOrgAssetsInfo();
        model.put("openType", openType);
        model.put("cooperationUpdateApply", curentApply);
        model.put("orgCooperateInfo", orgCooperateInfo);
        model.put("orgAssetsInfo", orgAssetsInfo);
        destroyFactory(clientFactory, orgFactory);
        return "aom/org/edit_cooperate_update_apply";
    }

    /** @author:liangyanjun
     * @time:2017年1月9日上午11:14:35
     * @param orgCooperationUpdateApply
     * @param response
     * @param req
     * @throws ThriftException
     * @throws TException */
    @RequestMapping(value = "saveOrgCooperationUpdateApply")
    public void saveOrgCooperationUpdateApply(OrgCooperationUpdateApply orgCooperationUpdateApply, HttpServletResponse response,
            HttpServletRequest req) throws ThriftException, TException {
        int orgId = orgCooperationUpdateApply.getOrgId();//机构id
        double creditLimit = orgCooperationUpdateApply.getCreditLimit();//授信额度
        orgCooperationUpdateApply.getActivateCreditLimit();
        double planRate = orgCooperationUpdateApply.getPlanRate();//预收费费率
        double fundSizeMoney = orgCooperationUpdateApply.getFundSizeMoney();//出款标准
        double assureMoneyProportion = orgCooperationUpdateApply.getAssureMoneyProportion();//保证金比例
        double assureMoney = orgCooperationUpdateApply.getAssureMoney();//保证金
        double actualFeeRate = orgCooperationUpdateApply.getActualFeeRate();//实际收费费率
        double singleUpperLimit = orgCooperationUpdateApply.getSingleUpperLimit();//单笔上限
        String remark = orgCooperationUpdateApply.getRemark();//修改原因
        if (orgId <= 0 || creditLimit <= 0 || planRate <= 0 || fundSizeMoney < 0 || assureMoneyProportion <= 0
                || assureMoney <= 0 || actualFeeRate <= 0 || singleUpperLimit <= 0) {
            fillReturnJson(response, false, "提交失败,参数不合法");
            return;
        }
        ShiroUser shiroUser = getShiroUser();
        Integer userId = shiroUser.getPid();
        Map<String, Object> result = new HashMap<String, Object>();
        BaseClientFactory orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
        OrgCooperatCompanyApplyService.Client  companyApplyClient= (OrgCooperatCompanyApplyService.Client) orgFactory.getClient();
        //根据机构id查询合作申请信息
        OrgCooperatCompanyApplyInf companyApplyInf = companyApplyClient.getByOrgId(orgId);
        if (companyApplyInf.getApplyStatus()!=AomConstant.ORG_COOPERATE_APPLY_STATUS_4) {
            fillReturnJson(response, false, "提交失败,合作申请状态未通过，不能申请修改数据");
            return;
        }
        if (creditLimit<companyApplyInf.getAvailableLimit()) {
            fillReturnJson(response, false, "提交失败,可用授信额度必须小于授信额度");
            return;
        }
       /* if (companyApplyInf.getRealAssureMoney()!=0&&activateCreditLimit!=0) {
            fillReturnJson(response, false, "提交失败,已收取保证金则不能修改启用授信额度");
            return;
        }*/
         if (companyApplyInf.getRealAssureMoney()>assureMoney) {
            fillReturnJson(response, false, "提交失败,保证金金额必须大于等于实收保证金");
            return;
        }
        if (assureMoney>=creditLimit) {
            fillReturnJson(response, false, "提交失败,保证金金额必须小于授信额度");
            return;
        }
        BaseClientFactory orgCooperationUpdateApplyFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperationUpdateApplyService");
        OrgCooperationUpdateApplyService.Client orgCooperationUpdateApplyclient = (OrgCooperationUpdateApplyService.Client) orgCooperationUpdateApplyFactory
                .getClient();
        //start 查询是否存在申请中的修改工作流
        List<Integer> applyStatusList = new ArrayList<Integer>();
        applyStatusList.add(Constants.APPLY_STATUS_2);
        applyStatusList.add(Constants.APPLY_STATUS_3);
        applyStatusList.add(Constants.APPLY_STATUS_4);
        List<OrgCooperationUpdateApply> list = orgCooperationUpdateApplyclient.getByOrgIdAndApplyStatus(orgId, applyStatusList);
        if (!list.isEmpty()) {
            OrgCooperationUpdateApply updateCooperationUpdateApply = list.get(0);
            int applyStatus = updateCooperationUpdateApply.getApplyStatus();
            if (applyStatus == Constants.APPLY_STATUS_2||applyStatus == Constants.APPLY_STATUS_3) {
                updateCooperationUpdateApply.setCreditLimit(orgCooperationUpdateApply.getCreditLimit());//授信额度
                updateCooperationUpdateApply.setActivateCreditLimit(orgCooperationUpdateApply.getActivateCreditLimit());//启用授信额度
                updateCooperationUpdateApply.setPlanRate(orgCooperationUpdateApply.getPlanRate());//预收费费率
                updateCooperationUpdateApply.setFundSizeMoney(orgCooperationUpdateApply.getFundSizeMoney());//出款标准
                updateCooperationUpdateApply.setAssureMoneyProportion(orgCooperationUpdateApply.getAssureMoneyProportion());//保证金比例
                updateCooperationUpdateApply.setAssureMoney(orgCooperationUpdateApply.getAssureMoney());//保证金
                updateCooperationUpdateApply.setSingleUpperLimit(orgCooperationUpdateApply.getSingleUpperLimit());//单笔上限
                updateCooperationUpdateApply.setAssureMoneyRemark(orgCooperationUpdateApply.getAssureMoneyRemark());//保证金备注
                updateCooperationUpdateApply.setRemark(remark);
                updateCooperationUpdateApply.setCreaterId(userId);
                updateCooperationUpdateApply.setUpdateId(userId);
                orgCooperationUpdateApplyclient.update(updateCooperationUpdateApply);
                result.put("pid", updateCooperationUpdateApply.getPid());
            } else if(applyStatus == Constants.APPLY_STATUS_4){
                fillReturnJson(response, false, "提交失败,该项目已经存在正在申请的流程");
                return;
            }
            fillReturnJson(response, true, "提交成功", result);
            return;
        }
        //end 查询是否存在申请中的修改工作流
        orgCooperationUpdateApply.setApplyStatus(Constants.APPLY_STATUS_2);
        orgCooperationUpdateApply.setCreaterId(userId);
        orgCooperationUpdateApply.setUpdateId(userId);
        int pid = orgCooperationUpdateApplyclient.insert(orgCooperationUpdateApply);
        result.put("pid", pid);
        fillReturnJson(response, true, "提交成功", result);
        recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "保存机构合作信息修改申请：参数：" + orgCooperationUpdateApply, req);
        fillReturnJson(response, true, "提交成功");
    }

    /** 根据机构id查询最后一次申请的审批状态
     *
     * @author:liangyanjun
     * @time:2017年1月9日下午4:54:31
     * @param orgId
     * @return */
    @RequestMapping(value = "/getOrgCooperationUpdateApplyStatus", method = RequestMethod.POST)
    @ResponseBody
    public int getOrgCooperationUpdateApplyStatus(int orgId) {
        try {
            //
            BaseClientFactory orgCooperationUpdateApplyFactory = getAomFactory(BusinessModule.MODUEL_ORG,
                    "OrgCooperationUpdateApplyService");
            OrgCooperationUpdateApplyService.Client orgCooperationUpdateApplyclient = (OrgCooperationUpdateApplyService.Client) orgCooperationUpdateApplyFactory
                    .getClient();
            OrgCooperationUpdateApply cooperationUpdateApply = orgCooperationUpdateApplyclient.getLastByOrgId(orgId);
            int applyStatus = cooperationUpdateApply.getApplyStatus();
            if (applyStatus == 0) {//等于0说明没有申请
                applyStatus = 1;
            }
            return applyStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    /**
     * 审批历史记录（分页查询）
     *@author:liangyanjun
     *@time:2017年1月10日下午5:18:10
     *@param query
     *@param response
     */
    @RequestMapping(value = "/applyHisList")
    public void applyHisList(OrgCooperationUpdateApply query,HttpServletResponse response) {
       Map<String, Object> map = new HashMap<String, Object>();
       List<OrgCooperationUpdateApply> list = null;
       int total = 0;
       try {
          // 查询查档历史列表
           BaseClientFactory orgCooperationUpdateApplyFactory = getAomFactory(BusinessModule.MODUEL_ORG,
                   "OrgCooperationUpdateApplyService");
           OrgCooperationUpdateApplyService.Client orgCooperationUpdateApplyclient = (OrgCooperationUpdateApplyService.Client) orgCooperationUpdateApplyFactory
                   .getClient();
          list = orgCooperationUpdateApplyclient.queryApplyHisIndex(query);
          total = orgCooperationUpdateApplyclient.getApplyHisIndexTotal(query);
       } catch (Exception e) {
          e.printStackTrace();
       }
       // 输出
       map.put("rows", list);
       map.put("total", total);
       outputJson(map, response);
    }
}
