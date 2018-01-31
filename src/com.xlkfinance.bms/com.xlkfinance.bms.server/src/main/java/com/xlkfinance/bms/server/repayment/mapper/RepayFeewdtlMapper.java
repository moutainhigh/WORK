/**
 * @Title: RepayFeewdtlMapper.java
 * @Package com.xlkfinance.bms.server.repayment.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: achievo
 * @date: 2015年3月26日 下午6:05:44
 * @version V1.0
 */
package com.xlkfinance.bms.server.repayment.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.repayment.FeeWaiverApplicationDTO;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlDatDTO;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlDatView;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;

/**
 * 
 * @ClassName: RepayFeewdtlMapper
 * @Description: TODO
 * @author: gaoWen
 * @date: 2015年3月26日 下午6:13:21
 */

@MapperScan("repayFeewdtlMapper")
public interface RepayFeewdtlMapper<T, PK> extends BaseMapper<T, PK> {
	/**
	  * @Description: 插入费用减免信息
	  * @param repayfeew
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:23:55
	  */
	int insertLoanFeewdelInfo(RepayFeewdtlDatDTO repayfeew);

	/**
	  * @Description: 保存费用减免详细信息
	  * @param repayfeew
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:24:48
	 */
	int insertLoanFeewInfo(RepayFeewdtlDatDTO repayfeew);

	/**
	  * @Description: 保存上传文件与费用减免关联
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:25:58
	  */
	int insertLoanFeewfileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);
	/**
	  * @Description: 保存上传文件
	  * @param uploadinstAdvapplyBaseDTO
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:25:20
	  */
	void insertFeewfileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);
	/**
	  * @Description: 查询文件列表
	  * @param map
	  * @return List<RegAdvapplyFileview>
	  * @author: JingYu.Dai
	  * @date: 2015年6月5日 上午10:33:59
	  */
	List<RegAdvapplyFileview> queryRegFeewapplyFile(Map<String,Integer> map);
	/**
	  * @Description: 查询文件列表总记录条数
	  * @param repayId
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年6月5日 上午10:35:08
	 */
	int queryRegFeewapplyFileTotal(int repayId);
    /**
     * @Description: 初始化流程任务信息贷款表信息
     * @param repayFeewdtlDatView
     * @return
     * @author: JingYu.Dai
     * @date: 2015年8月3日 下午3:28:20
     */
	List<RepayFeewdtlDatView> queryRegFeewLoanbyprocess(RepayFeewdtlDatView repayFeewdtlDatView);
	 /**
	   * @Description: 初始化流程任务信息费用减免表信息
	   * @param repayId
	   * @return
	   * @author: JingYu.Dai
	   * @date: 2015年8月3日 下午3:27:11
	   */
	List<RepayFeewdtlDatView> queryRegFeewDealbyprocess(int repayId);
	/**
	  * @Description: 查询费用减免的原因
	  * @param repayId
	  * @return
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:29:13
	  */
	List<RepayFeewdtlDatView> queryRegFeewReasonbyprocess(int repayId);
	/**
	  * @Description:更新减免的原因
	  * @param repayfeew
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:30:18
	 */
	int updateRegFeewDealReason(RepayFeewdtlDatDTO repayfeew);
	/**
	  * @Description: 更新减免的的信息
	  * @param feewdtlDatView
	  * @return
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:30:49
	 */
	int updateRegFeewDealinfo(RepayFeewdtlDatDTO feewdtlDatView);
	/**
	  * @Description: 查询费用减免项目信息更具费用减免id
	  * @param repayId
	  * @return List<RepayFeewdtlDatView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:32:03
	 */
	List<RepayFeewdtlDatView> queryRegFeewprojectinfobyrepayId(int repayId);
	/**
	  * @Description: 查询费用减免项目信息更具项目id
	  * @param projectId
	  * @return List<RepayFeewdtlDatView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:31:21
	 */
	List<RepayFeewdtlDatView> queryRegFeewprojectinfobyproId(int projectId);
	/**
	  * @Description:修改费用减免的状态
	  * @param repayFeewdtlDatView
	  * @return int 
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:34:56
	  */
	int changeReqstFeewdel(RepayFeewdtlDatView repayFeewdtlDatView);
	/**
	 * 修改减免细节是否启用（如果对于期数已经对账完毕了，哪这一期减免就失效了）
	  * @Description: TODO
	  * @param feedId
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年5月23日 下午4:00:01
	 */
	int changeFeewdelStatus(int feedId);

	int checkFeewDealByProjectId(int projectId);

	/**
	  * @Description: 删除费用减免信息
	  * @param repayId
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:36:56
	  */
	int deleteProjectbyFeewDeal(int repayId);
	
	/**
	  * @Description: 批量删除费用减免 根据费用减免Id
	  * @param repayIds 费用减免Id集合
	  * @return int 受影响行数
	  * @author: JingYu.Dai
	  * @date: 2015年5月30日 下午4:34:30
	 */
	int deleteProjectbyFeewDealList(List<Integer> repayIds);

	/**
	  * @Description: 更改费用减免详细信息
	  * @param repayfeew
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:39:44
	 */
	int updateLoanFeewdelInfo(RepayFeewdtlDatDTO repayfeew);

	/**
	  * @Description: 费用减免明细
	  * @param repayId
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:43:18
	 */
	int deleteFeewDealDatilbyrepayId(int repayId);
	
	/**
	  * @Description: 费用减免申请书数据查询
	  * @param map
	  * @return FeeWaiverApplicationDTO
	  * @author: JingYu.Dai
	  * @date: 2015年5月19日 下午8:05:06
	 */
	FeeWaiverApplicationDTO findFeeWaiverApplication(Map<String,Integer> map);
	
	/**
	  * @Description: 批量跟新费用减免数据列表
	  * @param feewdtlDatDTOs
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年5月22日 下午5:39:18
	 */
	int updateProjectFeewdtlBatch(List<RepayFeewdtlDatDTO> feewdtlDatDTOs);
}
