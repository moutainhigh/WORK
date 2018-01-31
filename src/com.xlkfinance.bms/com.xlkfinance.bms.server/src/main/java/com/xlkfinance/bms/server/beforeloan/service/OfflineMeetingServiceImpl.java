package com.xlkfinance.bms.server.beforeloan.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.beforeloan.BizMeetingMinutesFile;
import com.xlkfinance.bms.rpc.beforeloan.BizMeetingMinutesMember;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectMeeting;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectMeetingDTO;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectMeetingRST;
import com.xlkfinance.bms.rpc.beforeloan.OfflineMeetingService.Iface;
import com.xlkfinance.bms.rpc.beforeloan.OrganizationCommission;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.server.beforeloan.mapper.OfflineMeetingMapper;

/**
 *   
 * 
 * @Title: OfflineMeetingServiceImpl.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Ant.Peng  
 * @date 2015年2月5日 下午8:14:27
 * @version V1.0  
 */
@SuppressWarnings("unchecked")
@Service("offlineMeetingServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.OfflineMeetingService")
public class OfflineMeetingServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(OfflineMeetingServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "offlineMeetingMapper")
	private OfflineMeetingMapper offlineMeetingMapper;

	/**
	 * 
	 * @Description: 保存数据线下决议会议纪要
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveBizProjectMeeting(BizProjectMeeting bizProjectMeeting) throws ThriftServiceException, TException {
		try {
			String offlineMeetingNum = offlineMeetingMapper.getBizProjectMeetingNum(DateUtil.format(new Date()));
			bizProjectMeeting.setMeetingNum(offlineMeetingNum);
			offlineMeetingMapper.saveBizProjectMeeting(bizProjectMeeting);
			return bizProjectMeeting.getPid();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "保存插入数据线下决议会议纪要信息失败！");
		}
	}

	/**
	 * 
	 * @Description: 保存数据到参与人员表
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveBizMeetingMinutesMember(BizMeetingMinutesMember bizMeetingMinutesMember) throws ThriftServiceException, TException {
		try {
			return offlineMeetingMapper.saveBizMeetingMinutesMember(bizMeetingMinutesMember);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "插入数据到参与人员表信息失败！");
		}
	}

	/**
	 * 
	 * @Description: 保存会议纪要附件
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveBizMeetingMinutesFile(BizMeetingMinutesFile bizMeetingMinutesFile) throws ThriftServiceException, TException {
		try {
			return offlineMeetingMapper.saveBizMeetingMinutesFile(bizMeetingMinutesFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "保存会议纪要附件失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据projectId获得该项目所有会议纪要附件信息
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<BizFile> obtainBizFileForOfflineMeeting(int projectId) throws ThriftServiceException, TException {
		try {
			List<BizFile> list = offlineMeetingMapper.obtainBizFileForOfflineMeeting(projectId);
			return list == null ? new ArrayList<BizFile>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "根据projectId获得该项目所有会议纪要附件信息失败！");
		}
	}

	/**
	 * 
	 * @Description: 插入数据到BIZ_PROJECT_MEETING_RST表中
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveBizProjectMeetingRST(BizProjectMeetingRST bizProjectMeetingRST) throws ThriftServiceException, TException {
		try {
			return offlineMeetingMapper.saveBizProjectMeetingRST(bizProjectMeetingRST);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "保存插入数据线下决议会议纪要信息BIZ_PROJECT_MEETING_RST失败！");
		}
	}

	/**
	 * 
	 * @Description: 获得数据BIZ_PROJECT_MEETING表中，初始化使用
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public BizProjectMeeting obtainBizProjectMeetingByProjectId(int projectId) throws ThriftServiceException, TException {
		try {
			BizProjectMeeting bizProjectMeeting = offlineMeetingMapper.obtainBizProjectMeetingByProjectId(projectId);
			return bizProjectMeeting == null ? new BizProjectMeeting() : bizProjectMeeting;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "获得数据BIZ_PROJECT_MEETING表中，初始化使用失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据meetingId获得数据BIZ_PROJECT_MEETING_RST数据
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public BizProjectMeetingRST obtainBizProjectMeetingRSTByMeetingId(int meetingId) throws ThriftServiceException, TException {
		try {
			BizProjectMeetingRST bizProjectMeetingRST = offlineMeetingMapper.obtainBizProjectMeetingRSTByMeetingId(meetingId);
			return bizProjectMeetingRST == null ? new BizProjectMeetingRST() : bizProjectMeetingRST;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "根据meetingId获得数据BIZ_PROJECT_MEETING_RST数据失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据meetingId获得参与人员BIZ_MEETING_MINUTES_MEMBER数据
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<BizMeetingMinutesMember> obtainBizMeetingMinutesMemberByMeetingId(int meetingId) throws ThriftServiceException, TException {
		try {
			List<BizMeetingMinutesMember> list = offlineMeetingMapper.obtainBizMeetingMinutesMemberByMeetingId(meetingId);
			return list == null ? new ArrayList<BizMeetingMinutesMember>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "根据meetingId获得参与人员BIZ_MEETING_MINUTES_MEMBER数据失败！");
		}
	}

	/**
	 * 
	 * @Description: 更新数据BIZ_PROJECT_MEETING表中
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int updateBizProjectMeeting(BizProjectMeeting bizProjectMeeting) throws ThriftServiceException, TException {
		try {
			return offlineMeetingMapper.updateBizProjectMeeting(bizProjectMeeting);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "更新数据BIZ_PROJECT_MEETING表中失败！");
		}
	}

	/**
	 * 
	 * @Description: 更新数据BIZ_PROJECT_MEETING_RST表中
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int updateBizProjectMeetingRST(BizProjectMeetingRST bizProjectMeetingRST) throws ThriftServiceException, TException {
		try {
			return offlineMeetingMapper.updateBizProjectMeetingRST(bizProjectMeetingRST);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "更新数据BIZ_PROJECT_MEETING_RST表中失败！");
		}
	}

	/**
	 * 
	 * @Description: 更新数据BIZ_MEETING_MINUTES_MEMBER表中
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int updateBizMeetingMinutesMember(BizMeetingMinutesMember bizMeetingMinutesMember) throws ThriftServiceException, TException {
		try {
			return offlineMeetingMapper.updateBizMeetingMinutesMember(bizMeetingMinutesMember);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "更新数据BIZ_MEETING_MINUTES_MEMBER表中失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据projectId删除BIZ_PROJECT_MEETING
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int deleteBizProjectMeetingByProjectId(int projectId) throws ThriftServiceException, TException {
		try {
			return offlineMeetingMapper.deleteBizProjectMeetingByProjectId(projectId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "根据projectId删除BIZ_PROJECT_MEETING失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据meetingId删除BIZ_PROJECT_MEETING_RST
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int deletteBizProjectMeetingRSTByMeetingId(int meetingId) throws ThriftServiceException, TException {
		try {
			return offlineMeetingMapper.deletteBizProjectMeetingRSTByMeetingId(meetingId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "根据meetingId删除BIZ_PROJECT_MEETING_RST失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据meetingId删除BIZ_MEETING_MINUTES_MEMBER
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int deleteBizMeetingMinutesMemberByMeetingId(int meetingId) throws ThriftServiceException, TException {
		try {
			return offlineMeetingMapper.deleteBizMeetingMinutesMemberByMeetingId(meetingId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "根据meetingId删除BIZ_MEETING_MINUTES_MEMBER失败！");
		}
	}

	@Override
	public int saveOrganizationCommission(OrganizationCommission organizationCommission) throws ThriftServiceException, TException {
		int pid = 0;
		try {
			BizProjectMeeting bizProjectMeeting = new BizProjectMeeting();
			bizProjectMeeting.setProjectId(organizationCommission.getProjectId());
			bizProjectMeeting.setRecordUserId(organizationCommission.getConveneUserId());
			bizProjectMeeting.setStatus(1);
			offlineMeetingMapper.saveBizProjectMeeting(bizProjectMeeting);
			pid = bizProjectMeeting.getPid();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "组织召开待审会失败");
		}
		return pid;
	}
	/**
	 * 保存组织召开待审会
	 */
	@Override
	public int saveOfflineMeetingInfo(BizProjectMeetingDTO bizProjectMeetingDTO)
			throws ThriftServiceException, TException {
		
		loggerwater(bizProjectMeetingDTO);
		
		
		int meetingId = 0;
		BizProjectMeeting bizProjectMeeting = null;
		try {
			bizProjectMeeting = new BizProjectMeeting();
			bizProjectMeeting.setStatus(1);
			bizProjectMeeting.setProjectId(bizProjectMeetingDTO.getProjectId());
			bizProjectMeeting.setMeetingDttm(bizProjectMeetingDTO.getMeetingDttm());
			bizProjectMeeting.setRecordUserId(bizProjectMeetingDTO.getRecordUserId());
			bizProjectMeeting.setMeetingResult(bizProjectMeetingDTO.getMeetingResult());
			//生成贷审会编号
			String offlineMeetingNum = offlineMeetingMapper.getBizProjectMeetingNum(DateUtil.format(new Date()));
			bizProjectMeeting.setMeetingNum(offlineMeetingNum);
			//保存贷审会信息
			offlineMeetingMapper.saveBizProjectMeeting(bizProjectMeeting);
			//获取贷审会id
			 meetingId = bizProjectMeeting.getPid();

			BizProjectMeetingRST bizProjectMeetingRST = new BizProjectMeetingRST();
			bizProjectMeetingRST.setMeetingId(meetingId);
			bizProjectMeetingRST.setMeetingDttm(bizProjectMeetingDTO.getMeetingDttm());
			bizProjectMeetingRST.setRecordUserId(bizProjectMeetingDTO.getRecordUserId());
			bizProjectMeetingRST.setMeetingLocation(bizProjectMeetingDTO.getMeetingLocation());
			bizProjectMeetingRST.setMeetingResult(bizProjectMeetingDTO.getMeetingResult());
			bizProjectMeetingRST.setStatus(1);

			offlineMeetingMapper.saveBizProjectMeetingRST(bizProjectMeetingRST);
			//选多个成员要进行分割
			String[] allMembersPIDArrays = bizProjectMeetingDTO.getAllMeetingMembersPID().split(";");
			
			for (String meetingMemberUserId : allMembersPIDArrays) {
				// 参与人员BIZ_MEETING_MINUTES_MEMBER
				BizMeetingMinutesMember bizMeetingMinutesMember = new BizMeetingMinutesMember();
				bizMeetingMinutesMember.setMeetingId(meetingId);
				bizMeetingMinutesMember.setMeetingMemberUserId(Integer.parseInt(meetingMemberUserId));
				bizMeetingMinutesMember.setStatus(1);
				offlineMeetingMapper.saveBizMeetingMinutesMember(bizMeetingMinutesMember);// 参与人员BIZ_MEETING_MINUTES_MEMBER
			}
		} catch (Exception e) {
			logger.error("保存贷审会错误信息",e);
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD,"组织召开待审会失败");
		}
		
		return meetingId;
	}

	/**
	 * 
	  * @Description: 输出参数
	  * @param bizProjectMeetingDTO
	  * @author: Rain.Lv
	  * @date: 2015年6月17日 下午8:30:31
	 */
	private void loggerwater(BizProjectMeetingDTO bizProjectMeetingDTO) {
		logger.info("projectId[" + bizProjectMeetingDTO.getProjectId()
				+ "] meetingDttm[" + bizProjectMeetingDTO.getMeetingDttm()
				+ "] allMeetingMembersPID[" + bizProjectMeetingDTO.getAllMeetingMembersPID()
				+ "] recordUserId["+bizProjectMeetingDTO.getRecordUserId()
				+ "] allBizMeetingMinutesMember["+bizProjectMeetingDTO.getAllBizMeetingMinutesMember()
				+ "] meetingLocation["+bizProjectMeetingDTO.getMeetingLocation()
				+ "] meetingResult["+bizProjectMeetingDTO.getMeetingResult()
				+ "] userName["+bizProjectMeetingDTO.getUserName()
				+ "] meetingNum["+bizProjectMeetingDTO.getMeetingNum()
				+ "] ");
	}
	/**
	 * 修改组织贷审会信息
	 */
	@Override
	public int updateOfflineMeetingInfo(BizProjectMeetingDTO bizProjectMeetingDTO) throws ThriftServiceException, TException {
		BizProjectMeeting bizProjectMeeting = null;
		BizProjectMeetingRST bizProjectMeetingRST = null;
		
		int meetingId = 0;
		try {
			//通过项目id得出贷审会信息
			bizProjectMeeting = offlineMeetingMapper.queryBizProjectMeetingInfo(bizProjectMeetingDTO.getProjectId());
			meetingId = bizProjectMeeting.getPid();
			bizProjectMeeting.setMeetingDttm(bizProjectMeetingDTO.getMeetingDttm());
			bizProjectMeeting.setRecordUserId(bizProjectMeetingDTO.getRecordUserId());
			bizProjectMeeting.setMeetingResult(bizProjectMeetingDTO.getMeetingResult());
			
			if(null==bizProjectMeeting.getMeetingNum()||"".equals(bizProjectMeeting.getMeetingNum())){
				//生成贷审会编号
				String offlineMeetingNum = offlineMeetingMapper.getBizProjectMeetingNum(DateUtil.format(new Date()));
				bizProjectMeeting.setMeetingNum(offlineMeetingNum);
			}

			//修改保存后的信息
			offlineMeetingMapper.updateProjectMeetingInfo(bizProjectMeeting);
			
			//通过贷审会id得出线下决议会议纪要信息
			bizProjectMeetingRST = offlineMeetingMapper.queryBizProjectMeetingRstInfo(meetingId);
			
			if(null==bizProjectMeetingRST){//如果没有值 新增
				BizProjectMeetingRST bizProjectMeetingResInfo = new BizProjectMeetingRST();
				bizProjectMeetingResInfo.setMeetingId(meetingId);
				bizProjectMeetingResInfo.setMeetingDttm(bizProjectMeetingDTO.getMeetingDttm());
				bizProjectMeetingResInfo.setRecordUserId(bizProjectMeetingDTO.getRecordUserId());
				bizProjectMeetingResInfo.setMeetingLocation(bizProjectMeetingDTO.getMeetingLocation());
				bizProjectMeetingResInfo.setMeetingResult(bizProjectMeetingDTO.getMeetingResult());
				bizProjectMeetingResInfo.setStatus(1);
				offlineMeetingMapper.saveBizProjectMeetingRST(bizProjectMeetingResInfo);
			}else{
				bizProjectMeetingRST.setMeetingDttm(bizProjectMeetingDTO.getMeetingDttm());
				bizProjectMeetingRST.setRecordUserId(bizProjectMeetingDTO.getRecordUserId());
				bizProjectMeetingRST.setMeetingLocation(bizProjectMeetingDTO.getMeetingLocation());
				bizProjectMeetingRST.setMeetingResult(bizProjectMeetingDTO.getMeetingResult());
				//线下决议会议纪要保存
				offlineMeetingMapper.updateProjectMeetingRstInfo(bizProjectMeetingRST);
			}

			//先删除贷审会参与人员 在保存新的
			offlineMeetingMapper.deleteMeetingMinutesMemberInfo(meetingId);
			String[] allMembersPIDArrays = bizProjectMeetingDTO.getAllMeetingMembersPID().split(";");
			for (String meetingMemberUserId : allMembersPIDArrays) {
				// 参与人员BIZ_MEETING_MINUTES_MEMBER
				BizMeetingMinutesMember bizMeetingMinutesMember = new BizMeetingMinutesMember();
				bizMeetingMinutesMember.setMeetingId(meetingId);
				bizMeetingMinutesMember.setMeetingMemberUserId(Integer.parseInt(meetingMemberUserId));
				bizMeetingMinutesMember.setStatus(1);
				offlineMeetingMapper.saveBizMeetingMinutesMember(bizMeetingMinutesMember);// 参与人员BIZ_MEETING_MINUTES_MEMBER
			}
		} catch (Exception e) {
			logger.error("修改贷审会错误信息",e);
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD,"组织召开待审会失败");
		}
		
		return meetingId;
	}

}
