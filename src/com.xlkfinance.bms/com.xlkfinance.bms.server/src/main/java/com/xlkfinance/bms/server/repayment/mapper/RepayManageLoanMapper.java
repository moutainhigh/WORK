package com.xlkfinance.bms.server.repayment.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.repayment.InterestChgApplyView;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RepayCgInterestDTO;
import com.xlkfinance.bms.rpc.repayment.RepayCgInterestView;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;

/**
 * 类描述： 创建人：gaoWen 创建时间：2015年3月12日 下午2:36:06
 */
@MapperScan("repayManageLoanMapper")
public interface RepayManageLoanMapper<T, PK> extends BaseMapper<T, PK> {
	/**
	  * @Description: 描述：利率表更查询
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView> 
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:46:39
	 */
	List<RepayCgInterestView> selectLoanInterestDetail(
			RepayCgInterestDTO repayCgInterestDTO);

	/**
	  * @Description: 描述：保存利率表更申请
	  * @param uploadinstAdvapplyBaseDTO
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:47:42
	  */
	void insertInstCgapply(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	/**
	  * @Description: 描述：保存文件
	  * @param uploadinstAdvapplyBaseDTO
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:48:23
	  */
	void insertInstFileapply(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	/**
	 * 描述：插入保存的贷款利息
	 * 
	 * @return
	 */
	int insertInstCgLoan(RepayCgInterestDTO repayCgInterestDTO);

	/**
	 * 描述：插入保存的利息变更表
	 * 
	 * @return
	 */
	int insertInstCgLoanapply(RepayCgInterestDTO repayCgInterestDTO);

	/**
	  * @Description:描述：利率表更查询申请状态中的
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:49:06
	 */
	List<RepayCgInterestView> selectLoanRequestInterestDetail(
			RepayCgInterestDTO repayCgInterestDTO);

	/**
	  * @Description: 描述：更新保存的利息变更表
	  * @param repayCgInterestDTO
	  * @return
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:51:59
	  */
	int updateRepayCgapplyInfo(RepayCgInterestDTO repayCgInterestDTO);

	/**
	  * @Description: 查询保存的文件
	  * @param uploadinstAdvapplyBaseDTO
	  * @return List<RegAdvapplyFileview>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:59:18
	  */
	List<RegAdvapplyFileview> queryRepayCgapplyFile(
			UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	/**
	  * @Description: 查询保存的文件总数
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:59:50
	  */
	int queryRepayCgapplyFileCount(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);
	
	/**
	  * @Description: 查询利率变更申请中的利息根据利率变更id
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:00:27
	  */
	List<RepayCgInterestView> selectLoanInterestDetailbyProces(
			RepayCgInterestDTO repayCgInterestDTO);

	/**
	  * @Description: 查询利率变更申请中的利息根据利率变更id
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:00:55
	  */
	List<RepayCgInterestView> selectLoanRequestInterestDetailbyProces(
			RepayCgInterestDTO repayCgInterestDTO);

	/**
	  * @Description: 利率变更任务流完成时候利息变更生效
	  * @param repayCgInterestDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:01:25
	  */
	int updateintRepayCgapplyInfoEnd(RepayCgInterestDTO repayCgInterestDTO);

	/**
	  * @Description: 修改利率变更的申请状态
	  * @param repayCgInterestDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:01:53
	  */
	int changeReqstCg(RepayCgInterestDTO repayCgInterestDTO);

	int checkpreRepayByProjectId(int projectId);

	/**
	  * @Description: 删除利率变更
	  * @param interestChgId
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:02:47
	  */
	int deleteProjectbyinterestChgId(int interestChgId);

	/**
	 * 利息变更申请书
	 */
	List<InterestChgApplyView> makeCgApplyFile(int interestChgId);

	List<InterestChgApplyView> makeCgApplyGuarantee(int projectId);

	// 是变更利率生效
	int updateLoanbyintCg(int interestChgId);

	/**
	  * @Description: 通过项目ID得出利率变更ID
	  * @param projectId
	  * @return InterestChgApplyView
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:05:31
	  */
	InterestChgApplyView queryInterestChgId(int projectId);
	
	/**
	  * @Description: 查询利率变更申请中的利息根据利率变更id并且是已归档
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:05:57
	  */
	List<RepayCgInterestView> selectLoanRequestInterestDetailbyProcesByStatus(
			RepayCgInterestDTO repayCgInterestDTO);
	
	/**
	 * 
	  * @Description: 提交利率变更后保存到历史表
	  * @param repayCgInterestDTO
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月19日 上午11:00:31
	 */
	public int addLoanResInfo(int interestChgId);
	
	/**
	 * 
	  * @Description: 通过利率id查出变更前的利率方便做展示
	  * @param repayCgInterestDTO
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月19日 下午3:16:00
	 */
	List<RepayCgInterestView> queryLoanRes(RepayCgInterestDTO repayCgInterestDTO);
	
	/**
	  * @Description: 保存利率变更贷审会意见信息
	  * @param repayCgInterestDTO
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年6月27日 上午11:16:46
	  */
	int updateProceduresByPrimaryKey(RepayCgInterestDTO repayCgInterestDTO);
	
	/**
	 * 
	  * @Description: TODO
	  * @param interestChgId
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年6月27日 上午11:43:52
	 */
	List<RepayCgInterestView> getRepayCgInterestByPid(int interestChgId);

}
