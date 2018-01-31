package com.xlkfinance.bms.web.schedule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentHisService;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentIndexDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnDTO;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysRole;
import com.xlkfinance.bms.rpc.system.SysRoleService;
import com.xlkfinance.bms.rpc.system.SysUserDto;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.ApiUtil;
import com.xlkfinance.bms.web.util.PropertiesUtil;

/**
 * 系统查档定时任务
 * 
 * @author chenzhuzhen
 * @date 2017年1月13日 上午9:46:18
 */
@Service("checkDocumentJob")
public class CheckDocumentJob extends BaseController {

	private Logger logger = LoggerFactory.getLogger(CheckDocumentJob.class);

//	 @Scheduled(cron = "0 15 0 ? * *")
	// 每天 凌晨10分开始执行
	public void execute() {
		
		synchronized (this) {
			logger.info("系统查档定时调度任务开始" + DateUtil.format(DateUtil.getToday()));
			Long startTime=System.currentTimeMillis();
			
			BaseClientFactory deptClientFactory = null;
			BaseClientFactory estateClientFactory = null;
			BaseClientFactory checkDocHisFactory = null;
			BaseClientFactory handleClientFactory = null;
			BaseClientFactory sysRoleClientFactory = null;
			try {
				//查档
				deptClientFactory = getFactory(BusinessModule.MODUEL_INLOAN,"IntegratedDeptService");
				IntegratedDeptService.Client deptClient = (IntegratedDeptService.Client) deptClientFactory.getClient();
				//房产
				estateClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN,"BizProjectEstateService");
				BizProjectEstateService.Client estateClient = (BizProjectEstateService.Client) estateClientFactory.getClient();
				//查档历史
				checkDocHisFactory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckDocumentHisService");
		        CheckDocumentHisService.Client checkDocHisClient = (CheckDocumentHisService.Client) checkDocHisFactory.getClient();
		        
		        //预警处理
		        handleClientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizHandleService");
		        BizHandleService.Client handleClient = (BizHandleService.Client) handleClientFactory.getClient();
		        
		        //系统用户
		        sysRoleClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		        SysRoleService.Client sysRoleClient = (SysRoleService.Client) sysRoleClientFactory.getClient();
		        
				
				//-------------------------------------------------------
				CheckDocumentIndexDTO query = new CheckDocumentIndexDTO();
				// 己放款
				query.setRecFeeStatus(Constants.REC_STATUS_ALREADY_LOAN);
				// 未回款
				query.setLoanRepaymentStatus(Constants.REPAYMENT_STATUS_1);
				//过滤掉万通
				query.setProjectSource(-1);
				query.setPage(-1);
	
				List<CheckDocumentIndexDTO> checkDocumentIndexList = null;
				List<BizProjectEstate> estateList = null;
				int projectCount = 0;	//项目数
				int houseCount = 0;		//房产数
				int houseSuccessCount = 0;	//成功数
				int handleDifferWarnCount = 0;	//添加预警数量
				// 查询要件查档列表
				checkDocumentIndexList = deptClient.queryCheckDocumentIndex(query);
				if (checkDocumentIndexList != null && checkDocumentIndexList.size() > 0) {
					Date nowDate = new Date();
					Timestamp nowTime = new Timestamp(nowDate.getTime());
					
			        //系统查档异常通知用户
			        List<SysLookupVal> lookupVals = getSysLookupValByLookType(Constants.SYS_LOOKUP_CHECK_DOC_EXCEPTION_NOTICE_ROLE);
			        List<Integer> userIdList = new ArrayList<Integer>();
			        //发送给需要通知的角色用户
			        for (SysLookupVal v : lookupVals) {
			            SysRole querySysRole =  new SysRole();
			            querySysRole.setRoleCode(v.getLookupVal());
			            List<SysUserDto> userList = sysRoleClient.getUserListByRoleCode(querySysRole);
			            for (SysUserDto indexObj : userList) {
			            	userIdList.add(indexObj.getPid());
			            }
			        }
					
	
					//查档服务器地址
					String apiUrl = PropertiesUtil.getValue(Constants.CHECK_DOC_API_URL);
					//系统管理员id
					String systemAdminPid = PropertiesUtil.getValue(Constants.SYSTEM_ADMIN_PID);
					
					String apiResultStr = "";
					JSONObject apiResultJson = null;
					CheckDocumentDTO insertCheckDocument = null;
					String checkDocResult = "";	//查档内容
	
					for (CheckDocumentIndexDTO checkDoc : checkDocumentIndexList) {
						//经办人
						int pmUserId = checkDoc.getPmUserId();
						
						projectCount ++;
						estateList = estateClient.getAllByProjectId(checkDoc.getProjectId());
						if(estateList != null && estateList.size() > 0 ){
							for(BizProjectEstate estate : estateList){
								houseCount++;
								try{
									//调用接口执行查档
									apiResultStr = ApiUtil.submitCheckDoc(apiUrl, Constants.CHECK_DOC_QUERY_TYPE_1, 
											Constants.CHECK_DOC_CERT_TYPE_1, 
											estate.getHousePropertyCard(), 
											checkDoc.getSellerName().split(",")[0], null);
									
									//测试begin----------
	/*								
									JSONObject testJson= new JSONObject();
									testJson.put("code", "ok");
									
									JSONObject testDataJson= new JSONObject();
									testDataJson.put("result", "产权状态为：查封");
									testJson.put("data", testDataJson);
									
									apiResultStr = testJson.toString();*/
									//测试end----------
									
									
									if(!StringUtil.isBlank(apiResultStr) ){
										apiResultJson = JSONObject.parseObject(apiResultStr);
										if("ok".equals(apiResultJson.getString("code"))){
											checkDocResult = apiResultJson.getJSONObject("data").getString("result");
											
											houseSuccessCount++;
											insertCheckDocument = new CheckDocumentDTO();
											insertCheckDocument.setProjectId(checkDoc.getProjectId());
											insertCheckDocument.setHouseId(estate.getHouseId());
											insertCheckDocument.setCheckDate(nowTime.toString());
											insertCheckDocument.setCheckWay(Constants.CHECK_WAY_SYSTEM);
											insertCheckDocument.setCreaterId(StringUtil.isBlank(systemAdminPid) ? 0 : (Integer.parseInt(systemAdminPid)));
											insertCheckDocument.setCreaterDate(nowTime.toString());
											insertCheckDocument.setUpdateId(StringUtil.isBlank(systemAdminPid) ? 0 : (Integer.parseInt(systemAdminPid)));
											insertCheckDocument.setUpdateDate(nowTime.toString());
											insertCheckDocument.setRemark(checkDocResult);
	
											//查档状态 -正则匹配
											if(checkDocResult.contains("没有找到匹配的房产记录")){
												insertCheckDocument.setCheckStatus(Constants.CHECK_DOCUMENT_STATUS_3);
											}else if(checkDocResult.contains("该权利人没有房产")){
												insertCheckDocument.setCheckStatus(Constants.CHECK_DOCUMENT_STATUS_4);
											}else{
												Map<Integer, String> statusMap = Constants.CHECK_DOCUMENT_STATUS_MAP;
												for (Entry entry : statusMap.entrySet()) {
													if(isCheckStatus(checkDocResult, entry.getValue().toString())){
														insertCheckDocument.setCheckStatus((int)entry.getKey());
													}
												}
											}
											
											if(insertCheckDocument.getCheckStatus() == 0 ){
												insertCheckDocument.setCheckStatus(Constants.CHECK_DOCUMENT_STATUS_3);
											}
											
											checkDocHisClient.insert(insertCheckDocument);
											//----------------------------预警处理begin--------------------------------------
											
											if(isCheckStatus(checkDocResult,"查封") || isCheckStatus(checkDocResult,"抵押查封") 
													|| isCheckStatus(checkDocResult,"轮候查封")){
												
												HandleDifferWarnDTO queryHandle = null;
												HandleDifferWarnDTO insertHandle= null;
												if(!CollectionUtils.isEmpty(userIdList)){
													for (Integer userId : userIdList) {
														queryHandle=new HandleDifferWarnDTO();
														queryHandle.setProjectId(checkDoc.getProjectId());
														queryHandle.setHandleAuthor(userId);								//用户
														queryHandle.setHandleType(Constants.WARN_HANDLE_TYPE_3);		//系统查档
														//queryHandle.setStatus(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_STATUS);	//未处理
														//查询预警处理数量
														int handleCount = handleClient.getHandleDifferWarnTotal(queryHandle);
														//查询历史预警处理数量
														int hisHandleCount = handleClient.getHisHandleDifferWarnCount(queryHandle);
														
														//都不存在才需要添加
														if(handleCount == 0  && hisHandleCount == 0 ){
															insertHandle= new HandleDifferWarnDTO();
															insertHandle.setStatus(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_STATUS);
															insertHandle.setHandleAuthor(userId);
															insertHandle.setCreateDate(nowTime.toString());
															insertHandle.setProjectId(checkDoc.getProjectId());
															insertHandle.setProjectName(checkDoc.getProjectName());
															insertHandle.setDiffer(0);
															insertHandle.setHandleDynamicId(0);
															insertHandle.setHandleType(com.xlkfinance.bms.common.constant.Constants.WARN_HANDLE_TYPE_3);
															handleClient.addHandleDifferWarn(insertHandle);
															handleDifferWarnCount++;
														}
													}
												}
												//经办人
												if( pmUserId > 0 ){
													queryHandle=new HandleDifferWarnDTO();
													queryHandle.setProjectId(checkDoc.getProjectId());
													queryHandle.setHandleAuthor(pmUserId);								//用户
													queryHandle.setHandleType(Constants.WARN_HANDLE_TYPE_3);		//系统查档
													//queryHandle.setStatus(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_STATUS);	//未处理
													//查询预警处理数量
													int handleCount = handleClient.getHandleDifferWarnTotal(queryHandle);
 
													//查询历史预警处理数量
													int hisHandleCount = handleClient.getHisHandleDifferWarnCount(queryHandle);
													
													//都不存在才需要添加
													if(handleCount == 0  && hisHandleCount == 0 ){
														insertHandle= new HandleDifferWarnDTO();
														insertHandle.setStatus(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_STATUS);
														insertHandle.setHandleAuthor(pmUserId);
														insertHandle.setCreateDate(nowTime.toString());
														insertHandle.setProjectId(checkDoc.getProjectId());
														insertHandle.setProjectName(checkDoc.getProjectName());
														insertHandle.setDiffer(0);
														insertHandle.setHandleDynamicId(0);
														insertHandle.setHandleType(com.xlkfinance.bms.common.constant.Constants.WARN_HANDLE_TYPE_3);
														handleClient.addHandleDifferWarn(insertHandle);
														handleDifferWarnCount++;
													}
												}
											}
											//----------------------------预警处理end--------------------------------------
										}
									}
								}catch(Exception e){
									logger.error("调用查档接口错误，houseId:"+estate.getHouseId(),e);
								}
							}
						}
						
					}
				}
				Long endTime=System.currentTimeMillis();
				logger.info("系统查档定时调度成功,总项目数： " + projectCount + ", 总房产数："+houseCount
						+",总成功数:"+houseSuccessCount+" ,"+
						"添加预警数量："+handleDifferWarnCount+",耗费时间:  "+ ((endTime-startTime)/60000)+"分钟");
				
			} catch (Exception e) {
				logger.info("系统查档定时调度任务异常" + ExceptionUtil.getExceptionMessage(e));
			} finally {
				destroyFactory(deptClientFactory);
				destroyFactory(estateClientFactory);
				destroyFactory(checkDocHisFactory);
				destroyFactory(handleClientFactory);
				destroyFactory(sysRoleClientFactory);
			}
			logger.info("系统查档定时调度任务结束" + DateUtil.format(DateUtil.getToday()));
		}
	}
	
	/**
	 * 是否匹配查档状态 
	 * @param checkDocResult	查档返回内容
	 * @param checkStatusStr	查档状态名称
	 * @return
	 */
	public static  boolean isCheckStatus(String checkDocResult,String checkStatusStr){
		Matcher m = Pattern.compile("(产权状态为：.*?("+checkStatusStr+"){1})",Pattern.DOTALL).matcher(checkDocResult);
		if (m.find()) {
			String result = m.group(1);
			if(!StringUtil.isBlank(result)){
				return true;
			}
		} 
		return false;
	}
	
	public static void main(String[] args) {
		String str = "权利人在南山有房产，产权状态为：查封  抵押权人：珠海华润银行股份有限公司深圳";
		
		System.out.println(isCheckStatus(str,"查封")); 
		
		 
		
	}
}
