package com.xlkfinance.bms.server.credits.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.WorkflowIdConstant;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.beforeloan.CreditLimitRecord;
import com.xlkfinance.bms.rpc.beforeloan.CreditsDTO;
import com.xlkfinance.bms.rpc.beforeloan.CreditsLineDTO;
import com.xlkfinance.bms.rpc.beforeloan.CreditsService.Iface;
import com.xlkfinance.bms.rpc.beforeloan.ProjectAssure;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectAssureMapper;
import com.xlkfinance.bms.server.credits.mapper.CreditsDTOMapper;

/**
 * 
 * @ClassName: CreditsServiceImpl
 * @Description: 额度管理service
 * @author: Cai.Qing
 * @date: 2015年3月19日 下午5:10:08
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("creditsServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.CreditsService")
public class CreditsServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(CreditsServiceImpl.class);

	@Resource(name = "creditsDTOMapper")
	private CreditsDTOMapper creditsDTOMapper;

	@Resource(name = "projectAssureMapper")
	private ProjectAssureMapper projectAssureMapper;
	
	@Resource(name = "workflowServiceImpl")
	private WorkflowService.Iface workflowServiceImpl;

	@Override
		public  int  getCreditsLineProjectId(int projectId) throws ThriftServiceException, TException {
		List<CreditsLineDTO> lis = creditsDTOMapper.getCreditsLineProjectId(projectId);
		int result = 1;
		for (CreditsLineDTO creditsLineDTO : lis) {
			if(creditsLineDTO.getRequestStatus()!=4 && creditsLineDTO.getRequestStatus()!=3){
				result =2;
			}
		}
			return result;
		}

	
	@Override
	public List<CreditsDTO> getAllCredits(CreditsDTO creditsDTO) throws TException {
		List<CreditsDTO> list = new ArrayList<CreditsDTO>();
		try {
			list = creditsDTOMapper.getAllCredits(creditsDTO);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.info("额度查询列表:" + e.getMessage());
			}
		}
		return list;
	}
	/**
	 * 
	  * @Description: 获取记录数
	  * @param creditsDTO
	  * @return
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年6月30日 下午3:39:11
	 */
	@Override
	public int getAllCreditsDTOCount(CreditsDTO creditsDTO) throws TException {
		int count = 0;
		try {
			count = creditsDTOMapper.getAllCreditsDTOCount(creditsDTO);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.info("查询授信项目的数量:" + e.getMessage());
			}
		}
		return count;
	}
	/**
	 * 
	  * @Description: 额度保存，修改操作
	  * @param creditLimitRecord
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年6月30日 下午3:38:16
	 */
	@Override
	public int saveFreeze(CreditLimitRecord creditLimitRecord) throws ThriftServiceException, TException {
		int flag = 0;
		int pid = 0;
		try {	//如果id为空进入修改否则进去保存
			if (creditLimitRecord != null && creditLimitRecord.getPid() > 0) {
				//creditLimitRecord.setStatus(1);
				creditsDTOMapper.updateByPrimaryKey(creditLimitRecord);
				flag = 1;
			} else if(creditLimitRecord != null && creditLimitRecord.getPid() <= 0){
				creditLimitRecord.setCreDttm(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
				creditsDTOMapper.insert(creditLimitRecord);
				pid = creditLimitRecord.getPid();//返回保存好的id
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(flag == 0 ? ExceptionCode.CREDIT_RECORD_ADD : ExceptionCode.CREDIT_RECORD_UPDATE, "额度冻结、解冻 " + (flag == 0 ? "新增" : "修改") + "失败！");
		}
		return pid;
	}
	/**
	 * 
	  * @Description: 授信项目信息公共页面查询方法
	  * @param creditsDTO
	  * @return
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年7月14日 上午11:32:25
	 */
	@Override
	public List<CreditsDTO> getCreditsInfo(CreditsDTO creditsDTO) throws TException {
		List<CreditsDTO> list = new ArrayList<CreditsDTO>();
		try {
			int isHoop = creditsDTOMapper.getIsHoopById(creditsDTO.getProjectId());
			if(isHoop!=0){
				creditsDTO.setIsHoop(isHoop);
			}
			list = creditsDTOMapper.getCreditsInfo(creditsDTO);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.info("额度查询列表:" + e.getMessage());
			}
		}
		return list;
	}
	/**
	 * 
	  * @Description: 查询纪录结果集
	  * @param pid
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年7月14日 上午11:32:57
	 */
	public CreditLimitRecord getCreditLimitRecord(int pid) throws ThriftServiceException, TException {
		try {
			if (pid > 0) {
				return (CreditLimitRecord) creditsDTOMapper.selectByPrimaryKey(pid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_RECORD_QUERY, "额度冻结、解冻信息查询失败！");
		}
		return null;
	}

		/**
	  * @Description:  额度提取列表
	  * @param creditsLineDTO
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年6月30日 下午3:41:33
	 */
	public List<CreditsLineDTO> getCreditsLine(CreditsLineDTO creditsLineDTO) throws ThriftServiceException, TException {

		return creditsDTOMapper.getCreditsLine(creditsLineDTO);
	}
	/**
	 * 
	  * @Description: 额度提取总数（xuweihao）
	  * @param creditsLineDTO
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年6月30日 下午3:41:11
	 */
	@Override
	public int getCreditsLineTotal(CreditsLineDTO creditsLineDTO) throws ThriftServiceException, TException {

		return creditsDTOMapper.getCreditsLineTotal(creditsLineDTO);
	}
	/**
	 * 
	  * @Description: 额度提取删除（xuweihao）
	  * @param pidArray
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年6月30日 下午3:40:01
	 */
	@Override
	public boolean deleteCreditsLine(String pidArray) throws ThriftServiceException, TException {
		
		boolean del = true;
		String[] pids = pidArray.split(",");
		for (String string : pids) {
			int pid = Integer.parseInt(string);
			workflowServiceImpl.deleteProcessInstance(pid,-1,WorkflowIdConstant.CREDIT_WITH_DRAWAL_REQUEST_PROCESS);
			del = creditsDTOMapper.deleteCreditsLine(pid);
		}
		return del;
	}

	/**
	 * 
	 * @Description: 根据项目ID查询所有的保证信息
	 * @param projectId
	 *            项目ID
	 * @return 保证信息集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:12:11
	 */
	@Override
	public List<ProjectAssure> getAssureByProjectId(int projectId) throws TException {
		List<ProjectAssure> list = new ArrayList<ProjectAssure>();
		try {
			list = projectAssureMapper.getAssureByProjectId(projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.info("查询保证人信息:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 根据项目ID查询个人保证信息
	 * @param projectIds
	 *            项目ID(1,2)
	 * @return 保证信息集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:10:18
	 */
	@Override
	public List<ProjectAssure> getProjectAssureByPersonal(String projectIds) throws TException {
		List<ProjectAssure> list = new ArrayList<ProjectAssure>();
		try {
			// 组装查询条件HashMap
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectIds", projectIds.split(","));
			list = projectAssureMapper.getProjectAssureByPersonal(map);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.info("查询个人保证人信息:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 根据项目ID查询企业法人保证信息
	 * @param projectIds
	 *            项目ID(1,2)
	 * @return 保证信息集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:10:51
	 */
	@Override
	public List<ProjectAssure> getProjectAssureByEnterprise(String projectIds) throws TException {
		List<ProjectAssure> list = new ArrayList<ProjectAssure>();
		try {
			// 组装查询条件HashMap
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectIds", projectIds.split(","));
			list = projectAssureMapper.getProjectAssureByEnterprise(map);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.info("查询企业法人保证人信息:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 新增保证信息
	 * @param projectAssure
	 *            保证对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:11:16
	 */
	@Override
	public int addProjectAssure(ProjectAssure projectAssure) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 状态默认为1
			projectAssure.setStatus(1);
			// 所有人员
			String[] refIds = projectAssure.getRefIds().split(",");
			// 循环插入数据
			for (int i = 0; i < refIds.length; i++) {
				// 强转并赋值
				projectAssure.setRefId(Integer.parseInt(refIds[i]));
				// 插入数据
				rows = projectAssureMapper.insert(projectAssure);
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.info("新增保证信息:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 删除保证信息
	 * @param refIds
	 *            保证对象PID拼接(1,1,1)
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:13:01
	 */
	@Override
	public int deleteProjectAssure(String refIds) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 把传过来的数据劈开成一个数组作为参数传过去
			String[] strArr = refIds.split(",");
			rows = projectAssureMapper.deleteProjectAssure(strArr);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.info("删除保证信息:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 根据项目ID查询授信合同的信息和共同借款人信息
	 * @param projectId
	 *            项目ID
	 * @return 授信合同信息和共同借款人集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月22日 下午2:34:45
	 */
	@Override
	public List<CreditsDTO> getProjectAcctAndPublicManByProjectId(int projectId) throws TException {
		List<CreditsDTO> list = new ArrayList<CreditsDTO>();
		try {
			// 获取数据
			list = creditsDTOMapper.getProjectAcctAndPublicManByProjectId(projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.info("根据项目ID查询授信合同的信息和共同借款人信息:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	  * @Description: 财务对账成功要保存数据
	  * @param creditLimitRecord
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月30日 下午3:40:39
	 */
	@Override
	public int saveCreditInfo(CreditLimitRecord creditLimitRecord)
		{
		
		try
		{
			int creditId = creditsDTOMapper.getCreditId(creditLimitRecord.getLoanId());
			creditLimitRecord.setCreditId(creditId);
			creditLimitRecord.setReason("财务对账还款");
			creditLimitRecord.setCreDttm(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
			creditLimitRecord.setStatus(1);
			creditLimitRecord.setCreditUseType(5);//额度调整类型（1=授信、2=使用、3=冻结、4=解冻 、5=还款）
			creditsDTOMapper.insert(creditLimitRecord);
		}
		catch(Exception e)
		{
			logger.warn("根据loanId["+creditLimitRecord.getLoanId()+"]贷款Id["+creditLimitRecord.getAmt()+"]对账金额:" + e.getMessage());
		}
		
		 
		return 0;
   	}

 
	 
}
