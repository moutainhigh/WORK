/**
 * @Title: RepayFeewdtlController.java
 * @Package com.xlkfinance.bms.web.controller.repayment
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: gaoWen
 * @date: 2015年3月26日 上午10:25:51
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.repayment;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;
import com.xlkfinance.bms.rpc.repayment.FeeWaiverApplicationDTO;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlDatView;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlService;
import com.xlkfinance.bms.rpc.repayment.UploadFileService;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.controller.contract.ContractGenerator;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;
import com.xlkfinance.bms.web.util.FileDownload;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

/**
 * @ClassName: RepayFeewdtlController
 * @Description: 费用减免
 * @author: JingYu.Dai
 * @date: 2015年5月19日 下午8:33:04
 */
@Controller
@RequestMapping("/repayFeewdtlController")
public class RepayFeewdtlController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(RepayFeewdtlController.class);

	@RequestMapping("/repayFeewdtlDeal")
	public String repayfinancialDeal(int projectId, ModelMap model) {
		BaseClientFactory clientFactory = null;
		RepayFeewdtlDatView re = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,
					"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory
					.getClient();
			re = client.queryRegFeewprojectinfobyproId(projectId);
		} catch (Exception e) {
			logger.error("错误：repayFeewdtlController.repayfinancialDeal-"+e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.addAttribute("loanId", re.getLoanId());
		model.addAttribute("repayId", -1);
		model.addAttribute("projectId", projectId);
		model.addAttribute("projectName", re.getProjectName());
		model.addAttribute("projectNum", re.getProjectNum());
		return "repayment/repay_feewdtl_deal";
	}

	@RequestMapping("/queryloanInfobyId")
	@ResponseBody
	public void queryloanInfobyId(String pId, HttpServletResponse response) {
		List<RepayFeewdtlDatView> list = new ArrayList<RepayFeewdtlDatView>();
		String[] feewdelinfo = pId.split("/");
		String[] feewde = null;
		// RepayFeewdtlDatView feewdtlDatView=new RepayFeewdtlDatView();
		for (int i = 0; i < feewdelinfo.length; i++) {
			RepayFeewdtlDatView feewdtlDatView = new RepayFeewdtlDatView();
			feewde = feewdelinfo[i].split(",");
			feewdtlDatView.setPId(Integer.parseInt(feewde[0]));
			feewdtlDatView.setPlanCycleNum(Integer.parseInt(feewde[1]));
			feewdtlDatView.setShouldPrincipal(Double.parseDouble(feewde[2]));
			feewdtlDatView.setShouldInterest(Double.parseDouble(feewde[3]));
			feewdtlDatView.setShouldOtherCost(Double.parseDouble(feewde[4]));
			feewdtlDatView.setShouldMangCost(Double.parseDouble(feewde[5]));
			feewdtlDatView.setOverdueFine(Double.parseDouble(feewde[6]));
			feewdtlDatView.setOverdueInterest(Double.parseDouble(feewde[7]));
			feewdtlDatView.setTotal(Double.parseDouble(feewde[8])
					+ Double.parseDouble(feewde[7])
					+ Double.parseDouble(feewde[6]));
			feewdtlDatView.setTypeName("应收");
			list.add(feewdtlDatView);
			RepayFeewdtlDatView feewdtlDatV = new RepayFeewdtlDatView();
			feewdtlDatV.setPId(0);
			feewdtlDatV.setPlanCycleNum(Integer.parseInt(feewde[1]));
			feewdtlDatV.setShouldPrincipal(0);
			feewdtlDatV.setShouldInterest(0);
			feewdtlDatV.setShouldOtherCost(0);
			feewdtlDatV.setShouldMangCost(0);
			feewdtlDatV.setOverdueFine(0);
			feewdtlDatV.setOverdueInterest(0);
			feewdtlDatV.setTotal(0);
			feewdtlDatV.setTypeName("减免");
			list.add(feewdtlDatV);
			RepayFeewdtlDatView feewdtlD = new RepayFeewdtlDatView();
			feewdtlD.setPId(Integer.parseInt(feewde[0]));
			feewdtlD.setPlanCycleNum(Integer.parseInt(feewde[1]));
			feewdtlD.setShouldPrincipal(Double.parseDouble(feewde[2]));
			feewdtlD.setShouldInterest(Double.parseDouble(feewde[3]));
			feewdtlD.setShouldOtherCost(Double.parseDouble(feewde[4]));
			feewdtlD.setShouldMangCost(Double.parseDouble(feewde[5]));
			feewdtlD.setOverdueFine(Double.parseDouble(feewde[6]));
			feewdtlD.setOverdueInterest(Double.parseDouble(feewde[7]));
			feewdtlD.setTotal(Double.parseDouble(feewde[8]));
			feewdtlD.setTypeName("减免后应收");
			list.add(feewdtlD);
		}

		outputJson(list, response);
	}

	@RequestMapping("/insertLoanFeewdelInfo")
	@ResponseBody
	public void insertLoanFeewdelInfo(String reason, int projectId, int loanId,
			String datViews, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		int st = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory.getClient();
			st = client.insertLoanFeewdelInfo(reason, datViews, projectId,loanId);
		} catch (Exception e) {
			logger.error("错误：repayFeewdtlController.insertLoanFeewdelInfo-"+e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(st, response);
	}

	@RequestMapping(value = "uploadadvapply")
	@ResponseBody
	public void saveData(HttpServletRequest request,
			HttpServletResponse response) {
		UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO = new UploadinstAdvapplyBaseDTO();
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		ShiroUser shiroUser = getShiroUser();
		int userId = shiroUser.getPid();
		String path = null;
		String st = null;
		boolean falg = true;
		String pathname = null;
		try {
			request.setCharacterEncoding("UTF-8");
			map = FileUtil.processFileUpload(request, response,
					BusinessModule.MODUEL_REPAYMENT, getUploadFilePath(),
					getFileSize(), getFileDateDirectory(), getUploadFileType());
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,
					"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory
					.getClient();
			if ((Boolean) map.get("flag") == false) {
				logger.error("上传出错: 文件上传失败 or 文件格式不合法");
				st = "文件上传失败or文件格式不合法";
				falg = false;
			} else {
				@SuppressWarnings("rawtypes")
				List items = (List) map.get("items");
				for (int j = 0; j < items.size(); j++) {
					FileItem item = (FileItem) items.get(j);
					if (item.isFormField()) {
						if (item.getFieldName().equals("fileKinds")) {
							uploadinstAdvapplyBaseDTO
									.setFileKinds(ParseDocAndDocxUtil
											.getStringCode(item));
						} else if (item.getFieldName().equals("fileDesc")) {
							uploadinstAdvapplyBaseDTO
									.setFileDesc(ParseDocAndDocxUtil
											.getStringCode(item));
						} else if (item.getFieldName().equals("projectId")) {
							uploadinstAdvapplyBaseDTO.setProjectId(Integer
									.parseInt(ParseDocAndDocxUtil
											.getStringCode(item)));
						} else if (item.getFieldName().equals("repayId")) {
							uploadinstAdvapplyBaseDTO.setRepayId(Integer
									.parseInt(ParseDocAndDocxUtil
											.getStringCode(item)));
						}
					} else {
						if (item.getFieldName().equals("file1"))
							uploadinstAdvapplyBaseDTO.setFileSize((int) item
									.getSize());
					}
					if(null != item.getName()){
						pathname = item.getName();
						
					}
				}
				String uploadDttm = DateUtil.format(new Date());
				uploadinstAdvapplyBaseDTO.setFileDttm(uploadDttm);
				path = String.valueOf(map.get("path"));
				uploadinstAdvapplyBaseDTO.setFileName(pathname);
				String fileType = pathname.substring(pathname.lastIndexOf(".")).substring(1);
				uploadinstAdvapplyBaseDTO.setFileType(fileType);
				uploadinstAdvapplyBaseDTO.setFilePath(path);
				uploadinstAdvapplyBaseDTO.setUploadUserid(userId + "");
				client.uploadinstFeewapply(uploadinstAdvapplyBaseDTO);
				st = "上传成功！";
			}
		} catch (Exception e) {
			logger.error("错误：repayFeewdtlController.insertLoanFeewdelInfo-"+e.getMessage());
			e.printStackTrace();
		}

		finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		Map<String,Object> resMap = new HashMap<String, Object>();
		resMap.put("manager", st);
		resMap.put("falg", falg);
		outputJson(resMap, response);
	}

	// 下载文件
	@RequestMapping(value = "downloadadvapply")
	@ResponseBody
	public void downloadData(HttpServletRequest request,
			HttpServletResponse response, String path) {
		try {
			FileDownload.downloadLocal(response, request, path);
		} catch (IOException e) {
			logger.error("错误：repayFeewdtlController.downloadData-"+e.getMessage());
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/queryRegFeewapplyFile",method=RequestMethod.POST)
	@ResponseBody
	public void queryRegFeewapplyFile(int repayId,int page,int rows, HttpServletResponse response) {
		if (repayId == -1) {
			outputJson("", response);
		}
		BaseClientFactory clientFactory = null;
		List<RegAdvapplyFileview> list = new ArrayList<RegAdvapplyFileview>();
		int total = 0;
		Map<String ,Object> map = new HashMap<String,Object>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,
					"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory
					.getClient();
			list = client.queryRegFeewapplyFile(repayId,page,rows);
			total =  client.queryRegFeewapplyFileTotal(repayId);
		} catch (Exception e) {
			logger.error("错误：repayFeewdtlController.queryRegFeewapplyFile-"+e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		map.put("rows", list);
		map.put("total", total);
		outputJson(map, response);
	}

	@RequestMapping("/queryRegFeewReasonbyprocess")
	@ResponseBody
	public void queryRegFeewReasonbyprocess(int repayId,
			HttpServletResponse response) {
		if (repayId == -1) {
			outputJson("", response);
		}
		BaseClientFactory clientFactory = null;
		String reason = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,
					"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory
					.getClient();
			reason = client.queryRegFeewReasonbyprocess(repayId);
		} catch (Exception e) {
			logger.error("错误：repayFeewdtlController.queryRegFeewReasonbyprocess-"+e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(reason, response);
	}

	// 费用减免的流程任务的跳转页面
	@RequestMapping("/repayFeewdtlDealbyprocess")
	public String repayFeewdtlDealbyprocess(int repayId, ModelMap model) {
		BaseClientFactory clientFactory = null;
		RepayFeewdtlDatView re = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,
					"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory
					.getClient();
			re = client.queryRegFeewprojectinfobyrepayId(repayId);
		} catch (Exception e) {
			logger.error("错误：repayFeewdtlController.repayFeewdtlDealbyprocess-"+e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.addAttribute("loanId", re.getLoanId());
		model.addAttribute("repayId", repayId);
		model.addAttribute("projectId", re.getProjectId());
		model.addAttribute("projectName", re.getProjectName());
		model.addAttribute("projectNum", re.getProjectNum());
		return "repayment/repay_feewdtl_deal_process";
	}

	// 费用减免的流程任务的跳转页面
	@RequestMapping("/queryRegFeewDealbyprocess")
	public void queryRegFeewDealbyprocess(int repayId,
			HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		List<RepayFeewdtlDatView> list = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,
					"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory
					.getClient();
			list = client.queryRegFeewDealbyprocess(repayId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(list, response);
	}

	/**
	  * @Description: 跟新费用减免列表
	  * @param datViews 列表json数据
	  * @param reason 减免原因
	  * @param repayId 费用减免主表ID
	  * @param response
	  * @author: JingYu.Dai
	  * @date: 2015年5月22日 下午4:57:03
	 */
	@RequestMapping("/updateRegFeewDealbyprocess")
	public void updateRegFeewDealbyprocess(String datViews, String reason,
			int repayId, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		int st = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory.getClient();
			st = client.updateRegFeewDealbyprocess(datViews, reason, repayId);
		} catch (Exception e) {
			logger.debug("error repayFeewdtlController.updateRegFeewDealbyprocess"+e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(st, response);
	}

	@RequestMapping(value = "/deleteProjectbyFeewDealList",method = RequestMethod.POST)
	public void deleteProjectbyFeewDealList(@RequestParam("repayIds[]") int[] repayIds,
			@RequestParam("projectIds[]") int[] projectIds,
			HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = null;
		int st = 0;
		List<Integer> repayIdList = new ArrayList<Integer>();
		List<Integer> projectIdList = new ArrayList<Integer>();
		for (int i = 0; i < repayIds.length; i++) {
			repayIdList.add(repayIds[i]);
			projectIdList.add(projectIds[i]);
		}
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory.getClient();
			st = client.deleteProjectbyFeewDealList(repayIdList,projectIdList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(st, response);
	}

	@RequestMapping(value = "/deleteProjectbyFeewDeal",method = RequestMethod.POST)
	public void deleteProjectbyFeewDeal(int repayId,int projectId,
			HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = null;
		int st = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory.getClient();
			st = client.deleteProjectbyFeewDeal(repayId,projectId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(st, response);
	}
	@RequestMapping(value = "/checkFeewDealByProjectId.action")
	public void checkFeewDealByProjectId(int projectId,
			HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = null;
		int st = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,
					"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory
					.getClient();
			st = client.checkFeewDealByProjectId(projectId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(st, response);
	}

	/**
	 * @Description: 编辑上传信息
	 * @param projectId
	 * @param response
	 * @throws Exception
	 * @author: GW
	 * @date: 2015年3月27日 下午5:13:33
	 */
	@RequestMapping(value = "updateloadfileinfo",method=RequestMethod.POST)
	public void updateLoadFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		int st = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,
					"UploadFileService");
			UploadFileService.Client client = (UploadFileService.Client) clientFactory
					.getClient();
			st = client.updateFeedLoadFileInfo(uploadinstAdvapplyBaseDTO);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		outputJson(st, response);
	}

	/**
	 * @Description: //删除原来的保存现在的费用减免信息
	 * @param projectId
	 * @param response
	 * @throws Exception
	 * @author: GW
	 * @date: 2015年3月27日 下午5:13:33
	 */
	@RequestMapping(value = "updateInsertRegFeewDealbyprocess")
	public void updateInsertRegFeewDealbyprocess(String reason,
			String FeewInfo, int repayId, int projectId, int loanId,
			HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		int st = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,
					"RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory
					.getClient();
			st = client.updateInsertRegFeewDealbyprocess(reason, FeewInfo,
					repayId, projectId, loanId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(st, response);
	}
	
	/**
	  * @Description: 费用减免申请书
	  * @param response
	  * @param repayId 费用减免ID
	  * @author: JingYu.Dai
	  * @date: 2015年5月19日 下午8:36:54
	  */
	@RequestMapping(value="feeWaiverApplication")
	public void feeWaiverApplication(int repayId,HttpServletResponse response,HttpServletRequest request){
		BaseClientFactory feeFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,"RepayFeewdtlService");
		BaseClientFactory tempFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		TemplateFile template = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
			RepayFeewdtlService.Client feeClient = (RepayFeewdtlService.Client) feeFactory.getClient();
			TemplateFileService.Client tempClient = (TemplateFileService.Client) tempFactory.getClient();
			ShiroUser user = getShiroUser();
			int userId = user.getPid();
			//查询费用减免申请书数据
			FeeWaiverApplicationDTO feeWaiverApplicationDTO = feeClient.findFeeWaiverApplication(userId,repayId);
			template = tempClient.getTemplateFile("FYJMSQS");//费用减免申请书
			String docText = ParseDocAndDocxUtil.parseDocumet(template.getFileUrl(), request);
			String[] fileNames = ParseDocAndDocxUtil.getFiledName(feeWaiverApplicationDTO);
			List<String> list = ParseDocAndDocxUtil.getDocumentSign(docText);
			List<TempLateParmDto> templateParamList = new ArrayList<TempLateParmDto>();
			for(int i=0;i<list.size();i++){
				if(!list.get(i).equals("")){
					String biaoshi = list.get(i).replace("@", "");
					for(int j=0;j<fileNames.length;j++){
						if(biaoshi.equals(fileNames[j])){
							TempLateParmDto tempLateParmDto = new TempLateParmDto();
							tempLateParmDto.setValConvertFlag(0);
							tempLateParmDto.setMatchFlag(list.get(i));
							templateParamList.add(tempLateParmDto);
							map.put(list.get(i), ExcelExport.getFieldValueByName(fileNames[j], feeWaiverApplicationDTO));
						}
					}
				}
			}
			ContractGenerator contractGenerator = new ContractGenerator();
			String realFileName = genFileName(template.getFileUrl());
			String createTime = ExcelExport.getDateToString();
			String localPath = CommonUtil.getRootPath()+createTime;
			File file = ExcelExport.createFile(localPath, realFileName);
			String path = CommonUtil.getRootPath() +template.getFileUrl();
			boolean b = contractGenerator.generate(this,templateParamList, path, file.getPath(), map, null,null);
			if(b==true){
				FileDownload.downloadLocalFile(response, request, createTime+"/"+realFileName,"费用减免申请说明书");
				//ExcelExport.sentFile("费用减免申请说明书"+realFileName, localPath, response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (feeFactory != null) {
				try {
					feeFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if(tempFactory != null){
				try {
					tempFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
