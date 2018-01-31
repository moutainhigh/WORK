package com.xlkfinance.bms.web.controller.beforeloan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizMortgageEvaluation;
import com.xlkfinance.bms.rpc.beforeloan.BizMortgageEvaluationService;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotFile;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotFileService;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotInfo;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotInfoService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.inloan.BizCapitalSelRecord;
import com.xlkfinance.bms.rpc.inloan.BizCapitalSelRecordService;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfo;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfoService;
import com.xlkfinance.bms.rpc.inloan.RepaymentRecordDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentService;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;
import com.xlkfinance.bms.rpc.project.BizHisLoanInfo;
import com.xlkfinance.bms.rpc.project.BizHisLoanInfoService;
import com.xlkfinance.bms.rpc.project.CusHisCardInfo;
import com.xlkfinance.bms.rpc.project.CusHisCardInfoService;
import com.xlkfinance.bms.rpc.project.ProjectDTO;
import com.xlkfinance.bms.rpc.project.ProjectInfoService;
import com.xlkfinance.bms.rpc.project.ProjectInfoService.Client;
import com.xlkfinance.bms.rpc.repayment.RepaymentDetailIndexDTO;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;
/**
 * 抵押贷贷前申请controller
 * @author baogang
 *
 */
@Controller
@RequestMapping("/projectInfoController")
public class ProjectInfoController  extends BaseController {
	private Logger logger = LoggerFactory.getLogger(ProjectInfoController.class);
	
