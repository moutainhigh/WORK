/**
 * @Title: ElementLendController.java
 * @Package com.xlkfinance.bms.web.controller.inloan
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年2月26日 下午8:35:37
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.inloan;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.beforeloan.ElementLend;
import com.xlkfinance.bms.rpc.beforeloan.ElementLendDetails;
import com.xlkfinance.bms.rpc.beforeloan.ElementLendService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.inloan.CollectFileDTO;
import com.xlkfinance.bms.rpc.inloan.CollectFileMap;
import com.xlkfinance.bms.rpc.inloan.CollectFilePrintInfo;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService.Client;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;
import com.xlkfinance.bms.rpc.system.SysOrgInfoService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfo;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfoService;
import com.xlkfinance.bms.web.constant.Constants;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

/**
 * 要件借出申请Controller
 * 
 * @ClassName: ElementLendController
 * @author: Administrator
 * @date: 2016年2月26日 下午8:37:42
 */
@Controller
@RequestMapping("/elementLendController")
public class ElementLendController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ElementLendController.class);


	/**
	 * 要件借出查询跳转
	 */
	@RequestMapping(value = "/index")
	public String toIndex(ModelMap model) {
		return "inloan/elementLend/index";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public void list(ElementLend elementLend, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GridViewDTO> list = null;
		int total = 0;
		// 设置数据权限--用户编号list
		elementLend.setUserIds(getUserIds(request));
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
			ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();

			list = client.getAllElementLend(elementLend);
			total = client.getAllElementLendCount(elementLend);
			logger.info("要件借出查询列表查询成功：total：" + total + ",list:" + list);
		} catch (Exception e) {
			logger.error("获取要件借出列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + elementLend);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", list);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	 * 编辑要件借出页面
	 * 
	 * @Description:
	 * @param pid
	 * @param response
	 * @param model
	 * @author: andrew
	 * @date: 2016年2月26日 下午11:52:15
	 */
	@RequestMapping(value = "/editElementLend")
	public String editElementLend(@RequestParam(value = "pid", required = false) Integer pid,@RequestParam(value = "status", required = false) Integer status, HttpServletResponse response, ModelMap model) {
		ElementLend elementLend = new ElementLend();
		BaseClientFactory clientFactory = null;
		BaseClientFactory clientFactoryOrg = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
			ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();
			if (pid != null && pid > 0) {
				elementLend = client.getElementLendById(pid);
			}else{
				elementLend.setLendUserId(getShiroUser().getPid());
				elementLend.setRealName(getShiroUser().getRealName());
				int orgId = getSysUser().getOrgId();
				elementLend.setOrgId(orgId);
				if(clientFactoryOrg == null){
					clientFactoryOrg = this.getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
				}
				SysOrgInfoService.Client clientOrg = (SysOrgInfoService.Client) clientFactoryOrg.getClient();
				
				//获取登录用所属机构
				SysOrgInfo loginUserOrg = clientOrg.getSysOrgInfo(orgId);
				elementLend.setOrgName(loginUserOrg.getName());
			}
		} catch (Exception e) {
			logger.error("获取要件借出详情失败：" + e.getMessage());
			e.printStackTrace();
		}
		model.put("elementLend", elementLend);
		
		String url = "inloan/elementLend/edit_element";
		if(status == 1){
			url = "inloan/elementLend/edit_element";
		}else if(status == 2){
			url = "inloan/elementLend/show_element";
		}else if(status == 3){
			url = "inloan/elementLend/return_element";
		}else if(status == 4){
			url = "inloan/elementLend/sign_element";
		}
		
		return url;
	}
	
	/**
	 * 跳转到新增要件借出页面
	 * 
	 * @Description:
	 * @param pid
	 * @param response
	 * @param model
	 * @author: andrew
	 * @date: 2016年2月26日 下午11:52:15
	 */
	@RequestMapping(value = "/addElementLend")
	public String addElementLend(@RequestParam(value = "projectId", required = false) Integer projectId, HttpServletResponse response, ModelMap model) {
		ElementLend elementLend = new ElementLend();
		BaseClientFactory clientFactoryOrg = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getLoanProjectByProjectId(projectId);
			//项目基本信息
			elementLend.setProjectName(project.getProjectName());
			elementLend.setLendUserId(getShiroUser().getPid());
			elementLend.setRealName(getShiroUser().getRealName());
			int orgId = getSysUser().getOrgId();
			elementLend.setOrgId(orgId);
			elementLend.setProjectId(projectId);
			if(clientFactoryOrg == null){
				clientFactoryOrg = this.getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
			}
			SysOrgInfoService.Client clientOrg = (SysOrgInfoService.Client) clientFactoryOrg.getClient();
			
			//获取登录用所属机构
			SysOrgInfo loginUserOrg = clientOrg.getSysOrgInfo(orgId);
			elementLend.setOrgName(loginUserOrg.getName());

		} catch (Exception e) {
			logger.error("获取要件借出详情失败：" + e.getMessage());
			e.printStackTrace();
		}
		model.put("elementLend", elementLend);
		return  "inloan/elementLend/edit_element";
	}
	
	/**
	 * 保存要件借出信息
	 * 
	 * @param elementLend
	 * @param response
	 * @author: andrew
	 * @date: 2016年2月27日 上午12:01:10
	 */
	@RequestMapping(value = "/saveElementLend", method = RequestMethod.POST)
	public void saveElementLend(ElementLend elementLend, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		int pid = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
			ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();
			pid = elementLend.getPid();
			if (pid > 0) {// 修改要件
				elementLend.setUpdateTime(DateUtil.format(new Date()));
				int rows = client.updateElementLend(elementLend);
				if (rows != 0) {
					j.getHeader().put("success", true);
					// 成功的话,就做业务日志记录
					recordLog(BusinessModule.MODUEL_ELEMENT, SysLogTypeConstant.LOG_TYPE_UPDATE, "保存要件借出信息,编号:" + elementLend.getPid(),elementLend.getProjectId());
				} else {
					// 失败的话,做提示处理
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "修改失败,请重新操作!");
				}
			} else {// 新增要件信息
				elementLend.setLendUserId(getShiroUser().getPid());
				elementLend.setLendState(1);// 申请中
				// 获取系统时间,并设置
				Timestamp time = new Timestamp(new Date().getTime());
				elementLend.setUpdateTime(time.toString());

				pid = client.addElementLend(elementLend);
				j.getHeader().put("success", true);
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("新增要件借出信息:" + e.getMessage());
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		}
		j.getHeader().put("pid", pid);
		outputJson(j, response);
	}

	/**
	 * 获取项目列表
	 * 
	 * @Description:
	 * @param request
	 * @param response
	 * @author: andrew
	 * @date: 2016年2月27日 上午12:34:26
	 */
	@RequestMapping(value = "/getProjectList")
	public void getProjectList(HttpServletRequest request, HttpServletResponse response) {
		List<Project> resultList = new ArrayList<Project>();
		Project project = new Project();
		project.setPid(-1);
		project.setProjectName("--请选择--");
		resultList.add(project);
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project condition = new Project();
			condition.setUserIds(getUserIds(request));
			resultList.addAll(client.findProjectInfo(condition));
		} catch (Exception e) {
			logger.error("获取项目列表失败：" + e.getMessage());
			e.printStackTrace();
		}
		outputJson(resultList, response);
	}
	
	
	/**
	 * 通过项目ID查询产品信息
	  * @Description: TODO
	  * @param projectId
	  * @param response
	  * @author: andrew
	  * @date: 2016年2月27日 下午2:24:50
	 */
	@RequestMapping(value = "/getProductInfo")
	public void getProductInfo(Integer projectId, HttpServletResponse response) {
		Product product = new Product();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PRODUCT, "ProductService");
			ProductService.Client client = (ProductService.Client) clientFactory.getClient();
			product = client.findProductByProjectId(projectId);

		} catch (Exception e) {
			logger.error("通过项目ID查询产品信息：" + e.getMessage());
			e.printStackTrace();
		}
		outputJson(product, response);
	}

	/**
	 * 查询项目相关上传
	  * @Description: TODO
	  * @param projectId
	  * @param response
	  * @author: andrew
	  * @date: 2016年2月27日 下午2:53:58
	 */
	@RequestMapping(value = "/getProjectFileList")
	public void getProjectFileList(Integer projectId, HttpServletResponse response){
		List<DataInfo> fileList = new ArrayList<DataInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
			ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();
			fileList = client.findProjectFiles(projectId);
		}catch (Exception e) {
			logger.error("查询项目相关要件：" + e.getMessage());
			e.printStackTrace();
		}
		outputJson(fileList, response);
	}
	
	/**
	 * 	查询已借出的要件列表
	  * @Description: TODO
	  * @param pid
	  * @param response
	  * @author: andrew
	  * @date: 2016年2月28日 上午9:45:32
	 */
	@RequestMapping(value = "/getLendFileList")
	public void getLendFileList(Integer pid, HttpServletResponse response){
		
		List<ElementLendDetails> lendFileList = new ArrayList<ElementLendDetails>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
			ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();
			ElementLendDetails details = new ElementLendDetails();
			details.setLendId(pid);
			lendFileList = client.queryElementLendDetails(details );
		}catch (Exception e) {
			logger.error("查询已借出的要件：" + e.getMessage());
			e.printStackTrace();
		}
		
		outputJson(lendFileList, response);
	}
	
	/**
	 * 	查询已归还的要件列表
	  * @Description: TODO
	  * @param pid
	  * @param response
	  * @author: andrew
	  * @date: 2016年2月28日 上午9:45:32
	 */
	@RequestMapping(value = "/getReturnFileList")
	public void getReturnFileList(Integer pid, HttpServletResponse response){
		
		List<ElementLendDetails> returnFileList = new ArrayList<ElementLendDetails>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
			ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();
			ElementLendDetails details = new ElementLendDetails();
			details.setLendId(pid);
			details.setStatus(2);
			returnFileList = client.queryElementLendDetails(details );
		}catch (Exception e) {
			logger.error("查询已借出的要件：" + e.getMessage());
			e.printStackTrace();
		}
		
		outputJson(returnFileList, response);
	}
	
	/**
	 * 查询登录用户的部门列表
	  * @Description: TODO
	  * @param request
	  * @param response
	  * @author: andrew
	  * @date: 2016年2月28日 下午4:26:35
	 */
	@RequestMapping(value = "/getLoginUserOrgList")
	public void getLoginUserOrgList(HttpServletRequest request, HttpServletResponse response) {

		BaseClientFactory clientFactoryUserOrg = null;
		BaseClientFactory clientFactoryOrg = null;
		SysUserOrgInfo dataScopeUserOrgInfo = null;
		
		List<SysOrgInfo> resultList = new ArrayList<SysOrgInfo>();
		SysOrgInfo org = new SysOrgInfo();
		org.setId(-1);
		org.setName("--请选择--");
		resultList.add(org);
		try {
			if (clientFactoryUserOrg == null) {
				clientFactoryUserOrg = this.getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserOrgInfoService");
			}

			SysUserOrgInfoService.Client clientUserOrg = (SysUserOrgInfoService.Client) clientFactoryUserOrg.getClient();

			if (clientFactoryOrg == null) {
				clientFactoryOrg = this.getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
			}
			SysOrgInfoService.Client clientOrg = (SysOrgInfoService.Client) clientFactoryOrg.getClient();
			SysUser loginUser = getSysUser();
			// 获取登录用所属机构
			SysOrgInfo loginUserOrg = clientOrg.getSysOrgInfo(loginUser.getOrgId());
			// 根据登录用户id获取该用户的数据权限列表
			List<SysUserOrgInfo> dataUserList = clientUserOrg.listUserOrgInfo(loginUser.getPid());
			
			if (dataUserList != null && !dataUserList.isEmpty()) {
				
				dataScopeUserOrgInfo = dataUserList.get(0);
				// 私有数据,除了自己的部门，不添加别的数据
				if (dataScopeUserOrgInfo.getDataScope() == Constants.DATA_SCOPE_PRIVATE) {
					resultList.add(loginUserOrg);
					// 集体数据,返回登录用户所管理的机构下所有员工编号
				} else if (dataScopeUserOrgInfo.getDataScope() == Constants.DATA_SCOPE_PROTECED) {
					
					// 判断登录用户所属机构是否为集团,如为集团领导,则可以查看所有数据，dataUserIds=null
					if (Constants.QFANG_GROUP_LAYER == loginUserOrg.layer) {
						SysOrgInfo condition = new SysOrgInfo();
						condition.setCategory(1);//业务线所有部门
						resultList.addAll(clientOrg.listSysOrgByLayer(condition));
					}
					// 获取登录用户所管理的机构编号
					List<Integer> orgIds = new ArrayList<Integer>();
					for (SysUserOrgInfo temp : dataUserList) {
						Integer orgId = temp.getOrgId();
						orgIds.add(orgId);
						// 添加该机构下的子机构
						resultList.addAll(clientOrg.listSysOrgInfo(orgId));
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询用戶权限的部门列表：" + e.getMessage());
			e.printStackTrace();
		}
		outputJson(resultList, response);
	}
	
	/**
	  * 根据项目id获取收件列表
	  *@author:liangyanjun
	  *@time:2016年4月1日下午11:24:57
	  *@param projectId
	  *@param request
	  *@param response
	  */
	 @RequestMapping(value = "/getCollectFileListByProjectId",method= RequestMethod.POST)
	 public void getCollectFileListByProjectId(int projectId, HttpServletRequest request, HttpServletResponse response) {
	      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, "IntegratedDeptService");
	      try {
	         CollectFileDTO collectfileQuery=new CollectFileDTO();
	         collectfileQuery.setProjectId(projectId);
	         collectfileQuery.setPage(-1);//不分页查询
	         collectfileQuery.setStatus(Constants.STATUS_ENABLED);
	        
	         List<CollectFileDTO> collectFileList = service.queryCollectFile(collectfileQuery);
	         List<DataInfo> fileList = convertCollectFileList(collectFileList);
	         
	         outputJson(fileList, response);
	      } catch (TException e) {
	         logger.error("根据项目id获取收件列表失败：入参：projectId" + projectId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
	      }
	   }
   /**
    * 转换
    */
   private List<DataInfo> convertCollectFileList(List<CollectFileDTO> collectFileList){
	   List<DataInfo> fileList = new ArrayList<DataInfo>();
	   if(collectFileList != null && collectFileList.size()>0){
        	 for(CollectFileDTO dto: collectFileList){
        		 DataInfo dataInfo = new DataInfo();
        		 dataInfo.setFileId(dto.getPid());
        		 dataInfo.setFileName(dto.getName());
        		 dataInfo.setUploadDttm(dto.getCollectDate());
        		 fileList.add(dataInfo);
        	 }
         }
	   return fileList;
   }
   
   /**
    * 批量查询收件列表
     * @param pids
     * @param response
     * @author: baogang
     * @date: 2016年5月19日 上午10:58:27
    */
   @RequestMapping(value = "/getCollectFileListByPids")
   public void getCollectFileListByPids(String pids,HttpServletResponse response){
	   Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, "IntegratedDeptService");
	   List<CollectFileDTO> collectFileList = new ArrayList<CollectFileDTO>();
	   try {
		   collectFileList = service.queryCollectFileByPids(pids);
		} catch (Exception e) {
			logger.error("根据ids获取收件列表失败：入参：pids" + pids + "。错误：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
		}
	   outputJson(collectFileList, response);
   }
   
   
   @RequestMapping(value = "/getCollectFilesByCodes")
   public void getCollectFilesByCodes(CollectFileDTO collectFileDTO,HttpServletResponse response){
	   Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, "IntegratedDeptService");
	   List<Integer> pids = new ArrayList<Integer>();
	   try {
		   List<CollectFileMap> collectFileMapList = collectFileDTO.getCollectFileMapList();
		   //根据后台选择的值查询要件ID
		   if(collectFileMapList != null && collectFileMapList.size() >0){
			   CollectFileDTO collectfileQuery = new CollectFileDTO();
		       collectfileQuery.setPage(-1);
		       collectfileQuery.setProjectId(collectFileDTO.getProjectId());
		       collectfileQuery.setBuyerSellerName(collectFileDTO.getBuyerSellerName());
		       collectfileQuery.setBuyerSellerType(collectFileDTO.getBuyerSellerType());
		       collectfileQuery.setStatus(Constants.STATUS_ENABLED);
		       List<CollectFileDTO> collectFileList = service.queryCollectFile(collectfileQuery);
		       Map<String, String> codeAndRemarkMap = getCodeAndRemarkMap(collectFileMapList);
		       for (CollectFileDTO dto : collectFileList) {
		            String code = dto.getCode();
		           if (codeAndRemarkMap.containsKey(code)) {
		        	   pids.add(dto.getPid());
		            }
		         }
		   }
		} catch (Exception e) {
	        e.printStackTrace();
		}
	   outputJson(pids, response);
   }
   
   /**
    * 将要件的name值与remark组成Mapper
     * @param collectFileMapList
     * @return
     * @author: baogang
     * @date: 2016年5月19日 下午3:11:42
    */
   private Map<String, String> getCodeAndRemarkMap(List<CollectFileMap> collectFileMapList) {
      Map<String, String> codeAndRemarkMap = new HashMap<>();
      for (CollectFileMap collectFileMap : collectFileMapList) {
         if (StringUtil.isBlank(collectFileMap.getCode())) {
            continue;
         }
         codeAndRemarkMap.put(collectFileMap.getCode(), collectFileMap.getRemark());
      }
      return codeAndRemarkMap;
   }
   
   @RequestMapping(value = "/toPrintElementFile")
   public String toPrintElementFile(ModelMap model, int projectId, int buyerSellerType, String buyerSellerName,String codes,String porpuse){
	   try {
	         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, "IntegratedDeptService");
	         CollectFilePrintInfo collectFilePrintInfo = new CollectFilePrintInfo();
	         collectFilePrintInfo.setProjectId(projectId);
	         collectFilePrintInfo = service.getCollectFilePrintInfo(collectFilePrintInfo);

	         collectFilePrintInfo.setBuyerSellerType(buyerSellerType);
	         if (buyerSellerType == 1) {//买卖类型：买方=1，卖方=2
	            collectFilePrintInfo.setBuyerName(buyerSellerName);
	         } else {
	            collectFilePrintInfo.setSellerName(buyerSellerName);
	         }
	         String pids = "";
		     //根据后台选择的值查询要件ID
		     CollectFileDTO collectfileQuery = new CollectFileDTO();
	         collectfileQuery.setPage(-1);
	         collectfileQuery.setProjectId(projectId);
	         collectfileQuery.setBuyerSellerName(buyerSellerName);
	         collectfileQuery.setBuyerSellerType(buyerSellerType);
	         collectfileQuery.setStatus(Constants.STATUS_ENABLED);
	         List<CollectFileDTO> collectFileList = service.queryCollectFile(collectfileQuery);
	        
	         for (CollectFileDTO dto : collectFileList) {
	              String code = dto.getCode();
	             if (codes.indexOf(code) !=-1) {
	        	     pids+=dto.getPid()+",";
	              }
	           }
	         
	         model.put("collectFilePrintInfo", collectFilePrintInfo);
	         model.put("pids", pids);
	         model.put("porpuse", porpuse);
	      } catch (TException e) {
	         logger.error("获取要件打印信息;入参：projectId:"+projectId+",buyerSellerType:"+buyerSellerType+",buyerSellerName:"+buyerSellerName+"错误：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
	      }
	   return  "inloan/elementLend/element_file_print";
   }
}
