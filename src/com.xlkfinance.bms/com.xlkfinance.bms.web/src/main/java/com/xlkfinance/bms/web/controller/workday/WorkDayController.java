package com.xlkfinance.bms.web.controller.workday;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.workday.WorkDay;
import com.xlkfinance.bms.rpc.workday.WorkDayService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.rpc.workday.WorkDayService.Client;
/**
 * @author： Jony <br>
 * @time：2017年7月1日下午3:47:00 <br>
 */
@Controller
@RequestMapping("/workDayController")
public class WorkDayController extends BaseController {
	private static final String financeHandle = "workday/";
	private Logger logger = LoggerFactory.getLogger(WorkDayController.class);
	private final String serviceName = "WorkDayService";

	/**
	 * 法定节假日首页跳转
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap model) {
		return financeHandle + "index";
	}
	/**
	 * 法定节假日查询
	 * @author:Jony
	 * @time:2017年7月12日下午3:13:45
	 * @param workDay
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/workDayList")
	@ResponseBody
	public void workDayList(WorkDay workDay,HttpServletRequest request, HttpServletResponse response) {
		if (workDay == null) workDay = new WorkDay();
		Map<String, Object> map = new HashMap<String, Object>();
		List<WorkDay> workDayList = null;
		int total = 0;
		try {
			// 查询法定节假日表
	        WorkDayService.Client service = (Client)getService(BusinessModule.MODUEL_WORK_DAY, serviceName);
	        workDayList = service.getWorkDay(workDay);
			total = service.geWorkDayTotal(workDay);
			logger.info("法定节假日查询列表查询成功：total：" + total + ",list:" + workDayList);
		} catch (Exception e) {
			logger.error("获取法定节假日列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + workDay);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", workDayList);
		map.put("total", total);
		outputJson(map, response);
	}
	/**
	 * 新增or修改法定节假日
	 * @author:Jony
	 * @time:2017年7月12日下午3:13:45
	 * @param workDay
	 * @param request
	 * @param response
	 */
	 @RequestMapping(value = "/saveWorkDayInfo", method = RequestMethod.POST)
	   public void saveWorkDayInfo(WorkDay workDay, HttpServletRequest request, HttpServletResponse response) {
		logger.info("保存节假日，参数"+workDay);
		Json j = super.getSuccessObj();
		if(workDay==null) workDay = new WorkDay();
		//获取新增或者修改的法定节假日日期
		String correctDate = workDay.getCorrectDate();
		int correctYear = 2017;
		
		//创建或者修改人Id
	    ShiroUser shiroUser = getShiroUser();
	    Integer userId = shiroUser.getPid();
	    
	    //根据选择的法定假日日期截取年份
	    correctYear = Integer.parseInt(workDay.getCorrectDate().substring(0, 4));
	    
	    int pid = workDay.getPid();
	    List<WorkDay> workDayList = null;
	    List<String> listCorrectDate = new ArrayList<String>();
			try {
				WorkDayService.Client service = (Client)getService(BusinessModule.MODUEL_WORK_DAY, serviceName);
			    workDayList = service.getWorkDay(workDay);
			    
			   //获取整个法定假日的日期
			    if(workDayList !=null && workDayList.size() >0){
					for(int i=0;i<workDayList.size();i++){
						listCorrectDate.add(workDayList.get(i).getCorrectDate());
					}
				}
			    
			    //判断新增或者修改的日期是不是已经存在，存在则不给于保存
			    if(listCorrectDate.contains(correctDate)){
						j.getHeader().put("success", false);
						j.getHeader().put("msg", "保存失败,已存在该日期，请重新选择!");
						// 输出
						outputJson(j, response);
						return;
				}
				//pid>0为修改
				  if(pid > 0){
					  workDay.setCorrectYear(correctYear);
					  workDay.setUpdateId(userId);
					  // 调用修改方法
					  service.update(workDay);
						j.getHeader().put("success", true);
						j.getHeader().put("msg", "修改成功！");
			   	   //新增
			   	    }else{
			   	    	workDay.setCorrectYear(correctYear);
			   	    	//1=是 2=否
			   	    	int isHolidays = workDay.getIsHolidays();
			   	    	workDay.setIsHolidays(isHolidays);
			   	    	workDay.setCreateId(userId);
						// 调用新增方法
						service.insert(workDay);
						logger.info("插入节假日成功，参数"+workDay);
						j.getHeader().put("success", true);
						j.getHeader().put("msg", "新增成功！");
			   	      }
			} catch (ThriftServiceException tse) {
				tse.printStackTrace();
				j.getHeader().put("success", false);
				j.getHeader().put("msg", tse.message);
			} catch (Exception e) {
				logger.error("新增or修改法定节假日失败:" + ExceptionUtil.getExceptionMessage(e));
				e.printStackTrace();
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "新增or修改失败,请重新操作！");
			}
			outputJson(j, response);
	  }
	 
		/**
		 * 删除法定信息表
		  * @param pid
		  * @param response
		  * @author: jony
		  * @date: 2017年7月04日 上午9:30:52
		 */
		@RequestMapping(value="deleteById")
		public void deleteById(WorkDay workDay ,HttpServletResponse response,HttpServletRequest req){
			logger.info("删除法定节假日信息表，参数：" + workDay.getPid());
			Json j = super.getSuccessObj();
			WorkDayService.Client service = (Client)getService(BusinessModule.MODUEL_WORK_DAY, serviceName);			 
			int pid = workDay.getPid();
			try {
				//执行删除
				service.deleteById(pid);
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "删除成功!");
			} catch (Exception e) {
				logger.error("删除法定节假日信息:" + ExceptionUtil.getExceptionMessage(e));
				e.printStackTrace();
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "删除失败,请重新操作!");
			}
			// 输出
			outputJson(j, response);
		}
}
