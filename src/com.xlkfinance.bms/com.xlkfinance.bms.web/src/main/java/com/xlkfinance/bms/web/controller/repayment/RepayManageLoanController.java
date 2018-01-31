package com.xlkfinance.bms.web.controller.repayment;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.rpc.beforeloan.CalcOperDto;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;
import com.xlkfinance.bms.rpc.repayment.InterestChgApplyView;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RepayCgInterestDTO;
import com.xlkfinance.bms.rpc.repayment.RepayCgInterestView;
import com.xlkfinance.bms.rpc.repayment.RepayManageLoanService;
import com.xlkfinance.bms.rpc.repayment.RepaymentService;
import com.xlkfinance.bms.rpc.repayment.UploadFileService;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.controller.contract.ContractGenerator;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;
import com.xlkfinance.bms.web.util.FileDownload;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

/**
 * 类描述：
 * 创建人：gaoWen 
 * 创建时间：2015年3月12日 下午2:36:06
 */
@Controller
@RequestMapping("/repaymanageloan")
public class RepayManageLoanController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(RepayManageLoanController.class);

	// 还款管理/利率变更任务更新计划还款表
	@RequestMapping("/advMakeRepayCgInfo")
	public String advMakeRepayCgInfo( int projectId,int interestChgId,ModelMap model) {
		BaseClientFactory clientFactory = null;
		BaseClientFactory cf = null;
		BaseClientFactory cFt=null;
		Loan loan=null;
		RepaymentLoanInfo repayment =null;
		List<RepayCgInterestView> list=null;
		RepayCgInterestView  interest=null;
		try {
			//更具项目id查询贷款信息
			clientFactory=	getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			loan=client.getLoanInfobyProjectId(projectId);
			
			//更具利率变更id查询利率变更信息
			cf = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
			RepayManageLoanService.Client ct = (RepayManageLoanService.Client) cf.getClient();
			RepayCgInterestDTO interestDTO=new RepayCgInterestDTO();
			interestDTO.setInterestChgId(interestChgId);
			list = ct.selectLoanRequestInterestDetailbyProces(interestDTO);
			if(null!=list&&list.size()>0){
				interest=list.get(0);	
			}else{
				logger.error("==========利率变更查询出错=============");
				//防止空指针
				interest=new RepayCgInterestView();
			}
			if(null==loan||loan.getPid()<1){
				logger.error("==========贷款信息为空=============");
			}
			//更具贷款id查询更新后的贷款信息跳转到贷款试算页面
			cFt = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clt = (ProjectService.Client) cFt.getClient();
			repayment = clt.getRepaymentLoanInfo(loan.getPid());
			
			repayment.setLoanMgr(interest.getMonthLoanMgr());
			repayment.setLoanOtherFee(interest.getMonthLoanOtherFee());
			repayment.setLoanInterest(interest.getMonthLoanInterest());
			
			
			CalcOperDto calcOperDto=new CalcOperDto();
			calcOperDto.setMonthLoanMgr(interest.getMonthLoanMgr());
			calcOperDto.setMonthLoanOtherFee(interest.getMonthLoanOtherFee());
			calcOperDto.setMonthLoanInterest(interest.getMonthLoanInterest());
			calcOperDto.setPid(interestChgId);
		    //利率变更=3、	
			calcOperDto.setRepayType(3);
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		    String dateString = formatter.format(formatter.parse(interest.getRequestDttm()));
			calcOperDto.setOperRepayDt(dateString);
			int userId = getShiroUser().getPid();
			//	makeRepayMent(loan, 1,new CalcOperDto(0, 0, 0, 0, 0, "", 1,0,""));
			client.makeRepayMent(loan,userId, calcOperDto);
		} catch (Exception e) {
			logger.error("==========程序出错=============");
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory,cf,cFt);
		}
		model.put("repayment", repayment);
		model.put("loanId", loan.getPid());
		return "beforeloan/loan_calc";
		//outputJson(st, response);
	}

	// 还款管理/利率变更任务流完成时候利息变更生效
	@RequestMapping("/intRepayCgapplyInfoEnd")
	@ResponseBody
	public void intRepayCgapplyInfoEnd( RepayCgInterestDTO repayCgInterestDTO,HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		 int st=0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
			RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
		 st = client.intRepayCgapplyInfoEnd(repayCgInterestDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(st, response);
	}
	
	// 还款管理/利率变更跳转页面工作流跳转
	@RequestMapping("/repaydatilapplybyProcess")
	public String getRepayDatilIntCgApplybyProcess( RepayCgInterestDTO repayCgInterestDTO,ModelMap model) {
		BaseClientFactory clientFactory = null;
		List<RepayCgInterestView> list=null;
		 List<RepayCgInterestView> reqlist=null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
			RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
		 list = client.selectLoanInterestDetailbyProces(repayCgInterestDTO);
	     reqlist = client.selectLoanRequestInterestDetailbyProces(repayCgInterestDTO);
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
		if(null!=list&&list.size()>0){
			model.addAttribute("interest", list.get(0));
			model.addAttribute("loanId",  list.get(0).getLoanId());
			model.addAttribute("projectId", list.get(0).getProjectId());
			model.addAttribute("projectName", list.get(0).getProjectName());
			model.addAttribute("projectNum", list.get(0).getProjectNumber());
			model.addAttribute("interestChgId", repayCgInterestDTO.getInterestChgId());
		}
	if(null!=reqlist&&reqlist.size()>0){
			model.addAttribute("reqlist", reqlist.get(0));
		}else{
			reqlist=new ArrayList<RepayCgInterestView>();
			reqlist.add(new RepayCgInterestView());
			model.addAttribute("reqlist",reqlist.get(0));
		}
		return "repayment/datil_int_cg_apply";
	}
	// 还款管理/利率变更跳转页面
	@RequestMapping("/repaydatilapply")
	public String getRepayDatilIntCgApply( RepayCgInterestDTO repayCgInterestDTO,ModelMap model) {
		BaseClientFactory clientFactory = null;
		List<RepayCgInterestView> list=null;
		List<RepayCgInterestView> alist=null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
			RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
			InterestChgApplyView a = client.queryInterestChgId(repayCgInterestDTO.getProjectId());
				 list = client.selectLoanInterestDetail(repayCgInterestDTO);
				 if(a.getInterestChgId()!=0){//如果利率变更表里有数据的话，取最大的ID，也就是最后一条保存的数据 进行赋值 
					 alist = client.selectLoanRequestInterestDetailbyProcesByStatus(repayCgInterestDTO.setInterestChgId(a.getInterestChgId()));
					 if(alist.size()>0){
						 for (RepayCgInterestView repayCgInterestView : alist) {
							  list.get(0).setMonthLoanInterest(repayCgInterestView.getMonthLoanInterest());
							  list.get(0).setMonthLoanMgr(repayCgInterestView.getMonthLoanMgr());
							  list.get(0).setMonthLoanOtherFee(repayCgInterestView.getMonthLoanOtherFee());
							  list.get(0).setLiqDmgProportion(repayCgInterestView.getLiqDmgProportion());
							  list.get(0).setOverdueLoanInterest(repayCgInterestView.getOverdueLoanInterest());
							  list.get(0).setOverdueFineInterest(repayCgInterestView.getOverdueFineInterest());
							  list.get(0).setMisFineInterest(repayCgInterestView.getMisFineInterest());
							  list.get(0).setPrepayLiqDmgProportion(repayCgInterestView.getPrepayLiqDmgProportion());
							  list.get(0).setYearLoanInterest(repayCgInterestView.getYearLoanInterest());
							  list.get(0).setYearLoanMgr(repayCgInterestView.getYearLoanMgr());
							  list.get(0).setYearLoanOtherFee(repayCgInterestView.getYearLoanOtherFee());
							  list.get(0).setDayLoanInterest(repayCgInterestView.getDayLoanInterest());
							  list.get(0).setDayLoanMgr(repayCgInterestView.getDayLoanMgr());
							  list.get(0).setDayLoanOtherFee(repayCgInterestView.getDayLoanOtherFee());
						}
					 }
				 }
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
		if(null!=list&&list.size()>0){
			model.addAttribute("interest", list.get(0));
			model.addAttribute("loanId", list.get(0).getPId());
			model.addAttribute("projectId", list.get(0).getProjectId());
			model.addAttribute("projectName", list.get(0).getProjectName());
			model.addAttribute("projectNum", list.get(0).getProjectNumber());
		}
		model.addAttribute("interestChgId",-1);
		return "repayment/datil_int_cg_apply";
	}
	// 还款管理/利率变更跳转页面
		@RequestMapping("/repaytest")
		public String getrepaytest( ) {
		
			return "repayment/repay_test";
		}
		
		@RequestMapping(value = "saveData")
		@ResponseBody
		public void saveData(HttpServletRequest request,HttpServletResponse response){
			UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO = new UploadinstAdvapplyBaseDTO();
			Map<String, Object> map = new HashMap<String, Object>();
			BaseClientFactory clientFactory = null;
			ShiroUser shiroUser = getShiroUser();
			int userId = shiroUser.getPid();
			String path = null;
			int st = 0;
			String  status = "";

			try {
				request.setCharacterEncoding("UTF-8");
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_REPAYMENT, getUploadFilePath(), getFileSize(), getFileDateDirectory(),getUploadFileType());
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
				RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
				if ((Boolean) map.get("flag") == false) {
					logger.error("上传出错");
					status=	"文件上传失败or文件格式不合法";
				} else {
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						if (item.isFormField()) {
							if (item.getFieldName().equals("fileKinds")) {
								uploadinstAdvapplyBaseDTO.setFileKinds(ParseDocAndDocxUtil.getStringCode(item));
							} else if (item.getFieldName().equals("fileDesc")) {
								uploadinstAdvapplyBaseDTO.setFileDesc(ParseDocAndDocxUtil.getStringCode(item));
							} else if (item.getFieldName().equals("projectId")) {
								uploadinstAdvapplyBaseDTO.setProjectId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}
							 else if (item.getFieldName().equals("interestChgId")) {
									uploadinstAdvapplyBaseDTO.setInterestChgId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
								}
						} else {
							if (item.getFieldName().equals("file1"))
								uploadinstAdvapplyBaseDTO.setFileSize((int) item.getSize());
								String fileFullName = item.getName().toLowerCase();
								int dotLocation = fileFullName.lastIndexOf(".");
								uploadinstAdvapplyBaseDTO.setFileName(fileFullName.substring(0, dotLocation));
								String fileType = fileFullName.substring(dotLocation).substring(1);
								uploadinstAdvapplyBaseDTO.setFileType(fileType);
						}
					}
					String uploadDttm = DateUtil.format(new Date());
				uploadinstAdvapplyBaseDTO.setFileDttm(uploadDttm);
				path = String.valueOf(map.get("path"));
				/*String pathname = path.split("/")[path.split("/").length - 1];
				uploadinstAdvapplyBaseDTO.setFileName(pathname);
				String fileType = pathname.substring(pathname.lastIndexOf(".")).substring(1);
				uploadinstAdvapplyBaseDTO.setFileType(fileType);*/
				// .substring( 0,path.length()-pathname.length()-1)
				uploadinstAdvapplyBaseDTO.setFilePath(path);
				uploadinstAdvapplyBaseDTO.setUploadUserid(userId + "");
				uploadinstAdvapplyBaseDTO.setFileKinds("1");//1为利率变更申请资料 唯一
				st = client.uploadinstCgapply(uploadinstAdvapplyBaseDTO);
				status=	"文件上传成功";
				}
				} catch (Exception e) {
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
			outputJson(status, response);
		}
		
		/**
		 * 
		  * @Description: 利率变更申请新增
		  * @param repayCgInterestDTO
		  * @param response
		  * @author: Zhangyu.Hoo
		  * @date: 2015年6月26日 下午5:40:30
		 */
		@RequestMapping("/insertRepayCgapplyInfo")
		@ResponseBody
		public void insertRepayCgapplyInfo( RepayCgInterestDTO repayCgInterestDTO,HttpServletResponse response) {
			BaseClientFactory clientFactory = null;
			 int st=0;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
				RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
			 st = client.insertRepayCgapplyInfo(repayCgInterestDTO);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (clientFactory != null){
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
			outputJson(st, response);
		}
		
		//下载文件
		@RequestMapping(value = "downloadadvapply")
		@ResponseBody
		public void downloadData(HttpServletRequest request,HttpServletResponse response,String path){
			try {
				FileDownload.downloadLocal(response, request, path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@RequestMapping(value = "/delectFileCgapply")
		public void delectFileCgapply(@RequestParam("pId") String pId,HttpServletResponse response) throws Exception {
			int  st = 0;
			BaseClientFactory clientFactory = null; 
			Map<String,Object> tojson = new HashMap<String,Object>();
			try{
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
				RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
				st = client.delectFilebyId(pId);
				if(st>0){
					tojson.put("delStatus", "删除资料成功");
				}else{
					tojson.put("delStatus","删除资料失败" );
				}
			}catch(Exception e){
				logger.error("删除资料文件失败出错" + e.getMessage());
				e.printStackTrace();
			}finally{
				if (clientFactory != null) {
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
			outputJson(tojson, response);
		}	
		

		@RequestMapping(value = "/updateRepayCgapplyInfo.action")
		public void updateRepayCgapplyInfo( RepayCgInterestDTO repayCgInterestDTO,HttpServletResponse response) throws Exception {
			BaseClientFactory clientFactory = null;
			 int st=0;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
				RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
			 st = client.updateRepayCgapplyInfo(repayCgInterestDTO);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (clientFactory != null){
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
			outputJson(st, response);
		}
		//查询上传文件
		@RequestMapping(value = "/queryRepayCgapplyFile.action")
		public void queryRepayCgapplyFile(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO, HttpServletResponse response) throws Exception {
			BaseClientFactory clientFactory = null;
			List<RegAdvapplyFileview> list = new ArrayList<RegAdvapplyFileview>();
			Map<String, Object> map = new HashMap<String, Object>();
			int count = 0;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
				RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
				ShiroUser user=getShiroUser();
				uploadinstAdvapplyBaseDTO.setPmUserId(user.getPid());
				list = client.queryRepayCgapplyFile(uploadinstAdvapplyBaseDTO);
				count = client.queryRepayCgapplyFileCount(uploadinstAdvapplyBaseDTO);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (clientFactory != null){
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
			// 输出
			map.put("rows", list);
			map.put("total", count);
			outputJson(map, response);
		}	
		
		@RequestMapping(value = "/deleteProjectbyinterestChgId.action")
		public void deleteProjectbyinterestChgId(String interestChgIds,String projectIds, HttpServletResponse response) throws Exception {
			BaseClientFactory clientFactory = null;
			int  st=0;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
				RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
				st = client.deleteProjectbyinterestChgId(interestChgIds,projectIds);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (clientFactory != null){
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
			outputJson(st, response);
		}	
             //判断是否有重复的利率变更申请
		@RequestMapping(value = "/checkinterestChgByProjectId.action")
		public void checkinterestChgByProjectId(int projectId, HttpServletResponse response) throws Exception {
			BaseClientFactory clientFactory = null;
			int  st=0;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
				RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
				st = client.checkpreRepayByProjectId(projectId);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (clientFactory != null){
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
		 * 
		  * @Description: 生成申请书
		  * @param interestChgId
		  * @param request
		  * @param response
		  * @author:GW
		  * @date: 2015年4月21日 下午5:23:31
		 */
		@RequestMapping(value="makeCgApplyFile")
		public void makeCgApplyFile(int interestChgId,HttpServletRequest request,HttpServletResponse response){
			BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
			BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			InterestChgApplyView sf = new InterestChgApplyView();
			Map<String,String> map = new HashMap<String,String>();
			List<String> list = new ArrayList<String>();
			TemplateFile template = new TemplateFile();
			try{
				RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
				sf = client.makeCgApplyFile(interestChgId);
				TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
				template = cl.getTemplateFile("LLBGSQS");//利率变更申请书
		          String[] s = ParseDocAndDocxUtil.getFiledName(sf);
				String docText = ParseDocAndDocxUtil.parseDocumet(template.getFileUrl(), request);
				list = ParseDocAndDocxUtil.getDocumentSign(docText);
				List<TempLateParmDto> templateParamList = new ArrayList<TempLateParmDto>();
				for(int i=0;i<list.size();i++){
					if(!list.get(i).equals("")){
						String biaoshi = list.get(i).replace("@", "");
						for(int j=0;j<s.length;j++){
							if(biaoshi.equals(s[j])){
								TempLateParmDto tempLateParmDto = new TempLateParmDto();
								if(s[j].substring(s[j].length()-3,s[j].length()).equals("Amt")){
									tempLateParmDto.setValConvertFlag(3);
									tempLateParmDto.setMatchFlag(list.get(i));
								}else if(s[j].substring(s[j].length()-4,s[j].length()).equals("Dttm")){
									tempLateParmDto.setValConvertFlag(2);
									tempLateParmDto.setMatchFlag(list.get(i));
								}else{
									tempLateParmDto.setValConvertFlag(0);
									tempLateParmDto.setMatchFlag(list.get(i));
								}
								templateParamList.add(tempLateParmDto);
								map.put(list.get(i), ExcelExport.getFieldValueByName(s[j], sf));
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
				// TODO 新修改合同动态表格
				boolean b = contractGenerator.generate(this,templateParamList, path, file.getPath(), map, null,null);
				if(b==true){
					FileDownload.downloadLocalFile(response, request, createTime+"/"+realFileName,"利率变更申请说明书");
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if (c != null) {
					try {
						c.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}finally{
						if (clientFactory != null) {
							try {
								clientFactory.destroy();
							} catch (ThriftException e) {
								e.printStackTrace();
							}
						}
					}
				}
				
			}
		}
		
		/**
		 * 
		  * @Description: 编辑上传信息
		  * @param projectId
		  * @param response
		  * @throws Exception
		  * @author: GW
		  * @date: 2015年3月27日 下午5:13:33
		 */
		@RequestMapping(value = "updateloadfileinfo")
		public void updateLoadFileInfo(String  fileDesc, int interestChgId,int fileId ,HttpServletResponse response){
			BaseClientFactory clientFactory = null;
			UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO=new UploadinstAdvapplyBaseDTO();
			uploadinstAdvapplyBaseDTO.setFileDesc(fileDesc);
			//uploadinstAdvapplyBaseDTO.setFileKinds(fileKinds+"");
			uploadinstAdvapplyBaseDTO.setInterestChgId(interestChgId);
			uploadinstAdvapplyBaseDTO.setFileId(fileId);
			int st=0;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "UploadFileService");
				UploadFileService.Client client = (UploadFileService.Client) clientFactory.getClient();
			st=	client.updateACgLoadFileInfo(uploadinstAdvapplyBaseDTO);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage());
				}
			}finally {
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
		 * 
		  * @Description: 利率变更列表查看操作
		  * @param repayCgInterestDTO
		  * @param model
		  * @return
		  * @date: 2015年6月19日 下午3:04:56
		 */
		@RequestMapping("/repaydatilInfo")
		public String repaydatilInfo( RepayCgInterestDTO repayCgInterestDTO,ModelMap model) {
			BaseClientFactory clientFactory = null;
			List<RepayCgInterestView> list=null;
			 List<RepayCgInterestView> reqlist=null;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
				RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
			 list = client.queryLoanRes(repayCgInterestDTO);
			 if(list.size()==0){
				 list = client.selectLoanInterestDetailbyProces(repayCgInterestDTO);
			 }
		     reqlist = client.selectLoanRequestInterestDetailbyProces(repayCgInterestDTO);
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
			if(null!=list&&list.size()>0 ){
				model.addAttribute("interest", list.get(0));
				model.addAttribute("loanId",  list.get(0).getLoanId());
				model.addAttribute("projectId", list.get(0).getProjectId());
				model.addAttribute("projectName", list.get(0).getProjectName());
				model.addAttribute("projectNum", list.get(0).getProjectNumber());
				model.addAttribute("interestChgId", repayCgInterestDTO.getInterestChgId());
			}
			if(null!=reqlist&&reqlist.size()>0){
				model.addAttribute("reqlist", reqlist.get(0));
				/*if(list.size()<=0){//如果利率变更前有数据就不进行赋值
					model.addAttribute("interest", reqlist.get(0));
					model.addAttribute("loanId",  reqlist.get(0).getLoanId());
					model.addAttribute("projectId", reqlist.get(0).getProjectId());
					model.addAttribute("projectName", reqlist.get(0).getProjectName());
					model.addAttribute("projectNum", reqlist.get(0).getProjectNumber());
					model.addAttribute("interestChgId", repayCgInterestDTO.getInterestChgId());
				}*/
			}else{
				reqlist=new ArrayList<RepayCgInterestView>();
				reqlist.add(new RepayCgInterestView());
				model.addAttribute("reqlist",reqlist.get(0));
			}
			return "repayment/datil_int_cg_apply";
		}
		
		/**
		 * 
		  * @Description: 新增利率变更贷审会意见信息
		  * @param project
		  * @param response
		  * @author: Zhangyu.Hoo
		  * @date: 2015年6月27日 上午10:30:08
		 */
		@RequestMapping(value = "addProcedures", method = RequestMethod.POST)
		public void addProcedures(RepayCgInterestDTO repayCgInterestDTO, HttpServletResponse response) {
			Json j = super.getSuccessObj();
			BaseClientFactory clientFactory = null;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
				RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
				int rows = client.saveProcedures(repayCgInterestDTO);
				if (rows != 0) {
					// 成功的话,就做业务日志记录
					recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "保存利率变更贷审会信息");
					j.getHeader().put("success", true);
					j.getHeader().put("msg", rows);
				} else {
					// 失败的话,做提示处理
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "保存失败,请重新操作!");
				}
			} catch (ThriftServiceException tse) {
				tse.printStackTrace();
				j.getHeader().put("success", false);
				j.getHeader().put("msg", tse.message);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug("保存贷审会信息:" + e.getMessage());
				}
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存失败,请重新操作!");
			} finally {
				if (clientFactory != null) {
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
			// 输出
			outputJson(j, response);
		}
		
		/**
		 * 
		  * @Description: 查询利率变更信息
		  * @param projectId
		  * @param response
		  * @author: Zhangyu.Hoo
		  * @date: 2015年6月27日 上午11:35:55
		 */
		@RequestMapping(value = "getRepayCgInterestByPid")
		public void getRepayCgInterestByPid(int interestChgId, HttpServletResponse response) {
			// 创建对象
			BaseClientFactory clientFactory = null;
			RepayCgInterestView view = new RepayCgInterestView();
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayManageLoanService");
				RepayManageLoanService.Client client = (RepayManageLoanService.Client) clientFactory.getClient();
				RepayCgInterestDTO repayCgInterestDTO = new RepayCgInterestDTO();
				repayCgInterestDTO.setInterestChgId(interestChgId);
				List<RepayCgInterestView> list = client.selectLoanInterestDetailbyProces(repayCgInterestDTO);
				if(null != list && list.size() > 0 ){
					view = list.get(0);
				}
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug("根据项目ID查询利率变更详细信息:" + e.getMessage());
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
			// 输出
			outputJson(view, response);
		}
}