	/**
	 * 抵押贷列表页面跳转
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index() {
		return "projectinfo/index";
	}
	
	/**
	 * (资料上传列表页面跳转).<br/>
	 * @author dulin
	 * @param foreStatus
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "navigationDatum")
	public String navigationDatum(int foreStatus, ModelMap model) {
		model.put("foreStatus", foreStatus);
		return "projectinfo/loan_add_datum";
	}
	
	/**
	 * 查询赎楼项目列表
	  * @param project
	  * @param request
	  * @param response
	  * @author: Administrator
	  * @date: 2016年2月15日 下午2:57:11
	 */
	@RequestMapping(value = "getProjectByPage", method = RequestMethod.POST)
	public void getProjectByPage(ProjectDTO query,HttpServletRequest request, HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
			query.setUserIds(getUserIds(request));//数据权限
			query.setRecordClerkId(getShiroUser().getPid());//查询录单员为登录人的业务
			query.setIsChechan(0);//未撤单
			query.setProjectType(8);//抵押贷类产品
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			// 创建集合
 			List<ProjectDTO> list = client.getProjectByPage(query);
			int count = client.getProjectCount(query);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
				logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 跳转到项目编辑页面
	 * @param projectId
	 * @param editType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toEditProject")
	public String toEditProject(@RequestParam(value = "projectId", required = false) Integer projectId,@RequestParam(value = "type", required = false)String editType, ModelMap model) {
		// 创建对象
		Project project = new Project();
		
		SysUser sysUser = getSysUser();
		project.setProjectType(8);//默认设置项目类型
		project.setProjectSource(2);//项目来源为小科录入
		project.setRecordClerkId(sysUser.getPid());//录单员为系统登录人员
		project.setDeclaration(sysUser.getRealName());//录单员为系统登录人员
		BaseClientFactory clientFactory = null;
		
		try {
			// 项目id存在时，获取项目信息
			if (projectId != null && projectId > 0) {
				clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
				ProjectInfoService.Client projectInfoService = (Client) clientFactory.getClient();
				project = projectInfoService.getLoanProjectByProjectId(projectId);
			}
			clientFactory = getFactory(BusinessModule.MODUEL_PRODUCT, "ProductService");
			ProductService.Client client = (ProductService.Client) clientFactory.getClient();
			Product product = new Product();
			product.setProductType(Constants.PRODUCT_TYPE_MORTGAGE);
			List<Product> productList = client.getAllProductList(product);
			if (!CollectionUtils.isEmpty(productList)) {
				model.put("mortgageLoanProduct", productList.get(0));
			}
			
		} catch (Exception e) {
			logger.error("跳转抵押贷申请页面，查询抵押贷产品出错！" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		
		model.put("project", project);
		model.put("login", sysUser);
		model.put("editType", editType);
		return "projectinfo/project_tab";
	}
	
	/**
	 * 查询客户银行信息变更历史
	  * @param project
	  * @param request
	  * @param response
	  * @author: Administrator
	  * @date: 2016年2月15日 下午2:57:11
	 */
	@RequestMapping(value = "getCardInfoHistory", method = RequestMethod.POST)
	public void getCardInfoHistory(CusHisCardInfo cusCardInfo,HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		List<CusHisCardInfo> list = new ArrayList<CusHisCardInfo>();
		if (cusCardInfo != null && cusCardInfo.getProjectId() != 0) {
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "CusHisCardInfoService");
				CusHisCardInfoService.Client client = (CusHisCardInfoService.Client) clientFactory.getClient();
	 			list = client.getAll(cusCardInfo);
			} catch (Exception e) {
					logger.error("查询银行卡历史列表:" + ExceptionUtil.getExceptionMessage(e));
			} finally {
				destroyFactory(clientFactory);
			}
		}
		outputJson(list, response);
	}
	
	/**
	 * 查询贷款申请信息变更历史
	 * @param project
	 * @param request
	 * @param response
	 * @author: Administrator
	 * @date: 2016年2月15日 下午2:57:11
	 */
	@RequestMapping(value = "getLoanInfoAppHistory", method = RequestMethod.POST)
	public void getLoanInfoAppHistory(BizHisLoanInfo loanInfo,HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		List<BizHisLoanInfo> list = new ArrayList<BizHisLoanInfo>();
		if (loanInfo != null && loanInfo.getProjectId() != 0) {
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "BizHisLoanInfoService");
				BizHisLoanInfoService.Client client = (BizHisLoanInfoService.Client) clientFactory.getClient();
				list = client.getAll(loanInfo);
			} catch (Exception e) {
				logger.error("查询贷款申请信息历史列表:" + ExceptionUtil.getExceptionMessage(e));
			} finally {
				destroyFactory(clientFactory);
			}
		}
		outputJson(list, response);
	}
	
	/**
	 * 根据项目ID查询项目财务明细列表
	 * @param query
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getRepaymentByPage", method = RequestMethod.POST)
	public void getRepaymentByPage(@RequestParam(value = "projectId", required = true) Integer projectId,HttpServletRequest request, HttpServletResponse response) {
		if (projectId <= 0) {
	         logger.error("请求数据不合法：" + projectId);
	         fillReturnJson(response, false, "根据项目ID查询项目财务明细列表失败,请输入必填项!");
	         return;
	      }
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			RepaymentDetailIndexDTO query = new RepaymentDetailIndexDTO();
			query.setProjectId(projectId);
			// 创建集合
 			List<RepaymentDetailIndexDTO> list = client.queryRepayment(query);
			int count = list.size();
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
				logger.error("查询项目财务明细列表出错:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 保存抵押贷申请信息
	  * @param project
	  * @param response
	  * @author: baogang
	  * @date: 2016年5月5日 上午11:29:14
	 */
	@RequestMapping(value="saveProjectInfo")
	public void saveProjectInfo(Project project, HttpServletRequest request,HttpServletResponse response) {
		logger.info("保存抵押贷申请信息！{}", project);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			if(project.getPid() <= 0 && project.getPmUserId() <= 0){
				// 设置当前项目经理为当前登录的用户
				project.setPmUserId(getShiroUser().getPid());
				// 设置当前项目的下一节点处理人为登录用户
				project.setNextUserId(getShiroUser().getRealName());
			}
			 Map<String, String> projectInfo = client.saveProjectInfo(project);
			if (null != projectInfo && !projectInfo.isEmpty()) {
				// 分别获取项目ID和授信ID
				j.getHeader().put("success", true);
				j.getHeader().putAll(projectInfo);
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, String.format("保存房抵贷贷前申请成功返回结果：%s", projectInfo));
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存贷款申请失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("保存贷款申请失败：" + ExceptionUtil.getExceptionMessage(e));
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存贷款申请失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 抵押物评估
	 */
	@RequestMapping(value="saveEvaluation")
	public void saveEvaluation(BizMortgageEvaluation evaluation , HttpServletResponse response) {
		logger.info("抵押物评估！{}", evaluation);
		BaseClientFactory clientFactory = null;
		Json j = super.getSuccessObj();
		try {
			//物业未关联
			if(evaluation.getEstate() <=0 ) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "未关联物业");
			}
			else if(evaluation.getProjectId() <=0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "未关联项目");
			}
			else {
				evaluation.setCreaterId(getShiroUser().getPid());
				evaluation.setUpdateId(getShiroUser().getPid());
				clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizMortgageEvaluationService");
				BizMortgageEvaluationService.Client client = (BizMortgageEvaluationService.Client) clientFactory.getClient();
				if(client.eval(evaluation) >=0) {
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "操作成功!");
				}else {
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "操作失败!");
				}
			}
		} catch (Exception e) {
			logger.error("抵押物评估失败：" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 获取评估价列表值
	 * @param response
	 */
	@RequestMapping(value="getEvalDictList")
	public void getEvalDictList(BizMortgageEvaluation evaluation , HttpServletResponse response) {
		logger.debug("获取评估字典值{}", evaluation);
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		BaseClientFactory evalDictFactory = null;
		BaseClientFactory spotInfoFactory = null;
		//评估价列表
		List<BizMortgageEvaluation>  evalDictList = new ArrayList<BizMortgageEvaluation>();
		try {
			//如果物业有评估记录 即取出评估记录 否则取出系统字典值
			evalDictFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizMortgageEvaluationService");
			BizMortgageEvaluationService.Client bizMortgageEvaluationService = (BizMortgageEvaluationService.Client) evalDictFactory.getClient();
			BizMortgageEvaluation param = new BizMortgageEvaluation();
			param.setEstate(evaluation.getEstate());
			evalDictList = bizMortgageEvaluationService.getAll(param);
			if(evalDictList != null && evalDictList.size() <=0) {
				List<SysLookupVal> listLookups = new ArrayList<SysLookupVal>();
				clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
				SysLookupService.Client sysLookupService = (SysLookupService.Client) clientFactory.getClient();
				listLookups = sysLookupService.getProjectAssDtlByLookType("MORTGAGE_EVAL_PRICE");
				for(SysLookupVal item : listLookups) {
					BizMortgageEvaluation evalItem = new BizMortgageEvaluation();
					evalItem.setEvaluationSource(item.getLookupDesc());
					double propor = 0;
					if(!StringUtil.isBlank(item.getLookupVal())) {
						try {
							propor = Double.parseDouble(item.getLookupVal());
						} catch (Exception e) {
							propor = 0;
						}
					}
					evalItem.setEvaluationProportion(propor);
					evalDictList.add(evalItem);
				}
			}else {//查询下户时间
				spotInfoFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizSpotInfoService");
				BizSpotInfoService.Client bizSpotInfoService = (BizSpotInfoService.Client) spotInfoFactory.getClient();
				BizSpotInfo bizSpotInfo = new BizSpotInfo();
				bizSpotInfo.setEastateId(evaluation.getEstate());
				List<BizSpotInfo> bizSpotInfoList = bizSpotInfoService.getAll(bizSpotInfo);
				if(bizSpotInfoList!=null && bizSpotInfoList.size()>0) {
					map.put("shouldSpotTime", DateUtils.dateFormatByPattern(bizSpotInfoList.get(0).getShouldSpotTime(), "yyyy-MM-dd"));
				}
				
			}
			
			map.put("total", evalDictList != null ? evalDictList.size() : 0);
			map.put("rows", evalDictList);
		} catch (Exception e) {
			logger.error("获取评估价列表失败：" + ExceptionUtil.getExceptionMessage(e));
			map.put("total", 0);
			map.put("rows", evalDictList);
		} finally {
			destroyFactory(clientFactory);
			destroyFactory(evalDictFactory);
			destroyFactory(spotInfoFactory);
		}
		outputJson(map, response);
	}
	
	/**
	 * 跳转到财务明细页面
	 * @return
	 */
	@RequestMapping(value = "toFinanceDetail")
	public String toFinanceDetail(){
		return "projectinfo/finance_detail";
	}
	
	/**
	 * 分页查询回款记录的数据
	 * @param projectId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getRepaymentRecordByPage", method = RequestMethod.POST)
	public void getRepaymentRecordByPage(RepaymentRecordDTO query,HttpServletRequest request, HttpServletResponse response) {
		if (query.getProjectId() <= 0) {
	         logger.error("请求数据不合法：" + query.getProjectId());
	         fillReturnJson(response, false, "分页查询回款记录的数据失败,请输入必填项!");
	         return;
	      }
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();

			//查询回款记录
            List<RepaymentRecordDTO> list = client.queryRepaymentRecord(query );
			int count = client.getRepaymentRecordTotal(query);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error("分页查询回款记录的数据:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 贷前 跳转到尽职调查
	 */
	@RequestMapping(value="toSpotInfoPage")
	public String toSpotInfoPage(Integer projectId,int foreclosureStatus, ModelMap model) {
		//物业列表
		BaseClientFactory clientFactory = null;
		List<BizProjectEstate> list = new ArrayList<BizProjectEstate>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			list = client.getAllCascadeSpotInfoByProjectId(projectId);
		} catch (Exception e) {
			logger.error("根据项目ID查询物业信息:" + ExceptionUtil.getExceptionMessage(e));
		}  finally {
			destroyFactory(clientFactory);
		}
		model.put("projectSpotInfoList", list);
		model.put("projectId", projectId);
		model.put("foreclosureStatus", foreclosureStatus);
		return "projectinfo/projectSpotInfo";
	}
	
	
	/**
	 * 判断项目是否已评估
	 */
	@RequestMapping(value="isPledgeEval")
	@ResponseBody
	public int isPledgeEval(Integer projectId,HttpServletResponse response) {
		int result = 1;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			result = client.isPledgeEval(projectId);
			
		} catch (Exception e) {
			logger.error("根据项目ID查询物业信息是否已评估:" + ExceptionUtil.getExceptionMessage(e));
			result = -1;
		}  finally {
			destroyFactory(clientFactory);
		}
		return result;
	}
	
	/**
	 * 判断项目是否已尽职调查
	 */
	@RequestMapping(value="isSpotInfoSave")
	@ResponseBody
	public int isSpotInfoSave(Integer projectId,HttpServletResponse response) {
		int result = 1;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizSpotInfoService");
			BizSpotInfoService.Client client = (BizSpotInfoService.Client) clientFactory.getClient();
			result = client.isSave(projectId);
		} catch (Exception e) {
			logger.error("根据项目ID查询物业信息是否已评估:" + ExceptionUtil.getExceptionMessage(e));
			result = -1;
		}  finally {
			destroyFactory(clientFactory);
		}
		return result;
	}
	
	/**
	 * 贷前 保存 尽职调查
	 * @return
	 */
	@RequestMapping(value="saveSpotInfo",method = RequestMethod.POST)
	public void saveSpotInfo(BizSpotInfo spotInfoList,HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizSpotInfoService");
			BizSpotInfoService.Client client = (BizSpotInfoService.Client) clientFactory.getClient();
			int result = client.save(spotInfoList.getSpotInfos());
			if(result <=0 ) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败!");
			}
		} catch (Exception e) {
			logger.error("尽职调查失败:" + ExceptionUtil.getExceptionMessage(e));
		}  finally {
			destroyFactory(clientFactory);
		}
		outputJson(j, response);
	}
	
	/**
	 * 尽职调查文件上传
	 */
	@RequestMapping(value="uploadSpotFile")
	public void uploadSpotFile(HttpServletRequest request, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		int spotId = 0;
		BaseClientFactory bizFileClientFactory = null;
		try {
			Map<String, Object> map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_INLOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
			if ((Boolean) map.get("flag") == false) {
				logger.error("上传出错");
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "文件上传失败or文件格式不合法");
			}else {
				//增加一条文件记录
				clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizSpotFileService");
				BizSpotFileService.Client bizSpotFileService = (BizSpotFileService.Client) clientFactory.getClient();
				
				bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
				BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
				BizFile bizFile = new BizFile();
				
				@SuppressWarnings("unchecked")
				List<FileItem> items = (List<FileItem>) map.get("items");
				for (int i = 0; i < items.size(); i++) {
					FileItem item = (FileItem) items.get(i);
					// 获取文件上传的信息
					int fileSize = (int) item.getSize();
					if(item.getFieldName().equals("spotId")) {
						spotId = Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item));
					}
					if(item.getName() == null || item.getName().equals(""))continue;
					String fileFullName = item.getName().toLowerCase();
					bizFile.setFileSize(fileSize);
					bizFile.setFileName(item.getName());
					int dotLocation = fileFullName.lastIndexOf(".");
					String fileName = fileFullName.substring(0, dotLocation);
					fileName = fileName.replaceAll(" ", "");
					String fileType = fileFullName.substring(dotLocation).substring(1);
					bizFile.setFileType(fileType);
					bizFile.setFileName(fileName);
				}
				
				bizFile.setFileUrl(map.get("path").toString());
				String uploadDttm = DateUtil.format(new Date());
				bizFile.setUploadDttm(uploadDttm);
				bizFile.setStatus(1);
				int uploadUserId = getShiroUser().getPid();
				bizFile.setUploadUserId(uploadUserId);
				if(spotId >0) {
					int fileId = bizFileClient.saveBizFile(bizFile);
					
					
					BizSpotFile file = new BizSpotFile();
					file.setFileId(fileId);
					file.setSpotId(spotId);
					file.setStatus(1);
					bizSpotFileService.insert(file);
					j.getHeader().put("msg", "文件上传成功!");
				}else {
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "文件上传失败!尽职调查未关联ID");
				}
				
			}
		} catch (Exception e) {
			logger.error("尽职调查文件上传失败:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
			destroyFactory(bizFileClientFactory);
		}
		outputJson(j, response);
	}
	
	/**
	 * 根据尽职调查报告id获取文件列表
	 */
	@RequestMapping(value="getSpotFileListBySpotId",method = RequestMethod.POST)
	public void getSpotFileListBySpotId(BizSpotFile spotFile,HttpServletRequest request, HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		if (spotFile == null || spotFile.getSpotId() <= 0) {
			logger.info("请求数据不合法");
			map.put("rows", new ArrayList<BizSpotFile>());
			map.put("total", 0);
			outputJson(map, response);
			return;
		}
		BaseClientFactory clientFactory = null;
		List<BizSpotFile> list = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizSpotFileService");
			BizSpotFileService.Client bizSpotFileService = (BizSpotFileService.Client) clientFactory.getClient();
			BizSpotFile file = new BizSpotFile();
			file.setSpotId(spotFile.getSpotId());
			file.setPage(spotFile.getPage());
			file.setRows(spotFile.getRows());
			list = bizSpotFileService.getAll(file);
			int count = bizSpotFileService.getAllCount(file);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error("分页查询回款记录的数据:" + ExceptionUtil.getExceptionMessage(e));
			map.put("rows", list);
			map.put("total", 0);
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	@RequestMapping(value = "delSpotFile")
	public void delSpotFile(@RequestParam("pid") int pid, HttpServletResponse response) {
		int del = 0;
		BaseClientFactory c = null;
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizSpotFileService");
			BizSpotFileService.Client fileService = (BizSpotFileService.Client) c.getClient();
			BizSpotFile bizSpotFile = new BizSpotFile();
			bizSpotFile.setPid(pid);
			del = fileService.deleteById(bizSpotFile);
			if (del > 0) {
				tojson.put("delStatus", "删除文件成功");
			} else {
				tojson.put("delStatus", "删除文件失败");
			}
		} catch (Exception e) {
			logger.error("删除资料列表出错",e);
		} finally {
			destroyFactory(c);
		}
		outputJson(tojson, response);
	}
	
	
	/**
	 * 获取资方选择记录
	 * @param projectId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="getCapitalSelRecord",method = RequestMethod.POST)
	public void getCapitalSelRecord(Integer projectId,HttpServletRequest request, HttpServletResponse response){
		if (projectId <= 0) {
	         logger.error("请求数据不合法：" + projectId);
	         fillReturnJson(response, false, "获取资方选择记录数据失败,请输入必填项!");
	         return;
	      }
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizCapitalSelRecordService");
			BizCapitalSelRecordService.Client client = (BizCapitalSelRecordService.Client) clientFactory.getClient();

			//查询回款记录
            List<BizCapitalSelRecord> list = client.getAllByProjectId(projectId);
			int count = list.size();
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error("获取资方选择记录数据:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * tab page 签约公证抵押
	 */
	@RequestMapping(value="toInterviewPage")
	public String toInterviewPage(Integer projectId, ModelMap model) {
		//根据项目ID查询面签管理信息
		BaseClientFactory clientFactory = null;
		BizInterviewInfo info = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizInterviewInfoService");
			BizInterviewInfoService.Client bizInterviewInfoService = (BizInterviewInfoService.Client) clientFactory.getClient();
			
			info = bizInterviewInfoService.getByProjectId(projectId);
			
		} catch (Exception e) {
			logger.error("根据项目ID查询签约公证信息异常:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}
		model.put("interviewInfo", info);
		return "projectinfo/projectInterview";
	}
}
