package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.BizMeetingMinutesFile;
import com.xlkfinance.bms.rpc.beforeloan.BizMeetingMinutesMember;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectMeeting;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectMeetingRST;
import com.xlkfinance.bms.rpc.system.BizFile;

/**   
* @Title: BizProjectMeetingMapper.java 
* @Package com.xlkfinance.bms.server.beforeloan.mapper 
* @Description:线下决议会议纪要
* @author Ant.Peng   
* @date 2015年2月5日 下午8:31:11 
* @version V1.0   
*/ 
@MapperScan("offlineMeetingMapper")
public interface OfflineMeetingMapper<T, PK> extends BaseMapper<T, PK> {
	//保存线下贷审会会议纪要文件
	public int saveBizMeetingMinutesFile(BizMeetingMinutesFile bizMeetingMinutesFile);
	public int saveBizMeetingMinutesMember(BizMeetingMinutesMember bizMeetingMinutesMember);
	//保存项目会议
	public int saveBizProjectMeeting(BizProjectMeeting bizProjectMeeting);
	public List<BizFile> obtainBizFileForOfflineMeeting(int projectId);
	public int saveBizProjectMeetingRST(BizProjectMeetingRST bizProjectMeetingRST);
	//根据项目ID得到贷审会会议资料
	public BizProjectMeeting obtainBizProjectMeetingByProjectId(int projectId);
	public BizProjectMeetingRST obtainBizProjectMeetingRSTByMeetingId(int meetingId);
	public List<BizMeetingMinutesMember> obtainBizMeetingMinutesMemberByMeetingId(int meetingId);
	//修改项目会议文件
	public int updateBizProjectMeeting(BizProjectMeeting bizProjectMeeting);
	public int updateBizProjectMeetingRST(BizProjectMeetingRST bizProjectMeetingRST);
	public int updateBizMeetingMinutesMember(BizMeetingMinutesMember bizMeetingMinutesMember);
	//根据项目ID删除项目纪要文件
	public int deleteBizProjectMeetingByProjectId(int projectId);
	public int deletteBizProjectMeetingRSTByMeetingId(int meetingId);
	public int deleteBizMeetingMinutesMemberByMeetingId(int meetingId);
	
	public String getBizProjectMeetingNum(String nowDttm);
	
	/**
	 * 
	  * @Description: 通过项目id得到对象
	  * @param projectId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月17日 下午7:45:04
	 */
	public BizProjectMeeting queryBizProjectMeetingInfo(int projectId); 
	
	/**
	 * 
	  * @Description: 通过项目贷审会id得到线下决议会议纪要对象
	  * @param meetingId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月17日 下午8:51:40
	 */
	public BizProjectMeetingRST queryBizProjectMeetingRstInfo(int meetingId);
	
	/**
	 * 
	  * @Description: 修改贷审会信息
	  * @param bizProjectMeeting
	  * @author: Rain.Lv
	  * @date: 2015年6月18日 上午10:05:28
	 */
	public void updateProjectMeetingInfo(BizProjectMeeting bizProjectMeeting);
	
	/**
	 * 
	  * @Description: 修改线下决议会议纪要信息
	  * @param bizProjectMeetingRST
	  * @author: Rain.Lv
	  * @date: 2015年6月18日 上午10:06:16
	 */
	public void updateProjectMeetingRstInfo(BizProjectMeetingRST bizProjectMeetingRST);
	
	/**
	 * 
	  * @Description: 删除贷审会参与人员信息
	  * @param meetingId
	  * @author: Rain.Lv
	  * @date: 2015年6月18日 上午10:13:58
	 */
	public void deleteMeetingMinutesMemberInfo(int meetingId);
}
