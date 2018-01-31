package com.xlkfinance.bms.web.controller.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
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

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.SysAppVersionInfo;
import com.xlkfinance.bms.rpc.system.SysAppVersionInfoService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.FileUtil;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年11月1日上午9:56:06 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@Controller
@RequestMapping("/sysAppVersionInfoController")
public class SysAppVersionInfoController extends BaseController {
	private static final String PAGE_PATH = "system/";
    private Logger logger = LoggerFactory.getLogger(SysAppVersionInfoController.class);
    /**
     * 上传app版本文件
     *@author:liangyanjun
     *@time:2016年11月1日下午4:56:05
     *@param appId
     *@param req
     *@param resp
     *@throws ServletException
     *@throws IOException
     *@throws ThriftException
     *@throws ThriftServiceException
     *@throws TException
     */
    @RequestMapping(value = "/uploadFile")
    public void uploadFile(int appId,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ThriftException, ThriftServiceException, TException {
        try {
            Map<String, Object> resultMap = FileUtil.uploadFile2(req, resp, "system", getAomUploadFilePath(), getFileSize(),
                    getFileDateDirectory(), getUploadFileType());
            List<BizFile> bizFileList = (List<BizFile>) resultMap.get("bizFileList");
            if (bizFileList == null || bizFileList.isEmpty()) {
                return;
            }
            BizFile bizFile = bizFileList.get(0);
            BaseClientFactory bizFileFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
            BizFileService.Client bizFileClient = (BizFileService.Client) bizFileFactory.getClient();
            BaseClientFactory appFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAppVersionInfoService");
            SysAppVersionInfoService.Client appClient = (SysAppVersionInfoService.Client) appFactory.getClient();
            int fileId = bizFileClient.saveBizFile(bizFile);
            SysAppVersionInfo updateApp = appClient.getById(appId);
            updateApp.setFileId(fileId);
            appClient.update(updateApp);
            destroyFactory(bizFileFactory, appFactory);
            fillReturnJson(resp, true, "提交成功");
        } catch (Exception e) {
            fillReturnJson(resp, false, "提交失败,请联系管理员!");
            logger.error("上传app版本文件失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + appId);
            e.printStackTrace();
        }
    }
    /**
     * 更新或新增app版本信息
     *@author:liangyanjun
     *@time:2016年11月1日上午10:45:48`
     *@param appVersionInfo
     *@param req
     *@param resp
     */
    @RequestMapping(value = "/addOrUpdate")
    public void addOrUpdate(SysAppVersionInfo appVersionInfo,HttpServletRequest req, HttpServletResponse resp){
        int pid = appVersionInfo.getPid();
        String appName = appVersionInfo.getAppName();
        String appDescription = appVersionInfo.getAppDescription();
        String appVersion = appVersionInfo.getAppVersion();
        int status = appVersionInfo.getStatus();
        int coercivenessUpgradesStatus = appVersionInfo.getCoercivenessUpgradesStatus();
        int systemPlatform = appVersionInfo.getSystemPlatform();
        int appCategory = appVersionInfo.getAppCategory();
        if (StringUtil.isBlank(appName,appVersion)||systemPlatform<=0||appCategory<=0) {
            fillReturnJson(resp, false, "提交失败,数据不合法!");
            return;
        }
        try {
            BaseClientFactory factory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAppVersionInfoService");
            SysAppVersionInfoService.Client client = (SysAppVersionInfoService.Client) factory.getClient();
            Integer userId = getShiroUser().getPid();
            appVersionInfo.setUpdateId(userId);
            if (pid <= 0) {
                appVersionInfo.setCreaterId(userId);
                client.insert(appVersionInfo);
            }else{
                SysAppVersionInfo updateVersionInfo = client.getById(pid);
                updateVersionInfo.setAppName(appName);
                updateVersionInfo.setAppDescription(appDescription);
                updateVersionInfo.setAppVersion(appVersion);
                updateVersionInfo.setStatus(status);
                updateVersionInfo.setSystemPlatform(systemPlatform);
                updateVersionInfo.setCoercivenessUpgradesStatus(coercivenessUpgradesStatus);
                updateVersionInfo.setAppCategory(appCategory);
                client.update(updateVersionInfo);
            }
            destroyFactory(factory);
            fillReturnJson(resp, true, "提交成功");
        } catch (Exception e) {
            fillReturnJson(resp, false, "提交失败,请联系管理员!");
            logger.error("更新或新增app版本信息失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + appVersionInfo);
            e.printStackTrace();
        }
    }

    /**
     * 首页跳转
     */
    @RequestMapping(value = "/index")
    public String toIndex(ModelMap model) {
       return PAGE_PATH + "app_version_index";
    }
    /**
     * 
     *@author:liangyanjun
     *@time:2016年11月1日上午11:03:31
     *@param model
     *@param pid
     *@return
     *@throws ThriftException
     *@throws TException
     */
    @RequestMapping(value = "/toAddOrUpdate")
    public String toAddOrUpdate(ModelMap model,int pid) throws ThriftException, TException {
        if (pid>0) {
            BaseClientFactory appFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAppVersionInfoService");
            SysAppVersionInfoService.Client appClient = (SysAppVersionInfoService.Client) appFactory.getClient();
            SysAppVersionInfo appVersionInfo = appClient.getById(pid);
            model.put("appVersionInfo", appVersionInfo);
        }
        return PAGE_PATH + "app_version_addOrUpdate";
    }
    
    /**
     * 分页获取版本列表
     * @param query
     * @param resp
     */
    @RequestMapping(value = "getAppList" , method=RequestMethod.POST)
	@ResponseBody
    public void getAppList(SysAppVersionInfo query,HttpServletResponse resp){
    	Map<String, Object> map = new HashMap<String,Object>();
		List<SysAppVersionInfo> result = new ArrayList<SysAppVersionInfo>();
		int total = 0;
		BaseClientFactory factory = null;
		try {
			factory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAppVersionInfoService");
			SysAppVersionInfoService.Client client = (SysAppVersionInfoService.Client) factory.getClient();
			result = client.querySysAppVersionInfo(query);
			total = client.getSysAppVersionInfoTotal(query);
		} catch (Exception e) {
			logger .error("分页获取App版本信息：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}finally{
			destroyFactory(factory);
		}
		map.put("rows", result);
		map.put("total", total);
		// 输出
		outputJson(map, resp);
    }
}
